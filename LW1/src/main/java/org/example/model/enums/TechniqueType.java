package org.example.model.enums;

public enum TechniqueType {
    INNATE, SHIKIGAMI, BARRIER, WEAPON, BODY, UNKNOWN;

    public static TechniqueType fromString(String text) {
        if (text == null) return UNKNOWN;
        for (TechniqueType t : values()) {
            if (t.name().equalsIgnoreCase(text)) return t;
        }
        return UNKNOWN;
    }
}
