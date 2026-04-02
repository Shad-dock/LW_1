package org.example.model.blocks;

import org.example.model.enums.EscalationRisk;
import org.example.model.enums.Mobility;

public class EnemyActionBlock implements DataBlock{
    private String behaviorType;
    private String targetPriority;
    private String attackPatterns;
    private Mobility mobility;
    private EscalationRisk escalationRisk;
    private String countermeasuresUsed;

    public String getCountermeasuresUsed() {
        return countermeasuresUsed;
    }

    public void setCountermeasuresUsed(String countermeasuresUsed) {
        this.countermeasuresUsed = countermeasuresUsed;
    }

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public String getTargetPriority() {
        return targetPriority;
    }

    public void setTargetPriority(String targetPriority) {
        this.targetPriority = targetPriority;
    }

    public String getAttackPatterns() {
        return attackPatterns;
    }

    public void setAttackPatterns(String attackPatterns) {
        this.attackPatterns = attackPatterns;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(String mobility) {
        this.mobility = Mobility.fromString(mobility);
    }

    public EscalationRisk getEscalationRisk() {
        return escalationRisk;
    }

    public void setEscalationRisk(String escalationRisk) {
        this.escalationRisk = EscalationRisk.fromString(escalationRisk);
    }

    @Override
    public String getBlockName(){
        return "enemyAction";
    }

    @Override
    public String getSummary(){
        return "Тип поведения: " + behaviorType + ", приоритет целей: " + targetPriority + "\n паттерны атак: " +
                attackPatterns + ", мобильность: " + mobility +
                ", риск эскалации: " + escalationRisk + ", контрмеры: " + countermeasuresUsed;
    }
}
