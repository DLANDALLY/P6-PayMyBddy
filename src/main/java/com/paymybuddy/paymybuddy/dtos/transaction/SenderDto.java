package com.paymybuddy.paymybuddy.dtos.transaction;

import com.paymybuddy.paymybuddy.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SenderDto {
    private String username;
    private String description;
    private Double amount;
    private LocalDateTime date;


    /*String test = """
            SenderDto(
                sender=User(
                    id=3,
                    username=charlie,
                    email=charlie@example.com,
                    password=charliepw,
                    bankAccount=com.paymybuddy.paymybuddy.entities.BankAccount@31eb00fb,
                    connections=[
                        User(
                            id=6,
                            username=fiona,
                            email=fiona@example.com,
                            password=fionapass,
                            bankAccount=com.paymybuddy.paymybuddy.entities.BankAccount@32fced8d,
                            connections=[
                                User(
                                    id=9,
                                    username=isaac,
                                    email=isaac@example.com,
                                    password=isaacpw,
                                    bankAccount=com.paymybuddy.paymybuddy.entities.BankAccount@1c8d2b1e,
                                    connections=[],
                                    sentTransactions=[],
                                    receivedTransactions=[com.paymybuddy.paymybuddy.entities.Transaction@376c1ea],
                                    createdAt=2023-09-15T20:40) ],
                            sentTransactions=[
                                com.paymybuddy.paymybuddy.entities.Transaction@5b0d6a35],
                                receivedTransactions=[com.paymybuddy.paymybuddy.entities.Transaction@1ba7c727],
                                createdAt=2023-06-10T13:10)],
                                sentTransactions=[com.paymybuddy.paymybuddy.entities.Transaction@2ba603c1],
                                receivedTransactions=[com.paymybuddy.paymybuddy.entities.Transaction@2225197a],
                                createdAt=2023-03-18T09:00),
                                username=null,
                                description=Salary payment,
                                amount=-1200.0,
                                date=null)

            """;*/
}
