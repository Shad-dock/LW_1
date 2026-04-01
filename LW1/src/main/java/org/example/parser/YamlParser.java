package org.example.parser;

import org.example.model.Mission;
import org.example.model.blocks.EconomicAssessmentBlock;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;

public class YamlParser implements IMissionParser{
    @Override
    public Mission parse(File file) throws Exception{
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try (FileInputStream fis = new FileInputStream(file)){
            data = yaml.load(fis);
        }

        MissionBuilder builder = new MissionBuilder();

        builder.setMissionId(getString(data,  "missionId"));
        builder.setDate(getString(data, "date"));
        builder.setLocation(getString(data, "location"));
        builder.setOutcome(getString(data, "outcome"));
        builder.setDamageCost(getInt(data, "damageCost"));

        String notes = getString(data, "notes");
        if (notes != null) builder.addNote(notes);

        String comment = getString(data, "comment");
        if (comment != null) builder.addNote(comment);

        Map<String, Object> curseData = (Map<String, Object>) data.get("curse");
        if (curseData != null) {
            builder.setCurseName(getString(curseData, "name"));
            builder.setCurseThreatLevel(getString(curseData, "threatLevel"));
        }

        ArrayList<Map<String, Object>> sorcerersList = (ArrayList<Map<String, Object>>) data.get("sorcerers");
        if (sorcerersList != null) {
            for (Map<String, Object> sData : sorcerersList) {
                String name = getString(sData, "name");
                String rank = getString(sData, "rank");
                builder.addSorcerer(name, rank);
            }
        }

        ArrayList<Map<String, Object>> techniquesList = (ArrayList<Map<String, Object>>) data.get("techniques");
        if (techniquesList != null) {
            for (Map<String, Object> tData : techniquesList) {
                String name = getString(tData, "name");
                String type = getString(tData, "type");
                String owner = getString(tData, "owner");
                int damage = getInt(tData, "damage");
                builder.addTechnique(name, type, owner, damage);
            }
        }

        parseAdditionalBlock(data, builder);
        return builder.build();
    }

    private void parseAdditionalBlock(Map<String, Object> data, MissionBuilder builder){
        Map<String, Object> econData = (Map<String, Object>) data.get("economicAssessment");
        if (econData != null) {
            EconomicAssessmentBlock block = new EconomicAssessmentBlock();
            block.setTotalDamageCost(getInt(econData, "totalDamageCost"));
            block.setInfrastructureDamage(getInt(econData, "infrastructureDamage"));
            block.setTransportDamage(getInt(econData, "transportDamage"));
            block.setCommercialDamage(getInt(econData, "commercialDamage"));
            block.setRecoveryDays(getInt(econData, "recoveryEstimateDays"));

            Object insured = econData.get("insuranceCovered");
            if (insured instanceof Boolean) {
                block.setInsuranceCovered((Boolean) insured);
            } else if (insured instanceof String) {
                block.setInsuranceCovered(Boolean.parseBoolean((String) insured));
            }

            builder.addAdditionalBlock(block);
        }
    }

    private String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString() : null;
    }

    private int getInt(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Integer) {
            return (Integer) val;
        }
        if (val instanceof String) {
            try {
                return Integer.parseInt((String) val);
            } catch (NumberFormatException e) {}
        }
        return 0;
    }

    @Override
    public boolean support(File file) {
        return file.getName().toLowerCase().endsWith(".yaml");
    }

}
