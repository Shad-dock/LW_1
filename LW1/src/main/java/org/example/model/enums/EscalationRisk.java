package org.example.model.enums;

public enum EscalationRisk {
    ЗДЕСЬ_МОГЛА_БЫ_БЫТЬ_ВАША_РЕКЛАМА;

    public static EscalationRisk fromString(String text) {
        if (text == null) return null;
        for (EscalationRisk e : values()) {
            if (e.name().equalsIgnoreCase(text)) return e;
        }
        return null;
    }
}
