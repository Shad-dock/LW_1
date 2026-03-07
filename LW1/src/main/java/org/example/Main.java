package org.example;

import org.example.model.Mission;
import org.example.parser.IMissionParser;
import org.example.parser.JSONParser;
import org.example.parser.TxtParser;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String filePath = ("C:/Users/LEGION/Desktop/данные/Данные о миссиях. Вариант 1/Mission A.json");
        File file = new File(filePath);

        try {
            //IMissionParser parser = new TxtParser();
            IMissionParser parser = new JSONParser();
            Mission mission = parser.parse(file);
            mission.printReport();

        } catch (Exception e) {
            System.out.println("Ошибка при парсинге: " + e.getMessage());
            e.printStackTrace();
        }
    }
}