package org.example.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Mission;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONParser implements IMissionParser{
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public Mission parse(File file) throws Exception{
        Mission mission = new Mission();
        JsonNode root = mapper.readTree(file);

        mission.setMissionId(getJsonValue(root, "missionId"));
        mission.setDate(getJsonValue(root, "date"));
        mission.setLocation(getJsonValue(root, "location"));
        mission.setOutcome(getJsonValue(root, "outcome"));
        mission.setDamageCost(root.has("damageCost") ? root.get("damageCost").asInt() : 0);

        if(root.has("curse")){
            JsonNode curseNode = root.get("curse");
            Mission.Curse curse = new Mission.Curse();
            curse.setName(getJsonValue(curseNode, "name"));
            curse.setThreatLevel(getJsonValue(curseNode, "threatLevel"));
            mission.setCurse(curse);
        }

        if(root.has("sorcerers")){
            for(JsonNode jn : root.get("sorcerers")){
                Mission.Sorcerer sorcerer = new Mission.Sorcerer();
                sorcerer.setName(getJsonValue(jn, "name"));
                sorcerer.setRank(getJsonValue(jn, "rank"));
                mission.addSorcerer(sorcerer);
            }
        }

        if(root.has("techniques")){
            for(JsonNode jnt : root.get("techniques")){
                Mission.Technique technique = new Mission.Technique();
                technique.setName(getJsonValue(jnt, "name"));
                technique.setType(getJsonValue(jnt, "type"));
                technique.setOwner(getJsonValue(jnt, "owner"));
                technique.setDamage(jnt.has("damage") ? jnt.get("damage").asInt() : 0);
                mission.addTechnique(technique);
            }
            findAndSetNotes(root, mission);
        }

        return mission;
    }
    private void findAndSetNotes(JsonNode root, Mission mission){
        ArrayList<String> fieldNames = new ArrayList<>();
        Iterator<String> fieldIterator = root.fieldNames();
        while (fieldIterator.hasNext()) {
            String fieldName = fieldIterator.next();
            fieldNames.add(fieldName);
        }
        int techniquesIndex = fieldNames.indexOf("techniques");
        if (techniquesIndex >= 0 && techniquesIndex < fieldNames.size() - 1) {
            String nextField = fieldNames.get(techniquesIndex + 1);
            JsonNode nextFieldValue = root.get(nextField);

            if (nextFieldValue != null && nextFieldValue.isTextual()) {
                mission.setNotes(nextFieldValue.asText());
            }
        }
    }

    @Override
    public boolean support(File file){
        return file.getName().toLowerCase().endsWith(".json");
    }

    private String getJsonValue(JsonNode node, String field){
        JsonNode val = node.get(field);
        return val != null ? val.asText():null;
    }
}
