package com.vk.highnote.service.gcp;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import com.vk.highnote.model.UserTransaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleCloudStorageService {
    private final Storage storage;
    public static final String BUCKET_NAME = "high_note_transactions";

    public GoogleCloudStorageService() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public String uploadFile(String filePath, String fileName) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filePath));
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, content);
        return "File uploaded to " + BUCKET_NAME + "/" + fileName;
    }

    /**
     * processes a csv file from google cloud storage using streaming
     * @param fileName input file name
     * @return a list of user transactions
     * @throws IOException
     */
    public List<UserTransaction> processLargeCsvFile(String fileName, long userId) throws IOException {
        Blob blob = storage.get(BlobId.of(BUCKET_NAME, fileName));

        if (blob == null) {
            throw new FileNotFoundException("File not found in bucket: " + fileName);
        }
        List<UserTransaction> resultList = new ArrayList<>();
        try (ReadChannel readChannel = blob.reader();
             InputStream inputStream = Channels.newInputStream(readChannel);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord record : csvParser) {
                try {
                    resultList.add(processRecord(record, userId));
                } catch (Exception e) {
                    System.out.println("Failed parsing record" + e);
                }
            }
        }
        return resultList;
    }

    /**
     * convers a CSVRecord to UserTransaction
     * @param record takes in a CSVRecord as parameter
     *               Columns: "vendor", "product", "unitPrice", "totalPrice", "quantity", "invoiceDate"
     * @return UserTransaction
     */
    public UserTransaction processRecord(CSVRecord record, long userId) {
        return UserTransaction.builder()
                .userId(userId)
                .vendor(record.get(0))
                .product(record.get(1))
                .unitPrice(Float.valueOf(record.get(2)))
                .totalPrice(Float.valueOf(record.get(3)))
                .quantity(Float.valueOf(record.get(4)))
                .invoiceDate(Timestamp.valueOf(record.get(5)))
                .build();
    }

    /**
     * processes files with manageable size. This method reads the entire file contents onto the memory
     * @param fileName transactions file name
     * @return List of UserTransactions
     * @throws IOException
     */
    public List<UserTransaction> processCsvFile(String fileName, long userId) throws IOException {
        Blob blob = storage.get(BlobId.of(BUCKET_NAME, fileName));

        if (blob == null) {
            throw new FileNotFoundException("File not found in bucket: " + fileName);
        }

        // Read CSV content as InputStream
        InputStream inputStream = new ByteArrayInputStream(blob.getContent());
        List<UserTransaction> transactions = new ArrayList<>();

        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord record : csvParser) {
                try {
                    transactions.add(processRecord(record, userId));
                } catch (Exception e) {
                    System.out.println("Failed parsing record" + e);
                }
            }
        }
        return transactions;
    }
}
