package org.example.model.blocks;

import org.example.model.enums.ExposureRisk;

public class CivilianImpactBlock implements DataBlock{
    private int evacuated;
    private int injured;
    private int missing;
    private ExposureRisk risk;

    public int getEvacuated() {
        return evacuated;
    }

    public void setEvacuated(int evacuated) {
        this.evacuated = evacuated;
    }

    public int getInjured() {
        return injured;
    }

    public void setInjured(int injured) {
        this.injured = injured;
    }

    public int getMissing() {
        return missing;
    }

    public void setMissing(int missing) {
        this.missing = missing;
    }

    public ExposureRisk getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = ExposureRisk.fromString(risk);
    }

    @Override
    public String getBlockName(){
        return "civillianImpact";
    }

    @Override
    public String getSummary(){
        return "Эвакуировано: " + evacuated + ", пострадавшие: " + injured + "\n пропавшие: " +
                missing + ", риск раскрытия: " + risk;
    }
}
