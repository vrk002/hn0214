package com.vk.highnote.service;

import com.vk.highnote.model.UserTransaction;
import com.vk.highnote.repository.UserTransactionRepository;
import com.vk.highnote.service.gcp.GoogleCloudStorageService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {
    private final GoogleCloudStorageService googleCloudStorageService;
    private final UserTransactionRepository userTransactionRepository;

    public List<UserTransaction> findAllTransactions() {
        return userTransactionRepository.findAll();
    }

    public List<UserTransaction> findTransactionsByUser(@NonNull Long userId) {
        return userTransactionRepository.findByUserId(userId);
    }

    public int processTransactionFile(@NonNull String fileName, @NonNull Long userId) {
        List<UserTransaction> transactions = process(fileName, userId);
        persistTransactions(transactions);
        System.out.println("completed processing file: " + fileName + ", number of transactions created: " + transactions.size());
        return transactions.size();
    }

    @Transactional
    public void persistTransactions(List<UserTransaction> userTransactionList) {
        userTransactionRepository.saveAll(userTransactionList);
        System.out.println("saved transactions count: " + userTransactionList.size());
    }

    public List<UserTransaction> process(@NonNull String fileName, @NonNull Long userId) {
        System.out.println("Starting to process fileName:" + fileName);
        List<UserTransaction> transactions = null;
        try {
            transactions = googleCloudStorageService.processLargeCsvFile(fileName, userId);
            System.out.println("UserId: " + userId + ", total number of transactions compiled: " + transactions.size());
            return transactions;
        }   catch (Exception e) {
            System.out.println("Failed while processing S3 file: " + fileName);
        }
        return transactions;
    }
}
