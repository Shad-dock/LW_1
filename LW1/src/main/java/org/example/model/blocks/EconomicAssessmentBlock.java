package org.example.model.blocks;

public class EconomicAssessmentBlock implements DataBlock{
    private int totalDamageCost;
    private int recoveryDays;
    private int infrastructureDamage;
    private int transportDamage;
    private int commercialDamage;
    private boolean insuranceCovered;

    public int getTransportDamage() {
        return transportDamage;
    }

    public void setTransportDamage(int transportDamage) {
        this.transportDamage = transportDamage;
    }

    public int getInfrastructureDamage() {
        return infrastructureDamage;
    }

    public void setInfrastructureDamage(int infrastructureDamage) {
        this.infrastructureDamage = infrastructureDamage;
    }

    public int getCommercialDamage() {
        return commercialDamage;
    }

    public void setCommercialDamage(int commercialDamage) {
        this.commercialDamage = commercialDamage;
    }

    public boolean isInsuranceCovered() {
        return insuranceCovered;
    }

    public void setInsuranceCovered(boolean insuranceCovered) {
        this.insuranceCovered = insuranceCovered;
    }

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
        return "Общий ущерб: " + totalDamageCost + ", ущерб инфраструктуре: " + infrastructureDamage + "\n коммерческий ущерб: " +
                commercialDamage + " ущерб транспорту: " + transportDamage +
                "\n восстановление: " + recoveryDays + " дн" +
                ", покрытие страховки: " + insuranceCovered;
    }
}
