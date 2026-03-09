package org.example.model;

import java.util.ArrayList;

public class Mission {
    private String missionId;
    private String title;
    private String date;
    private String location;
    private Curse curse;
    private ArrayList<Sorcerer> sorcerers;
    private ArrayList<Technique> techniques;
    private String outcome;
    private int damageCost;
    private String Status;
    private String notes;

    public Mission(){
        this.sorcerers = new ArrayList<>();
        this.techniques = new ArrayList<>();
        this.curse = new Curse();
    }

    public String getMissionId(){return missionId;}
    public void setMissionId(String missionId){this.missionId = missionId;}

    public String getDate() {return date;}
    public void setDate(String date){this.date = date;}

    public String getLocation() {return location;}
    public void setLocation(String location){this.location = location;}

    public String getOutcome(){return outcome;}
    public void setOutcome(String outcome){this.outcome = outcome;}

    public int getDamageCost(){return damageCost;}
    public void setDamageCost(int damageCost){this.damageCost = damageCost;}

    public Curse getCurse(){return curse;}
    public void setCurse(Curse curse){this.curse = curse;}

    public ArrayList<Sorcerer> getSorcerers(){return sorcerers;}
    public void setSorcerers(ArrayList<Sorcerer> sorcerers){this.sorcerers = sorcerers;}
    public void addSorcerer(Sorcerer sorcerer) {this.sorcerers.add(sorcerer);}

    public ArrayList<Technique> getTechniques(){return techniques;}
    public void setTechniques(ArrayList<Technique> techniques){ this.techniques = techniques;}
    public void addTechnique(Technique technique){this.techniques.add(technique);}

    public String getNotes(){return notes;}
    public void setNotes(String notes){this.notes = notes;}

    public static class Curse{
        private String name;
        private String threatLevel;

        public Curse(){}

        public Curse(String name, String threatLevel){
            this.name = name;
            this.threatLevel = threatLevel;
        }

        public String getName(){return name;}
        public void setName(String name){
            this.name = name;
        }
        public String getThreatLevel(){
            return threatLevel;
        }
        public void setThreatLevel(String threatLevel){
            this.threatLevel = threatLevel;
        }

        @Override
        public String toString(){
            return name + "; Threat Level:" + threatLevel;
        }
    }

    public static class Sorcerer{
        private String name;
        private String rank;

        public Sorcerer() {}

        public Sorcerer(String name, String rank){
            this.name = name;
            this.rank = rank;
        }

        public String getName(){return name;}
        public void setName(String name){this.name = name;}
        public String getRank(){return rank;}
        public void setRank(String rank){this.rank = rank;}

        @Override
        public String toString() {
            return "Name:" + name + "; Rank:" + rank;
        }
    }

    public static class Technique{
        private String name;
        private String type;
        //private String owner;
        private Sorcerer owner;
        private int damage;

        public Technique() {}

        public Technique(String name, String type, Sorcerer owner, int damage){
            this.name = name;
            this.type = type;
            this.owner = owner;
            this.damage = damage;
        }

        public String getName(){return name;}
        public void setName(String name){this.name = name;}
        public String getType(){return type;}
        public void setType(String type){this.type = type;}
        public Sorcerer getOwner(){return owner;}
        public void setOwner(Sorcerer owner){this.owner = owner;}
        public int getDamage(){return damage;}
        public void setDamage(int damage){this.damage = damage;}

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append("Name:").append(name).append("; Type:").append(type).append("; Owner:");
            if(owner!=null){
                sb.append(owner.getName());
            }
            sb.append("; Damage:").append(damage);
            return sb.toString();
            //return "Name:" + name + "; Type:" + type + "; Owner:" + owner + "; Damage:" + damage;
        }
    }

    private String getValue(String value){
        return !value.isEmpty() ? value : "не указано";
    }

    public void printReport(){
        System.out.println("-".repeat(85));
        System.out.println(" ".repeat(30) + "Отчет о миссии");
        System.out.println("-".repeat(85));
        System.out.println("Mission ID: " + getValue(missionId));
        System.out.println("Date: " + getValue(date));
        System.out.println("Location: " + getValue(location));

        System.out.println("Curse: " + curse);
        System.out.println("Participants: ");
        if(sorcerers != null && !sorcerers.isEmpty()) {
            for (int i = 0; i < sorcerers.size(); i++) {
                System.out.println((i+1) + ". " + sorcerers.get(i));
            }
        }else {
            System.out.println("Не указаны");
        }

        System.out.println("Techniques: ");
        if(techniques != null && !techniques.isEmpty()) {
            for (int i = 0; i < techniques.size(); i++) {
                System.out.println((i+1) + ". " + techniques.get(i));
            }
        }else {
            System.out.println("Не указаны");
        }

        System.out.println("Outcome: " + getValue(outcome));
        System.out.println("Damage Cost: " + damageCost);

        if(notes != null && !notes.isEmpty() ){
            System.out.println("-".repeat(45));
            System.out.println(" ".repeat(15) + "Notes");
            System.out.println(notes);
        }
        System.out.println("-".repeat(85));
    }
}
