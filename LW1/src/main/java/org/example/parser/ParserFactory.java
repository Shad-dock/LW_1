package org.example.parser;

import org.example.model.Mission;

import java.io.File;
import java.util.ArrayList;

public class ParserFactory {
    private static ArrayList<IMissionParser> parsers = new ArrayList<>();
    static {
        parsers.add(new TxtParser());
        parsers.add(new JSONParser());
        parsers.add(new XmlParser());
    }

    public static IMissionParser getParser(File file){
        for (IMissionParser imp : parsers){
            if(imp.support(file)){
                System.out.println(imp.support(file));
                return imp;
            }
        }
        throw new IllegalArgumentException("Неподдерживаемый формамт файла");
    }
}
