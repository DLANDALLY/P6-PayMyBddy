package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.transaction.ReceiverDto;
import com.paymybuddy.paymybuddy.dtos.transaction.SenderDto;
import com.paymybuddy.paymybuddy.dtos.transaction.TransactionDto;
import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.TransactionForm;
import com.paymybuddy.paymybuddy.repositories.TransactionRepository;
import com.paymybuddy.paymybuddy.services.interfaces.IBankAccount;
import com.paymybuddy.paymybuddy.services.interfaces.ITransaction;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransaction {
    private TransactionRepository transactionRepository;
    private IBankAccount bankAccountService;
    private IUser userService;
    private ModelMapper modelMapper;


    @Override
    public List<TransactionDto> getReceiverAndSenderTransaction(User user) {
        List<Transaction> senders = getTransactionBySender(user);
        senders.forEach(t -> t.setAmount(-Math.abs(t.getAmount())));

        List<Transaction> receiver = getTransactionByReceiver(user);
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(senders);
        transactions.addAll(receiver);
        transactions.sort(Comparator.comparing(Transaction::getId).reversed());

        return transactions.stream()
                .map(t -> {
                    TransactionDto transactionDto = new TransactionDto();
                    transactionDto.setDescription(t.getDescription());
                    transactionDto.setAmount(t.getAmount());
                    transactionDto.setUsername(
                            (t.getSender().getId() == user.getId()) ?
                                    t.getReceiver().getUsername() :
                                    t.getSender().getUsername());
                    return transactionDto;
                }).toList();
    }

    @Override
    public List<Transaction> getTransactionBySender(User user){
        return transactionRepository.findTransactionsBySender(user);
    }

    @Override
    public List<Transaction> getTransactionByReceiver(User user){
        return transactionRepository.findTransactionsByReceiver(user);
    }

    /**
     * pour faire une transaction
     * - le compte du UserSender
     * - le compte du UserReceiver
     *
     * condition : Verifier que le UserSender à le montant annoncé
     *
     * soustraire le prix du compte UserSender
     * addition le prix du compte UserReceiver
     *
     * (method de la class BankService)
     * update le compte UserSender
     * update le compte UserReceiver
     *
     * Ajouter la transaction dans la base de donnée
     * create - createTransaction(idSender, idReceiver, amount, description)
     * add(new Transaction)
     */
    @Transactional
    @Override
    public Transaction getTransaction(User session, TransactionForm transactionForm){
        System.out.println("GET transaction :");
        // userSession : HttpSession
        User userSender = userService.getUserById(session.getId());
        System.out.println("################# start ##################");
        System.out.println("sender: "+ userSender.getUsername());
        System.out.println("balance: "+ userSender.getBankAccount().getBalance());
        System.out.println("##########################################");

        User userReceiver = userService.getUserByEmail(transactionForm.getEmail());
        System.out.println("receiver: "+ userReceiver.getUsername());
        System.out.println("balance: "+ userReceiver.getBankAccount().getBalance());

        double tax = (0.5 * transactionForm.getAmount())/100 ; //TODO: tax ajouter la tax au compte "admin"
        double calculeMontant = transactionForm.getAmount() + tax;
        double balanceSender = userSender.getBankAccount().getBalance() - (calculeMontant);
        double balanceReceiver = userReceiver.getBankAccount().getBalance() + transactionForm.getAmount();

        if (balanceSender < 0) {
            String error = "tas pas de tune mec :p";}

        userSender.getBankAccount().setBalance(balanceSender);
        userReceiver.getBankAccount().setBalance(balanceReceiver);


        System.out.println("################# Update ##################");
        System.out.println("sender: "+ userSender.getUsername());
        System.out.println("Update balance: "+ userSender.getBankAccount().getBalance());
        System.out.println("Montant de la tax = "+ tax);
        System.out.println("##########################################");
        System.out.println("receiver: "+ userReceiver.getUsername());
        System.out.println("Update balance: "+ userReceiver.getBankAccount().getBalance());
        System.out.println("##########################################");

        bankAccountService.updateBank(userSender.getBankAccount());
        bankAccountService.updateBank(userReceiver.getBankAccount());

        Transaction transaction = new Transaction();
        transaction.setSender(userSender);
        transaction.setReceiver(userReceiver);
        transaction.setDescription(transactionForm.getDescription());
        transaction.setAmount(calculeMontant);
        createTransaction(transaction);

        return null;
    }

    //Ajt execption
    public void createTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

    //A supp
    private ReceiverDto ReceiverMapper(Transaction transaction) {
        return  modelMapper.map(transaction, ReceiverDto.class);
    }
    //A supp
    private SenderDto SenderMapper(Transaction transaction) {
        return  modelMapper.map(transaction, SenderDto.class);
    }
}
