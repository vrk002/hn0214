package com.vk.highnote.service;

import com.vk.highnote.model.UserTransaction;
import com.vk.highnote.repository.UserTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @BeforeEach
    public void init() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, Month.FEBRUARY, 5, 12, 0);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        UserTransaction userTransaction = UserTransaction.builder()
                .userId(2L)
                .vendor("costco")
                .product("egg roll")
                .unitPrice(1.5F)
                .totalPrice(3F)
                .quantity(2F)
                .invoiceDate(timestamp)
                .build();

        UserTransaction userTransaction2 = UserTransaction.builder()
                .userId(2L)
                .vendor("amazon")
                .product("t-shirt")
                .unitPrice(10F)
                .totalPrice(10F)
                .quantity(1F)
                .invoiceDate(timestamp)
                .build();

        userTransactionRepository.saveAll(Arrays.asList(userTransaction2, userTransaction));
    }

    @Test
    public void findAllTransactions() {
        List<UserTransaction> transactions = transactionService.findAllTransactions();
        assertEquals(2, transactions.size());
    }

    @Test
    public void process() {
//        transactionService.process("src/main/resources/static/input_vk.txt");
//        List<UserTransaction> userTransactionList = userTransactionRepository.findAll();
//        assertEquals(2, userTransactionList.size());
    }
}
