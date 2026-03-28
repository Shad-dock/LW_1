package org.example.model.blocks;

public class EconomicAssessmentBlock implements DataBlock{
    private int totalDamageCost;
    private int recoveryDays;

    public EconomicAssessmentBlock(){}

    public int getRecoveryDays() {
        return recoveryDays;
    }

    public void setRecoveryDays(int recoveryDays) {
        this.recoveryDays = recoveryDays;
    }

    public int getTotalDamageCost() {
        return totalDamageCost;
    }

    public void setTotalDamageCost(int totalDamageCost) {
        this.totalDamageCost = totalDamageCost;
    }

    @Override
    public String getBlockName(){
        return "economicAssessment";
    }

    @Override
    public String getSummary(){
        return "Ущерб: " + totalDamageCost + ", восстановление: " + recoveryDays + " дн";
    }
}
