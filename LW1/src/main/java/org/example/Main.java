package org.example;

import org.example.model.Mission;
import org.example.parser.IMissionParser;
import org.example.parser.TxtParser;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String filePath = ("C:/Users/LEGION/Desktop/данные/Данные о миссиях. Вариант 1/Mission A.txt");
        File file = new File(filePath);

        try {
            IMissionParser parser = new TxtParser();

            Mission mission = parser.parse(file);
            mission.printReport();

        } catch (Exception e) {
            System.out.println("Ошибка при парсинге: " + e.getMessage());
            e.printStackTrace();
        }
    }
}