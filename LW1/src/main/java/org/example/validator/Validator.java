package org.example.validator;

import org.example.model.Mission;

import java.util.ArrayList;

public abstract class Validator {
    protected Validator next;
    protected ArrayList<String> errors = new ArrayList<>();

    public Validator setNext(Validator next){
        this.next = next;
        return next;
    }

    public ArrayList<String> validate(Mission mission){
        errors.clear();
        validateField(mission);
        if(next != null){
            errors.addAll(next.validate(mission));
        }
        return errors;
    }

    protected abstract void validateField(Mission mission);
    protected void addError(String error){
        errors.add(error);
    }

}
