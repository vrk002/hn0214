package com.vk.highnote.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    public String[] loadFileLines(String filePath) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            List<String> list = new ArrayList<String>();
            while ((str = in.readLine()) != null) {
                list.add(str);
            }
            String[] stringArr = list.toArray(new String[0]);
            return stringArr;
        } catch (IOException e) {
            System.out.println("Could not load file." + e.getMessage());
        }
        return null;
    }
}
