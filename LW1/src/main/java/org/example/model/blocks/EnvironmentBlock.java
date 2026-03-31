package org.example.model.blocks;

import org.example.model.enums.Visibility;

public class EnvironmentBlock implements DataBlock{
    private String weather;
    private String timeOfDay;
    private Visibility visibility;
    private int cursedEnergyDensity;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = Visibility.fromString(visibility);
    }

    public int getCursedEnergyDensity() {
        return cursedEnergyDensity;
    }

    public void setCursedEnergyDensity(int cursedEnergyDensity) {
        this.cursedEnergyDensity = cursedEnergyDensity;
    }

    @Override
    public String getBlockName(){
        return "environment";
    }

    @Override
    public String getSummary(){
        return "Погода: " + weather + ", время суток: " + timeOfDay + ", видимость: " + visibility + ", плотность энергии: " + cursedEnergyDensity;
    }
}
