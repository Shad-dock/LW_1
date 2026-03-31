package org.example.parser;

import org.example.model.Mission;
import org.example.model.blocks.DataBlock;
import org.example.model.blocks.EnvironmentBlock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TxtSectionParser implements IMissionParser{
    @Override
    public Mission parse(File file) throws Exception {
        MissionBuilder builder = new MissionBuilder();
        String currentSection = "";

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("[") && line.endsWith("]")) {
                    currentSection = line;
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length < 2) {
                    builder.addNote(line);
                    continue;
                }

                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (currentSection) {
                    case "[MISSION]":
                        parseMissionSection(builder, key, value);
                        break;
                    case "[CURSE]":
                        parseCurseSection(builder, key, value);
                        break;
                    case "[SORCERER]":
                        parseSorcererSection(builder, key, value);
                        break;
                    case "[TECHNIQUE]":
                        parseTechniqueSection(builder, key, value);
                        break;
                    default:
                        //builder.addNote("[" + currentSection + "] " + key + "=" + value);
                        parseAdditionalBlock(currentSection, builder, key, value);
                        break;
                }
            }
        }

        return builder.build();
    }

    private void parseMissionSection(MissionBuilder builder, String key, String value) {
        switch (key.toLowerCase()) {
            case "missionid": builder.setMissionId(value); break;
            case "date": builder.setDate(value); break;
            case "location": builder.setLocation(value); break;
            case "outcome": builder.setOutcome(value); break;
            case "damagecost":
                try {
                    builder.setDamageCost(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    builder.addNote("damageCost (не число): " + value);
                }
                break;
            case "notes": case "note": case "comment":
                builder.addNote(value);
                break;
            default:
                builder.addNote("[MISSION] " + key + "=" + value);
        }
    }

    private void parseCurseSection(MissionBuilder builder, String key, String value) {
        switch (key.toLowerCase()) {
            case "name": builder.setCurseName(value); break;
            case "threatlevel": builder.setCurseThreatLevel(value); break;
            default: builder.addNote("[CURSE] " + key + "=" + value);
        }
    }

    private void parseSorcererSection(MissionBuilder builder, String key, String value) {
        switch (key.toLowerCase()) {
            case "name": builder.addSorcerer(value, null); break;
            case "rank": builder.updateLastSorcererRank(value); break;
            default: builder.addNote("[SORCERER] " + key + "=" + value);
        }
    }

    private void parseTechniqueSection(MissionBuilder builder, String key, String value) {
        switch (key.toLowerCase()) {
            case "name": builder.addTechnique(value, null, null, 0); break;
            case "type": builder.updateLastTechniqueType(value); break;
            case "owner": builder.updateLastTechniqueOwner(value); break;
            case "damage": builder.updateLastTechniqueDamage(value); break;
            default: builder.addNote("[TECHNIQUE] " + key + "=" + value);
        }
    }

    private void parseAdditionalBlock(String currentSection, MissionBuilder builder, String key, String value){
        EnvironmentBlock block = getOrCreateEnvironmentBlock(builder);
        if(currentSection.equals("[ENVIRONMENT]")) {
            switch (key.toLowerCase()) {
                case "weather":
                    block.setWeather(value);
                    break;
                case "timeofday":
                    block.setTimeOfDay(value);
                    break;
                case "visibility":
                    block.setVisibility(value);
                    break;
                case "cursedenergydensity":
                    block.setCursedEnergyDensity(Integer.parseInt(value));
                    break;
                default:
                    builder.addNote("[ENVIRONMENT] " + key + "=" + value);
            }
        }
    }

    private EnvironmentBlock getOrCreateEnvironmentBlock(MissionBuilder builder) {
        for (DataBlock block : builder.getAdditionalBlock()) {
            if (block.getBlockName().equals("environment")) {
                return (EnvironmentBlock) block;
            }
        }
        EnvironmentBlock newBlock = new EnvironmentBlock();
        builder.addAdditionalBlock(newBlock);
        return newBlock;
    }

    @Override
    public boolean support(File file) {
        if (!file.getName().toLowerCase().endsWith(".txt")) return false;
        return startsWithMissionSection(file);
    }

    private boolean startsWithMissionSection(File file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                return line.equals("[MISSION]");
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
