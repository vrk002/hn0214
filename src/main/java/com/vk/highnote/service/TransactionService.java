package com.vk.highnote.service;

import com.vk.highnote.model.UserTransaction;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vk.highnote.repository.UserTransactionRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {
    private final FileService fileService;
    private final UserTransactionRepository userTransactionRepository;

    public List<UserTransaction> findAllTransactions() {
        return userTransactionRepository.findAll();
    }

    public List<UserTransaction> findTransactionsByUser(@NonNull Long userId) {
        return userTransactionRepository.findByUserId(userId);
    }

    public void processTransactionFile(String fileName, Long userId) {
        List<UserTransaction> transactions = process(fileName, userId);
        persistTransactions(transactions);
        System.out.println("completed processing file: " + fileName + ", number of transactions created: " + transactions.size());
    }

    @Transactional
    public void persistTransactions(List<UserTransaction> userTransactionList) {
        userTransactionRepository.saveAll(userTransactionList);
        System.out.println("saved transactions count: " + userTransactionList.size());
    }

    public List<UserTransaction> process(String fileName, Long userId) {
        System.out.println("Starting to process fileName:" + fileName);
        String[] transactions = fileService.loadFileLines(fileName);
        List<UserTransaction> userTransactionList = new ArrayList<>();
        for (String transaction: transactions){
            System.out.println(transaction);
            String[] content = transaction.split(" ");

            UserTransaction userTransaction = UserTransaction.builder()
                    .userId(userId)
                    .vendor(content[0])
                    .product(content[1])
                    .unitPrice(Float.valueOf(content[2]))
                    .totalPrice(Float.valueOf(content[3]))
                    .quantity(Float.valueOf(content[4]))
                    .invoiceDate(Timestamp.valueOf(content[5] + " " + content[6]))
                    .build();

            userTransactionList.add(userTransaction);
        }
        System.out.println("total number of transactions compiled: " + userTransactionList.size());
        return userTransactionList;
    }
}
