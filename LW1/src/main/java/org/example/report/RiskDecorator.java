package org.example.report;

import org.example.model.Mission;

public class RiskDecorator extends ReportDecorator{
    public RiskDecorator(IReport wrapped){
        super(wrapped);
    }

    @Override
    public String generate(Mission mission){
        int risk = mission.getDamageCost() > 3000000 ? 2 : (mission.getDamageCost() > 1000000 ? 1 : 0);
        String level = risk == 2 ? "ВЫСОКИЙ" : (risk == 1 ? "СРЕДНИЙ" : "НИЗКИЙ");
        return wrapped.generate(mission) + " Риск: " + level;
    }
}
