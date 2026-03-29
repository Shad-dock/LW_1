package org.example.validator;

import org.example.model.Mission;

public class MissionValidator extends Validator{
    @Override
    protected void validateField(Mission mission){
        if(mission.getMissionId() == null || mission.getMissionId().trim().isEmpty()){
            addError("Отсуттвует ID миссии");
        }
    }
}
