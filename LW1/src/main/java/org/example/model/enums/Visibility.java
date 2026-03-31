package org.example.model.enums;

public enum Visibility {
    LOW, MEDIUM, HIGH;

    public static Visibility fromString(String text){
        if(text == null) return null;
        for(Visibility v : values()){
            if(v.name().equalsIgnoreCase(text)) return v;
        }
        return null;
    }
}
