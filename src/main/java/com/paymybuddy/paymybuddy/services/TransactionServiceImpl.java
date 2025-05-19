package com.paymybuddy.paymybuddy.services;

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
    public List<TransactionDto> getAllUserTransactions(User user) {
        User userBD = userService.getUserByEmail(user.getEmail());

        List<Transaction> senders = getTransactionBySender(userBD);
        List<Transaction> receiver = getTransactionByReceiver(userBD);
        senders.forEach(t -> t.setAmount(-Math.abs(t.getAmount())));

        List<Transaction> transactions = mergeAndSortTransactions(senders, receiver);

        return convertToDto(transactions, userBD);
    }

    private List<Transaction> getTransactionBySender(User user){
        return transactionRepository.findTransactionsBySender(user);
    }

    private List<Transaction> getTransactionByReceiver(User user){
        return transactionRepository.findTransactionsByReceiver(user);
    }

    private List<TransactionDto> convertToDto(List<Transaction> transactions, User user){
        return transactions.stream()
                .map(t -> {
                    TransactionDto dto = new TransactionDto();
                    dto.setDescription(t.getDescription());
                    dto.setAmount(t.getAmount());
                    dto.setUsername(
                            (t.getSender().getId() == user.getId()) ?
                                    t.getReceiver().getUsername() :
                                    t.getSender().getUsername());
                    return dto;
                }).toList();
    }

    private List<Transaction> mergeAndSortTransactions(List<Transaction> receiver, List<Transaction> senders ){
        List<Transaction> transactions = new ArrayList<>();
        if (senders != null) transactions.addAll(senders);
        if (receiver != null) transactions.addAll(receiver);

        transactions.sort(Comparator.comparing(
                Transaction::getId,
                Comparator.nullsLast(Comparator.naturalOrder()))
                .reversed());

        return transactions;
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
    public void processTransaction(User session, TransactionForm form){
        User sender = userService.getUserById(session.getId());
        User receiver = userService.getUserByEmail(form.getEmail());
        User admin = userService.getUserByEmail("admin@hotmail.fr");

        if (receiver == null || sender == null || admin == null)
            throw new IllegalArgumentException("User not found");

        if (receiver.getId() == sender.getId())
            throw new IllegalArgumentException("You can't send money to yourself");

        double tax = calculateTax(form.getAmount());
        double totalAmount = form.getAmount() + tax;

        if (sender.getBankAccount().getBalance() < totalAmount)
            throw new IllegalStateException("Insufficient balance");

        updateBalances(sender, receiver, admin, form.getAmount(), tax);

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setDescription(form.getDescription());
        transaction.setAmount(form.getAmount());
        createTransaction(transaction);
    }

    private double calculateTax(double amount) {
        return (0.5 * amount) / 100;
    }

    private void updateBalances(User sender, User receiver, User admin, double amount, double tax) {
        sender.getBankAccount().setBalance(sender.getBankAccount().getBalance() - (amount + tax));
        receiver.getBankAccount().setBalance(receiver.getBankAccount().getBalance() + amount);
        admin.getBankAccount().setBalance(admin.getBankAccount().getBalance() + tax);

        bankAccountService.updateBank(sender.getBankAccount());
        bankAccountService.updateBank(receiver.getBankAccount());
        bankAccountService.updateBank(admin.getBankAccount());
    }

    private void createTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }


}
