package org.example.parser;

import org.example.model.Mission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TxtColonParser implements IMissionParser{

    @Override
    public Mission parse(File file) throws Exception {
        MissionBuilder builder = new MissionBuilder();
        try(
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))){
            String line;
            int lineNumb = 0;
            int currentSorcererIdx = -1;
            int currentTechniqueIdx = -1;

            while ((line = reader.readLine()) != null){
                lineNumb++;
                line = line.trim();

                if(line.isEmpty()){
                    continue;
                }

                String[] parts = line.split(":", 2);
                if(parts.length < 2){
                    builder.addNote("строка " + lineNumb + ": " + line);
                    continue;
                }

                String key = parts[0].trim().toLowerCase();
                String value = parts[1].trim();

                switch (key){
                    case "missionid":
                        builder.setMissionId(value);
                        break;

                    case "date":
                        builder.setDate(value);
                        break;

                    case "location":
                        builder.setLocation(value);
                        break;

                    case "outcome":
                        builder.setOutcome(value);
                        break;

                    case "damagecost":
                        try {
                            builder.setDamageCost(Integer.parseInt(value));
                        } catch (NumberFormatException e) {
                            builder.addNote("; damageCost (не число): " + value);
                        }
                        break;

                    case "curse.name":
                        builder.setCurseName(value);
                        break;

                    case "curse.threatlevel":
                        builder.setCurseThreatLevel(value);
                        break;

                    case "notes":
                    case "note":
                    case "comment":
                        builder.addNote(value);
                        break;

                    case "technique.name":
                        builder.addTechnique(value, null, null, 0);
                        break;
                    case "technique.type":
                        builder.updateLastTechniqueType(value);
                        break;
                    case "technique.owner":
                        builder.updateLastTechniqueOwner(value);
                        break;
                    case "technique.damage":
                        builder.updateLastTechniqueDamage(value);
                        break;
                    default:
                        if (key.startsWith("sorcerer[")) {
                            if (key.endsWith("].name")) {
                                builder.addSorcerer(value, null);
                            } else if (key.endsWith("].rank")) {
                                builder.updateLastSorcererRank(value);
                            }
                        } else if (key.startsWith("technique[")) {
                            if (key.endsWith("].name")) {
                                builder.addTechnique(value, null, null, 0);
                            } else if (key.endsWith("].type")) {
                                builder.updateLastTechniqueType(value);
                            } else if (key.endsWith("].owner")) {
                                builder.updateLastTechniqueOwner(value);
                            } else if (key.endsWith("].damage")) {
                                builder.updateLastTechniqueDamage(value);
                            }
                        } else {
                            builder.addNote(key + ": " + value);
                        }
                        break;
                }
            }
        }
            return builder.build();

    }
    @Override
    public boolean support(File file) {
        if (!file.getName().toLowerCase().endsWith(".txt")) return false;
        return containsColonFormat(file);
    }

    private boolean containsColonFormat(File file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.contains(":") && !line.startsWith("[")) {
                    return true;
                }
                if (line.startsWith("[MISSION]")) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
