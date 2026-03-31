package org.example.validator;

import org.example.model.Mission;

public class DamageCostValidator extends Validator{
    @Override
    protected void validateField(Mission mission){
        if(mission.getDamageCost() == 0){
            addError("Отсутствует damageCost");
        }
    }
}
