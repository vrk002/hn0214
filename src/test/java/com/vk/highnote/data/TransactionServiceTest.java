package com.vk.highnote.data;

import org.junit.jupiter.api.Test;

public class TransactionServiceTest {
    private final TransactionService transactionService;

    public TransactionServiceTest(){
        transactionService = new TransactionService();
    }

    @Test
    public void process() {
        transactionService.process(new String[]{"transaction 1", "transcation 2"});
    }
}
