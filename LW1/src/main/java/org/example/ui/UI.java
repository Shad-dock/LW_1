package org.example.ui;

import org.example.model.Mission;
import org.example.model.blocks.DataBlock;
import org.example.observer.LoggerObserver;
import org.example.observer.MissionEventManager;
import org.example.parser.IMissionParser;
import org.example.parser.ParserFactory;
import org.example.report.IReport;
import org.example.report.RiskDecorator;
import org.example.report.SimpleReport;
import org.example.report.StatsDecorator;
import org.example.validator.DateValidator;
import org.example.validator.MissionValidator;
import org.example.validator.Validator;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    private Scanner scanner;
    private MissionEventManager eventManager;

    public UI(){
        this.scanner = new Scanner(System.in);
        this.eventManager = new MissionEventManager();

        eventManager.addObserver(new LoggerObserver());
    }

    public UI(MissionEventManager eventManager) {
        this.scanner = new Scanner(System.in);
        this.eventManager = eventManager;
    }


    public void start(){
        while (true){
            printMenu();
            String choice = scanner.nextLine();

            if (choice.equals("2")) {
                System.out.println("Выход..");
                scanner.close();
                System.exit(0);
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
        System.out.println("-".repeat(85));
        System.out.println(" ".repeat(30) + "Анализатор миссий");
        System.out.println("-".repeat(85));
        System.out.println("1.Загрузить файл миссии");
        System.out.println("2.Выход");
        System.out.println("Выберите действие: ");
    }

    private void processFile(){
        System.out.println("Введите путь к файлу: ");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
//        System.out.println("Введенный путь: " + filePath);
//        System.out.println("Абсолютный путь: " + file.getAbsolutePath());
//        System.out.println("Файл существует: " + file.exists());
//        System.out.println("Это директория: " + file.isDirectory());
        if(!file.exists()){
            System.out.println("Файл не найден");
            return;
        }

        try{
            eventManager.notifyFileLoadStarted(file);
            IMissionParser parser = ParserFactory.getParser(file);
            Mission mission = parser.parse(file);
            eventManager.notifyParseComplete(mission);
            System.out.println("Файл загружен");
            //mission.printReport();

            Validator validator = new MissionValidator();
            validator.setNext(new DateValidator());

            ArrayList<String> errors = validator.validate(mission);

            if (!errors.isEmpty()) {
                System.out.println("Найдены проблемы: ");
                for (String err : errors) {
                    System.out.println(" " + err);
                }
            }

            demoComposite(mission);

            System.out.println("Выберите тип отчета:");
            System.out.println("1.Базовый");
            System.out.println("2.Со статистикой");
            System.out.println("3.С анализом рисков");
            System.out.println("4.Полный(статистика + риски)");
            System.out.print("Выбор: ");
            String reportChoice = scanner.nextLine();
            IReport report = buildReport(reportChoice);
            System.out.println(report.generate(mission));


        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при обработке файла " + e.getMessage());
        }
        System.out.println("Нажмите Enter для продолжения..");
        scanner.nextLine();
    }

    private IReport buildReport(String choice) {
        IReport base = new SimpleReport();

        switch (choice) {
            case "2":
                return new StatsDecorator(base);
            case "3":
                return new RiskDecorator(base);
            case "4":
                return new RiskDecorator(new StatsDecorator(base));
            default:
                return base;
        }
    }

    private void demoComposite(Mission mission) {
        System.out.println("ДЕМОНСТРАЦИЯ PATTERN COMPOSITE:");

        ArrayList<DataBlock> blocks = mission.getDataBlocks();
        if (blocks.isEmpty()) {
            System.out.println("Дополнительных блоков нет");
        } else {
            System.out.println("Дополнительные блоки данных:");
            for (DataBlock block : blocks) {
                System.out.println("  " + block.getBlockName() + ": " + block.getSummary());
            }
        }
    }
}
