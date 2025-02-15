package com.vk.highnote.controller;

import com.vk.highnote.service.TransactionService;
import com.vk.highnote.model.UserTransaction;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
     * @param path filePath s3 file path
     */
    @PostMapping("/{userId}")
    public void processByFile(@RequestParam String path) {

    }
}
