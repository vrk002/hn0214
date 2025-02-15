package com.vk.highnote.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class FileServiceTest {
    private final FileService fileService;

    public FileServiceTest() {
        fileService = new FileService();
    }

    @Test
    public void loadFileLines() {
        String[] transactions = fileService.loadFileLines("src/main/resources/static/input_vk.txt");
        System.out.println(Arrays.toString(transactions));
    }
}
