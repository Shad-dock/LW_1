package org.example;

import org.example.model.Mission;
import org.example.parser.IMissionParser;
import org.example.parser.JSONParser;
import org.example.parser.TxtParser;
import org.example.parser.XmlParser;
import org.example.ui.Chooser;
import org.example.ui.UI;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        //String filePath = ("C:/Users/LEGION/Desktop/данные/Данные о миссиях. Вариант 1/Mission A.xml");
//        UI ui = new UI();
//        ui.start();
        Chooser ch = new Chooser();
        ch.start();
    }
}