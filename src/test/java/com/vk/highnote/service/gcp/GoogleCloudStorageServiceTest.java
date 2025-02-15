package com.vk.highnote.service.gcp;


import com.vk.highnote.model.UserTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GoogleCloudStorageServiceTest {
    private final GoogleCloudStorageService googleCloudStorageService;

    @Autowired
    public GoogleCloudStorageServiceTest(GoogleCloudStorageService googleCloudStorageService) {
        this.googleCloudStorageService = googleCloudStorageService;
    }

    @Test
    public void processLargeCsvFile() throws IOException {
        String fileName = "transactions.csv";
        List<UserTransaction> transactionList = googleCloudStorageService.processLargeCsvFile(fileName);
        assertEquals(100, transactionList.size());
    }

    @Test
    public void processCsvFile() throws IOException {
        String fileName = "transactions.csv";
        List<UserTransaction> transactionList = googleCloudStorageService.processCsvFile(fileName);
        assertEquals(100, transactionList.size());
    }
}
