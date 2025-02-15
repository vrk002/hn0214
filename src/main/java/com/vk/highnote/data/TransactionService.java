package com.vk.highnote.data;

public class TransactionService {
    public void process(String[] transactions) {
        System.out.println("Starting Transaction Processor:");
        System.out.println("Start Inputs:");
        for (String transaction: transactions){
            System.out.println(transaction);
        }
        System.out.println("End Inputs:");
// TODO: Implement
    }
}
