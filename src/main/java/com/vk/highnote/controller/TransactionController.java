package com.vk.highnote.controller;

import com.vk.highnote.service.TransactionService;
import com.vk.highnote.model.UserTransaction;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * find all transactions
     * @return List of Transactions
     */
    @GetMapping
    public List<UserTransaction> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    /**
     * find all transactions by user
     * @param userId userId
     * @return list of transactions
     */
    @GetMapping("/{userId}")
    public List<UserTransaction> findTransactionsByUser(@PathParam("userId") Long userId) {
        return transactionService.findTransactionsByUser(userId);
    }

    /**
     * process a file and persist the transactions
     * @param s3FilePath filePath s3 file path
     * @param userId userId to associate the transactions with
     */
    @PostMapping("/{userId}")
    public int processByFile(@PathParam("userId") Long userId, @RequestParam String s3FilePath) {
        return transactionService.processTransactionFile(s3FilePath, userId);
    }
}
