package org.example.parser;

import org.example.model.Mission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TxtParser implements IMissionParser{
    private Map<Integer, String> techOwnerMap = new HashMap<>();

    @Override
    public Mission parse(File file) throws Exception{
        Mission mission = new Mission();
        Mission.Curse curse = new Mission.Curse();
        StringBuilder extraNotes = new StringBuilder();
        techOwnerMap.clear();

        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))){
            String line;
            int lineNumb = 0;

            while ((line = reader.readLine()) != null){
                lineNumb++;
                line = line.trim();

                if(line.isEmpty()){
                    continue;
                }

                String[] parts = line.split(":", 2);
                if(parts.length < 2){
                    if (extraNotes.length() > 0) extraNotes.append("; ");
                    extraNotes.append("строка ").append(lineNumb).append(": ").append(line);
                    //continue;
                }

                String key = parts[0].trim().toLowerCase();
                String value = parts[1].trim();

                switch (key){
                    case "missionid":
                        mission.setMissionId(value);
                        break;

                    case "date":
                        mission.setDate(value);
                        break;

                    case "location":
                        mission.setLocation(value);
                        break;

                    case "outcome":
                        mission.setOutcome(value);
                        break;

                    case "damagecost":
                        try {
                            mission.setDamageCost(Integer.parseInt(value));
                        } catch (NumberFormatException e) {
                            extraNotes.append("; damageCost (не число): ").append(value);
                        }
                        break;

                    case "curse.name":
                        curse.setName(value);
                        break;

                    case "curse.threatlevel":
                        curse.setThreatLevel(value);
                        break;

                    default:
                        if (key.startsWith("sorcerer[")) {
                            sorcererField(key, value, mission, extraNotes);
                        }
                        else if (key.startsWith("technique[")) {
                            techniqueField(key, value, mission, extraNotes);
                        }
                        else {
                            if (extraNotes.length() > 0) extraNotes.append("; ");
                            extraNotes.append(key).append(": ").append(value);
                        }
                        break;
                }
            }
        }
        mission.setCurse(curse);
        link(mission);
        mission.setNotes(extraNotes.toString());

        return mission;

    }

    private void link(Mission mission){
        for(int i = 0; i<mission.getTechniques().size(); i++){
            Mission.Technique technique = mission.getTechniques().get(i);
            String ownerName = techOwnerMap.get(i);
            for (Mission.Sorcerer sorcerer : mission.getSorcerers()){
                if(ownerName.equals(sorcerer.getName())){
                    technique.setOwner(sorcerer);
                    break;
                }
            }
        }
    }

    @Override
    public boolean support(File file){
        return file.getName().toLowerCase().endsWith(".txt");
    }

    private void sorcererField(String key, String value, Mission mission, StringBuilder notes) {
        int idx = extractIdx(key);
        Mission.Sorcerer sorcerer = getORcreateSorcerer(mission, idx);

        if (key.endsWith("].name")) {
            sorcerer.setName(value);
        }else if (key.endsWith("].rank")){
            sorcerer.setRank(value);
        }else{
            if (notes.length() > 0){
                notes.append("; ");
            }
            notes.append(key).append(": ").append(value);
        }
    }

    private void techniqueField(String key, String value, Mission mission, StringBuilder notes) {
        int idx = extractIdx(key);
        Mission.Technique technique = getORcreateTechnique(mission, idx);

        if (key.endsWith("].name")) {
            technique.setName(value);
        }else if(key.endsWith("].type")){
            technique.setType(value);
        } else if(key.endsWith("].owner")){
            //technique.setOwner(value);
            techOwnerMap.put(idx, value);
        } else if(key.endsWith("].damage")){
            try {
                technique.setDamage(Integer.parseInt(value));
            } catch(NumberFormatException e){
                notes.append("; technique[").append(idx).append("].damage (не число): ").append(value);
            }
        } else{
            if (notes.length() > 0) notes.append("; ");
            notes.append(key).append(": ").append(value);
        }
    }

    private int extractIdx(String key){
        int start = key.indexOf("[") + 1;
        int finish = key.indexOf("]");
        try{
            return Integer.parseInt(key.substring(start, finish));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Mission.Sorcerer getORcreateSorcerer(Mission mission, int idx){
        while (mission.getSorcerers().size() <= idx) {
            mission.addSorcerer(new Mission.Sorcerer());
        }
        return mission.getSorcerers().get(idx);
    }

    private Mission.Technique getORcreateTechnique(Mission mission, int idx) {
        while (mission.getTechniques().size() <= idx) {
            mission.addTechnique(new Mission.Technique());
        }
        return mission.getTechniques().get(idx);
    }
}
