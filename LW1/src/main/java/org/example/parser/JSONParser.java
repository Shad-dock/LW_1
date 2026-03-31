package org.example.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Mission;
import org.example.model.blocks.CurseBlock;
import org.example.model.blocks.EconomicAssessmentBlock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONParser implements IMissionParser{
    private ObjectMapper mapper = new ObjectMapper();
    MissionBuilder builder = new MissionBuilder();
    private Map<Integer, String> techOwnerMap = new HashMap<>();

    @Override
    public Mission parse(File file) throws Exception{
        //Mission mission = new Mission();
        JsonNode root = mapper.readTree(file);
        techOwnerMap.clear();

        builder.setMissionId(getJsonValue(root, "missionId"));
        builder.setDate(getJsonValue(root, "date"));
        builder.setLocation(getJsonValue(root, "location"));
        builder.setOutcome(getJsonValue(root, "outcome"));
        builder.setDamageCost(root.has("damageCost") ? root.get("damageCost").asInt() : 0);
        String notes = getJsonValue(root, "notes");
        if (notes != null) builder.addNote(notes);
        String comment = getJsonValue(root, "comment");
        if (comment != null) builder.addNote(comment);

        if(root.has("curse")){
            JsonNode curseNode = root.get("curse");
            CurseBlock curse = new CurseBlock();
            //Mission.Curse curse = new Mission.Curse();
            curse.setName(getJsonValue(curseNode, "name"));
            curse.setThreatLevel(getJsonValue(curseNode, "threatLevel"));
            builder.addAdditionalBlock(curse);
            System.out.println("[DEBUG] Добавлен блок curse");
            //mission.setCurse(curse);
        }

        if(root.has("sorcerers")){
            for(JsonNode jn : root.get("sorcerers")){
                Mission.Sorcerer sorcerer = new Mission.Sorcerer();
                String name = (getJsonValue(jn, "name"));
                String rank = (getJsonValue(jn, "rank"));
                builder.addSorcerer(name, rank);
            }
        }

        if(root.has("techniques")){
            int idx = 0;
            for(JsonNode jnt : root.get("techniques")){
                Mission.Technique technique = new Mission.Technique();
                String name = (getJsonValue(jnt, "name"));
                String type = (getJsonValue(jnt, "type"));

                String ownerName = getJsonValue(jnt, "owner");
                techOwnerMap.put(idx, ownerName);
                //technique.setOwner(getJsonValue(jnt, "owner"));
                int damage = (jnt.has("damage") ? jnt.get("damage").asInt() : 0);
                builder.addTechnique(name ,type, ownerName, damage);
                idx++;
            }
        }
        parseAdditionalBlocks(root, builder); //пример

        return builder.build();
    }

    private void parseAdditionalBlocks(JsonNode root, MissionBuilder builder) {
        // EconomicAssessment
        if (root.has("economicAssessment")) {
            JsonNode econ = root.get("economicAssessment");
            EconomicAssessmentBlock block = new EconomicAssessmentBlock();
            block.setTotalDamageCost(econ.has("totalDamageCost") ? econ.get("totalDamageCost").asInt() : 0);
            block.setRecoveryDays(econ.has("recoveryEstimateDays") ? econ.get("recoveryEstimateDays").asInt() : 0);
            builder.addAdditionalBlock(block);
        }

        // Другие блоки можно добавить здесь
    }

//    private void link(Mission mission){
//        for(int i = 0; i<mission.getTechniques().size(); i++){
//            Mission.Technique technique = mission.getTechniques().get(i);
//            String ownerName = techOwnerMap.get(i);
//            for (Mission.Sorcerer sorcerer : mission.getSorcerers()){
//                if(ownerName.equals(sorcerer.getName())){
//                    technique.setOwner(sorcerer);
//                    break;
//                }
//            }
//        }
//    }

//    private void findAndSetNotes(JsonNode root, Mission mission){
//        ArrayList<String> fieldNames = new ArrayList<>();
//        Iterator<String> fieldIterator = root.fieldNames();
//        while (fieldIterator.hasNext()) {
//            String fieldName = fieldIterator.next();
//            fieldNames.add(fieldName);
//        }
//        int techniquesIndex = fieldNames.indexOf("techniques");
//        if (techniquesIndex >= 0 && techniquesIndex < fieldNames.size() - 1) {
//            String nextField = fieldNames.get(techniquesIndex + 1);
//            JsonNode nextFieldValue = root.get(nextField);
//
//            if (nextFieldValue != null && nextFieldValue.isTextual()) {
//                mission.setNotes(nextFieldValue.asText());
//            }
//        }
//    }

    @Override
    public boolean support(File file){
        return file.getName().toLowerCase().endsWith(".json");
    }

    private String getJsonValue(JsonNode node, String field){
        JsonNode val = node.get(field);
        return val != null ? val.asText():null;
    }
}
