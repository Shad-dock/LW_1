package org.example.validator;

import org.example.model.Mission;

public class DateValidator extends Validator{
    @Override
    protected void validateField(Mission mission){
        if(mission.getDate() == null || mission.getDate().isEmpty()){
            addError("Отсутствует дата миссии");
        }
    }
}
