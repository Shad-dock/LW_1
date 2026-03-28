package org.example.report;

import org.example.model.Mission;

public class StatsDecorator extends ReportDecorator{
    public StatsDecorator(IReport wrapped){
        super(wrapped);
    }

    @Override
    public String generate(Mission mission){
        return wrapped.generate(mission) + " Участников: " + mission.getSorcerers().size();
    }
}
