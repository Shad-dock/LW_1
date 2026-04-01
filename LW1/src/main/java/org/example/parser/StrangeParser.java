package org.example.parser;

import org.example.model.Mission;
import org.example.model.blocks.CivilianImpactBlock;
import org.example.model.blocks.EnemyActionBlock;
import org.example.model.blocks.TimeLineEventBlock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static javax.xml.bind.DatatypeConverter.parseDateTime;

public class StrangeParser implements IMissionParser{
    @Override
    public Mission parse(File file) throws Exception {
        MissionBuilder builder = new MissionBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length == 0) continue;

                String command = parts[0];

                switch (command) {
                    case "MISSION_CREATED":
                        parseMissionCreated(builder, parts);
                        break;

                    case "CURSE_DETECTED":
                        parseCurseDetected(builder, parts);
                        break;

                    case "SORCERER_ASSIGNED":
                        parseSorcererAssigned(builder, parts);
                        break;

                    case "TECHNIQUE_USED":
                        parseTechniqueUsed(builder, parts);
                        break;

                    case "TIMELINE_EVENT":
                        parseAdditionalBlock(command, builder, parts);
                        break;

                    case "ENEMY_ACTION":
                        parseAdditionalBlock(command, builder, parts);
                        break;

                    case "CIVILIAN_IMPACT":
                        parseAdditionalBlock(command, builder, parts);
                        break;

                    case "MISSION_RESULT":
                        parseMissionResult(builder, parts);
                        break;

                    default:
                        builder.addNote("Неизвестная команда: " + line);
                        break;
                }
            }
        }

        return builder.build();
    }

    private void parseMissionCreated(MissionBuilder builder, String[] parts) {
        if (parts.length > 1) builder.setMissionId(parts[1]);
        if (parts.length > 2) builder.setDate(parts[2]);
        if (parts.length > 3) builder.setLocation(parts[3]);
    }

    private void parseCurseDetected(MissionBuilder builder, String[] parts) {
        if (parts.length > 1) builder.setCurseName(parts[1]);
        if (parts.length > 2) builder.setCurseThreatLevel(parts[2]);
    }

    private void parseSorcererAssigned(MissionBuilder builder, String[] parts) {
        String name = parts.length > 1 ? parts[1] : null;
        String rank = parts.length > 2 ? parts[2] : null;
        builder.addSorcerer(name, rank);
    }

    private void parseTechniqueUsed(MissionBuilder builder, String[] parts) {
        String name = parts.length > 1 ? parts[1] : null;
        String type = parts.length > 2 ? parts[2] : null;
        String owner = parts.length > 3 ? parts[3] : null;
        int damage = 0;
        if (parts.length > 4) {
            try {
                damage = Integer.parseInt(parts[4]);
            } catch (NumberFormatException e) {
                builder.addNote("technique damage (не число): " + parts[4]);
            }
        }
        builder.addTechnique(name, type, owner, damage);
    }

    private void parseAdditionalBlock(String command, MissionBuilder builder, String[] parts){
        switch (command){
            case "TIMELINE_EVENT":
                String timestamp = parts.length > 1 ? parts[1] : "";
                String eventType = parts.length > 2 ? parts[2] : "";
                String description = parts.length > 3 ? parts[3] : "";

                TimeLineEventBlock event = new TimeLineEventBlock();
                event.setTime(timestamp);
                event.setType(eventType);
                event.setDescription(description);
                builder.addAdditionalBlock(event);
                break;
            case "ENEMY_ACTION":
                String behavior = parts.length > 1 ? parts[1] : "";
                String targetPr = parts.length > 2 ? parts[2] : "";
                String attackPatterns = parts.length > 3 ? parts[3] : "Отсутствует";
                String mobility = parts.length > 4 ? parts[4] : "Отсутствует";
                String risk = parts.length > 5 ? parts[5] : "Отсутствует";

                EnemyActionBlock enemy = new EnemyActionBlock();
                enemy.setBehaviorType(behavior);
                enemy.setTargetPriority(targetPr);
                enemy.setAttackPatterns(attackPatterns);
                enemy.setMobility(mobility);
                enemy.setEscalationRisk(risk);
                builder.addAdditionalBlock(enemy);
                break;
            case "CIVILIAN_IMPACT":
                CivilianImpactBlock block = new CivilianImpactBlock();

                for (int i = 1; i < parts.length; i++) {
                    String[] kv = parts[i].split("=");
                    if (kv.length == 2) {
                        String key = kv[0];
                        String value = kv[1];
                        switch (key) {
                            case "evacuated":
                                block.setEvacuated(parseInt(value, builder, "evacuated"));
                                break;
                            case "injured":
                                block.setInjured(parseInt(value, builder, "injured"));
                                break;
                            case "missing":
                                block.setMissing(parseInt(value, builder, "missing"));
                                break;
                            case "exposureRisk":
                                block.setRisk(value);
                            default:
                                builder.addNote("CIVILIAN_IMPACT неизвестное поле: " + key + "=" + value);
                                break;
                        }
                    }
                }

                builder.addAdditionalBlock(block);
        }
    }

    private int parseInt(String value, MissionBuilder builder, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            builder.addNote(fieldName + " (не число): " + value);
            return 0;
        }
    }

    private void parseMissionResult(MissionBuilder builder, String[] parts) {
        if (parts.length > 1) builder.setOutcome(parts[1]);
        if (parts.length > 2) {
            String damageStr = parts[2];
            if (damageStr.contains("=")) {
                String[] kv = damageStr.split("=");
                if (kv.length == 2) {
                    try {
                        builder.setDamageCost(Integer.parseInt(kv[1]));
                    } catch (NumberFormatException e) {
                        builder.addNote("damageCost (не число): " + kv[1]);
                    }
                }
            } else {
                try {
                    builder.setDamageCost(Integer.parseInt(damageStr));
                } catch (NumberFormatException e) {
                    builder.addNote("damageCost (не число): " + damageStr);
                }
            }
        }
    }

    @Override
    public boolean support(File file) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String firstLine = reader.readLine();
            if (firstLine == null) return false;
            return firstLine.startsWith("MISSION_CREATED|") ||
                    firstLine.startsWith("MISSION_CREATED");
        } catch (Exception e) {
            return false;
        }
    }
}
