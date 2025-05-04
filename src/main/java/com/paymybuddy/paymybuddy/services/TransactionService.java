package com.paymybuddy.paymybuddy.services;

import com.paymybuddy.paymybuddy.dtos.transaction.ReceiverDto;
import com.paymybuddy.paymybuddy.dtos.transaction.SenderDto;
import com.paymybuddy.paymybuddy.dtos.transaction.TransactionDto;
import com.paymybuddy.paymybuddy.entities.Transaction;
import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.repositories.BankAccountRepository;
import com.paymybuddy.paymybuddy.repositories.TransactionRepository;
import com.paymybuddy.paymybuddy.repositories.UserRepository;
import com.paymybuddy.paymybuddy.services.interfaces.ITransaction;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionService implements ITransaction {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private IUser userService;
    @Autowired
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

    public Transaction getTransaction(User session, String email ){
        //Process transaction

        //emai // description // payer

        //Transaction
        // userSession : HttpSession
        User userSender = userService.getUserById(session.getId());
        // userRequest : email -> getUserByEmail(email)
        User userReceiver = userService.getUserByEmail(email);


        // Bank
        // double amount = userSession.getAmount()
        // userSession.setAmount( amount - payer)
        // userRequest.


        // sender :: Sender(User session) receiver(userRequest)

        // addTransaction(object Transaction)

        return null;
    }

    public void addTransaction(){

    }


    private ReceiverDto ReceiverMapper(Transaction transaction) {
        return  modelMapper.map(transaction, ReceiverDto.class);
    }
    private SenderDto SenderMapper(Transaction transaction) {
        return  modelMapper.map(transaction, SenderDto.class);
    }
}
