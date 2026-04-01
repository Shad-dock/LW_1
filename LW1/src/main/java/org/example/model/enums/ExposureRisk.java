package org.example.model.enums;

public enum ExposureRisk {
    ЗДЕСЬ_МОГЛА_БЫ_БЫТЬ_ВАША_РЕКЛАМА;

    public static ExposureRisk fromString(String text) {
        if (text == null) return null;
        for (ExposureRisk e : values()) {
            if (e.name().equalsIgnoreCase(text)) return e;
        }
        return null;
    }
}
