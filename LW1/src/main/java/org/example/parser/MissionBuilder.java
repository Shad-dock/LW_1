package org.example.parser;

import org.example.model.Mission;
import org.example.model.blocks.DataBlock;
import org.example.model.enums.MissionOutcome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MissionBuilder {
    private String missionId;
    private String date;
    private String location;
    private MissionOutcome outcome;
    private int damageCost;
    private String notes;

    private String curseName;
    private String curseThreatLevel;

    private StringBuilder notesBuilder = new StringBuilder();

    private ArrayList<Mission.Sorcerer> tempSorcerers = new ArrayList<>();

    private ArrayList<Mission.Technique> tempTechniques = new ArrayList<>();
    private Map<Integer, String> techniqueOwnerMap = new HashMap<>();

    private ArrayList<DataBlock> additionalBlocks = new ArrayList<>();

    public void setMissionId(String id){
        this.missionId = id;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setLocation(String loc){
        this.location = loc;
    }
    public void setOutcome(String outcome){
        this.outcome = MissionOutcome.fromString(outcome);
    }
    public void setDamageCost(int cost){
        this.damageCost = cost;
    }
    public void setNotes(String notes){
        this.notes = notes;
    }
    public void addNote(String note) {
        if (notesBuilder.length() > 0) {
            notesBuilder.append("; ");
        }
        notesBuilder.append(note);
    }

    public void setCurseName(String name){
        this.curseName = name;
    }
    public void setCurseThreatLevel(String level){
        this.curseThreatLevel = level;
    }

    public void addSorcerer(String name, String rank){
        Mission.Sorcerer s = new Mission.Sorcerer();
        s.setName(name);
        if(rank != null){
            s.setRank(rank);
        }
        tempSorcerers.add(s);
    }
    public void updateLastSorcererRank(String rank) {
        if (!tempSorcerers.isEmpty()) {
            tempSorcerers.get(tempSorcerers.size() - 1).setRank(rank);
        }
    }
    public void updateLastSorcererName(String name) {
        if (!tempSorcerers.isEmpty()) {
            tempSorcerers.get(tempSorcerers.size() - 1).setName(name);
        }
    }

    public void addTechnique(String name, String type, String ownerName, int damage) {
        Mission.Technique t = new Mission.Technique();
        t.setName(name);
        t.setType(type);
        t.setDamage(damage);
        tempTechniques.add(t);
        techniqueOwnerMap.put(tempTechniques.size() - 1, ownerName);
    }

    public void updateLastTechniqueOwner(String owner) {
        if (!tempTechniques.isEmpty()) {
            techniqueOwnerMap.put(tempTechniques.size() - 1, owner);
        }
    }
    public void updateLastTechniqueDamage(String damage) {
        if (!tempTechniques.isEmpty()) {
            try {
                tempTechniques.get(tempTechniques.size() - 1).setDamage(Integer.parseInt(damage));
            } catch (NumberFormatException e) {}
        }
    }
    public void updateLastTechniqueType(String type) {
        if (!tempTechniques.isEmpty()) {
            tempTechniques.get(tempTechniques.size() - 1).setType(type);
        }
    }

    public void addAdditionalBlock(DataBlock block) {
        additionalBlocks.add(block);
    }

    public ArrayList<DataBlock> getAdditionalBlock(){
        return additionalBlocks;
    }

    private void linkTechniquesToSorcerers() {
        for (int i = 0; i < tempTechniques.size(); i++) {
            Mission.Technique t = tempTechniques.get(i);
            String ownerName = techniqueOwnerMap.get(i);
            if (ownerName != null) {
                for (Mission.Sorcerer s : tempSorcerers) {
                    if (ownerName.equals(s.getName())) {
                        t.setOwner(s);
                        break;
                    }
                }
            }
        }
    }

    public Mission build() {
        Mission mission = new Mission();

        mission.setMissionId(missionId);
        mission.setDate(date);
        mission.setLocation(location);
        mission.setOutcome(outcome);
        mission.setDamageCost(damageCost);
        mission.setNotes(notes);

        if (curseName != null) {
            Mission.Curse curse = new Mission.Curse();
            curse.setName(curseName);
            curse.setThreatLevel(curseThreatLevel);
            mission.setCurse(curse);
        }

        for (Mission.Sorcerer s : tempSorcerers) {
            mission.addSorcerer(s);
        }

        linkTechniquesToSorcerers();
        for (Mission.Technique t : tempTechniques) {
            mission.addTechnique(t);
        }

        for (DataBlock block : additionalBlocks) {
            mission.addDataBlock(block);
        }

        return mission;
    }

}
