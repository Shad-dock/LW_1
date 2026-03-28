package org.example.ui;

import javax.swing.*;
import java.util.Scanner;

public class Chooser {
    private Scanner scanner;

    public Chooser(){
        this.scanner = new Scanner(System.in);
    }

    public void start(){
        while (true){
            printMenu();
            String choice = scanner.nextLine();
            switch (choice){
                case"1":
                    UI ui = new UI();
                    ui.start();
                    break;
                //переписать gui
//                case"2":
//                    SwingUtilities.invokeLater(() -> {
//                        MainFrame frame = new MainFrame();
//                        frame.setVisible(true);
//                    });
//                    return;
                case "3":
                    System.out.println("Выход..");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
    private void printMenu(){
        System.out.println("-".repeat(85));
        System.out.println(" ".repeat(30) + "Анализатор миссий");
        System.out.println("-".repeat(85));
        System.out.println("1.Консольный режим");
        System.out.println("2.GUI");
        System.out.println("3.Выход");
        System.out.println("Выберите действие: ");
    }
}
