package org.example.model.enums;

public enum Mobility {
    ЗДЕСЬ_МОГЛА_БЫ_БЫТЬ_ВАША_РЕКЛАМА;

    public static Mobility fromString(String text) {
        if (text == null) return null;
        for (Mobility m : values()) {
            if (m.name().equalsIgnoreCase(text)) return m;
        }
        return null;
    }
}
