package org.example.model.enums;

public enum MissionOutcome {
    SUCCESS, FAILURE, PARTIAL_SUCCESS;

    public static MissionOutcome fromString(String text) {
        if (text == null) return null;
        for (MissionOutcome o : values()) {
            if (o.name().equalsIgnoreCase(text)) return o;
        }
        return null;
    }
}
