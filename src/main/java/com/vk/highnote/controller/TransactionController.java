package com.vk.highnote.controller;

import com.vk.highnote.service.TransactionService;
import com.vk.highnote.model.UserTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
@Tag(name = "Transaction Controller", description = "APIs for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * find all transactions
     * @return List of Transactions
     */
    @Operation(summary = "Get all transactions")
    @ApiResponse(responseCode = "200", description = "Found all transactions")
    @GetMapping
    public List<UserTransaction> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    /**
     * find all transactions by user
     * @param userId userId
     * @return list of transactions
     */
    @Operation(summary = "Get transactions by user ID")
    @ApiResponse(responseCode = "200", description = "Found transactions by user ID")
    @GetMapping("/{userId}")
    public List<UserTransaction> findTransactionsByUser(@PathVariable("userId") Long userId) {
        return transactionService.findTransactionsByUser(userId);
    }

    /**
     * process a file and persist the transactions
     * @param s3FilePath filePath s3 file path
     * @param userId userId to associate the transactions with
     */
    @Operation(summary = "Process a file and persist the transactions")
    @ApiResponse(responseCode = "200", description = "Processed the file and persisted the transactions")
    @PostMapping("/{userId}")   
    public int processByFile(@PathVariable("userId") Long userId, @RequestParam("s3FilePath") String s3FilePath) {
        return transactionService.processTransactionFile(s3FilePath, userId);
    }
}
