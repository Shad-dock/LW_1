package org.example.model.enums;

public enum ThreatLevel {
    LOW, MEDIUM, HIGH, SPECIAL;

    public static ThreatLevel fromString(String text){
        if(text == null) return null;
        for(ThreatLevel t : values()){
            if(t.name().equalsIgnoreCase(text)) return t;
        }
        return null;
    }
}
