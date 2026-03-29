package org.example.report;

import org.example.model.Mission;
import org.example.model.blocks.DataBlock;
import org.example.model.enums.MissionOutcome;

public class DetailedReport implements IReport{

    @Override
    public String generate(Mission mission){
        StringBuilder sb = new StringBuilder();

        sb.append("-".repeat(70)).append("\n");
        sb.append("ДЕТАЛЬНЫЙ ОТЧЕТ\n");

        sb.append("ID миссии:  ").append(nullSafe(mission.getMissionId())).append("\n");
        sb.append("Дата:       ").append(nullSafe(mission.getDate())).append("\n");
        sb.append("Локация:    ").append(nullSafe(mission.getLocation())).append("\n");
        sb.append("Итог:       ").append(nullSafeEnum(mission.getOutcome())).append("\n");
        sb.append("Ущерб:      ").append(mission.getDamageCost()).append("\n\n");

        DataBlock curse = mission.getBlock("curse");
        if (curse != null) {
            sb.append("ПРОКЛЯТИЕ:\n");
            sb.append(" ").append(curse.getSummary()).append("\n\n");
        }

        sb.append("УЧАСТНИКИ:\n");
        if (mission.getSorcerers() != null && !mission.getSorcerers().isEmpty()) {
            for (int i = 0; i < mission.getSorcerers().size(); i++) {
                sb.append(" ").append(i + 1).append(". ").append(mission.getSorcerers().get(i)).append("\n");
            }
        } else {
            sb.append("Не указаны\n");
        }
        sb.append("\n");

        sb.append("ТЕХНИКИ:\n");
        if (mission.getTechniques() != null && !mission.getTechniques().isEmpty()) {
            for (int i = 0; i < mission.getTechniques().size(); i++) {
                sb.append("   ").append(i + 1).append(". ").append(mission.getTechniques().get(i)).append("\n");
            }
        } else {
            sb.append("Не указаны\n");
        }
        sb.append("\n");

        if (mission.getNotes() != null && !mission.getNotes().isEmpty()) {
            sb.append("ПРИМЕЧАНИЯ:\n");
            sb.append("  ").append(mission.getNotes()).append("\n\n");
        }

        if (!mission.getDataBlocks().isEmpty()) {
            sb.append("ДОПОЛНИТЕЛЬНЫЕ ДАННЫЕ:\n");
            for (DataBlock block : mission.getDataBlocks()) {
                if (!block.getBlockName().equals("curse")) {
                    sb.append(" ").append(block.getSummary()).append("\n");
                }
            }
            sb.append("\n");
        }

        sb.append("-".repeat(70));

        return sb.toString();
    }

    private String nullSafe(String value) {
        return value != null && !value.isEmpty() ? value : "Не указано";
    }

    private String nullSafeEnum(MissionOutcome outcome) {
        return outcome != null ? outcome.toString() : "Не указано";
    }
}
