package org.example.parser.adapter;

import org.example.model.Mission;
import org.example.parser.IMissionParser;

import java.io.File;

public class CsvAdapter implements IMissionParser {
    @Override
    public Mission parse(File file) throws Exception{
        //заглушка!
        Mission mission = new Mission();
        mission.setMissionId("testID");
        mission.setDate("2026-03-28");
        return mission;
    }

    @Override
    public boolean support(File file){
        return file.getName().toLowerCase().endsWith(".csv");
    }
}
