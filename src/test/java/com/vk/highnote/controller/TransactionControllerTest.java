package com.vk.highnote.controller;

import com.vk.highnote.model.UserTransaction;
import com.vk.highnote.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void findAllTransactions() throws Exception {
        List<UserTransaction> transactions = Arrays.asList(
            UserTransaction.builder().build(),
            UserTransaction.builder().build()
        );
        
        when(transactionService.findAllTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk());
    }   
} 