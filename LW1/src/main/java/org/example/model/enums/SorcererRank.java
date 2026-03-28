package org.example.model.enums;

public enum SorcererRank {
    GRADE_4, GRADE_3, GRADE_2, GRADE_1, SPECIAL_GRADE, SEMI_GRADE_1;

    public static SorcererRank fromString(String text) {
        if (text == null) return null;
        for (SorcererRank r : values()) {
            if (r.name().equalsIgnoreCase(text)) return r;
        }
        if (text.toUpperCase().contains("SEMI")) return SEMI_GRADE_1;
        return null;
    }
}
