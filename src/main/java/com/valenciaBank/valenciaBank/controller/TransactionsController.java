package com.valenciaBank.valenciaBank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valenciaBank.valenciaBank.model.Account;
import com.valenciaBank.valenciaBank.model.Transaction;
import com.valenciaBank.valenciaBank.model.TransactionData;
import com.valenciaBank.valenciaBank.model.User;
import com.valenciaBank.valenciaBank.service.AccountService;
import com.valenciaBank.valenciaBank.service.TransactionService;
import com.valenciaBank.valenciaBank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

import static com.valenciaBank.valenciaBank.utils.Methods.readJSON;


@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class TransactionsController {



    @Autowired
    private TransactionService transactionsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;


    //En este metodo me esta entrando el json de la web al servidor refactorizar codigo pero funciona como dios
    @PostMapping("/add")
    public Transaction addDeposit(@RequestBody String inputWeb) {
        TransactionData transactionData;
        System.out.println("lo que me pasan es" + inputWeb);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            transactionData = objectMapper.readValue(inputWeb, TransactionData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Transaction transaction = transactionData.getTransaction();
        User user = userService.getUser(transactionData.getUser());

        System.out.println("Lo que nos envia la web:");
        System.out.println("Origin Account: " + transaction.getOriginAccount());
        System.out.println("Destination Account: " + transaction.getDestinationAccount());
        System.out.println("Amount: " + transaction.getAmount());
        System.out.println("Date: " + transaction.getDate());
        System.out.println("dniUser: " + user.getDni());

        Account destinationAccount = accountService.findAccountByNumber(transaction.getDestinationAccount());
        Account originAccount = accountService.findAccountByNumber(transaction.getOriginAccount());
        System.out.println("user que obtenemos: " + user.toString());


        try{
            if (user.getId() != null){
                transaction.setUser(user);
                if (transaction.getOriginAccount().equalsIgnoreCase(transaction.getDestinationAccount())){ // si es un ingreso solamente se suma el dinero de su misma cuenta
                    destinationAccount.setBalance(destinationAccount.getBalance() + transaction.getAmount());
                    user.setTransactions(user.getTransactions());
                    transactionsService.saveTransaction(transaction);
                }else { // si es una transferencia de una cuenta a otra hay que quitar el dinero de la cuenta origen y sumarle lo que tiene mas lo que le llega a la destination account
                    originAccount.setBalance(originAccount.getBalance() - transaction.getAmount());
                    destinationAccount.setBalance(destinationAccount.getBalance() + transaction.getAmount());
                    user.setTransactions(user.getTransactions());
                    transactionsService.saveTransaction(transaction);
                }

            }else{
                System.out.println("el usuario que se esta pasando con el dni null por lo tanto no se esta cogiendo bien la info");
            }
        }catch (Exception e){
            System.out.println("hubo un fallo en el servidor");
            System.out.println(e.getMessage());
        }
        return transaction;
    }


}
