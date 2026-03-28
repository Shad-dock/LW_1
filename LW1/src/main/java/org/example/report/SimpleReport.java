package org.example.report;

import org.example.model.Mission;

public class SimpleReport implements IReport{
    @Override
    public String generate(Mission mission){
        return "ID: " + mission.getMissionId() + ". Итог: " + mission.getOutcome();
    }
}
