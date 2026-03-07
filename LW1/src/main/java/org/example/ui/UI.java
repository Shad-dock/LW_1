package org.example.ui;

import org.example.model.Mission;
import org.example.parser.IMissionParser;
import org.example.parser.ParserFactory;

import java.io.File;
import java.util.Scanner;

public class UI {
    private Scanner scanner;

    public UI(){
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        while (true){
            printMenu();
            String choice = scanner.nextLine();

            if (choice.equals("2")) {
                System.out.println("Выход..");
                break;
            }

            if (choice.equals("1")) {
                processFile();
            } else {
                System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
        scanner.close();
    }

    private void printMenu(){
        System.out.println("-".repeat(60));
        System.out.println("Анализатор миссий");
        System.out.println("-".repeat(60));
        System.out.println("1.Загрузить файл миссии");
        System.out.println("2.Выход");
        System.out.println("Выберите действие: ");
    }

    private void processFile(){
        System.out.println("Введите путь к файлу ");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        System.out.println("Введенный путь: " + filePath);
        System.out.println("Абсолютный путь: " + file.getAbsolutePath());
        System.out.println("Файл существует: " + file.exists());
        System.out.println("Это директория: " + file.isDirectory());
        if(!file.exists()){
            System.out.println("Файл не найден");
            return;
        }

        try{
            IMissionParser parser = ParserFactory.getParser(file);
            Mission mission = parser.parse(file);
            System.out.println("Файл загружен");
            mission.printReport();
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при обработке файла " + e.getMessage());
        }
    }
}
