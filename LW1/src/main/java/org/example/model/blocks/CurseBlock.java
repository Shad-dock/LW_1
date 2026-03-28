package org.example.model.blocks;

import org.example.model.enums.ThreatLevel;

public class CurseBlock implements DataBlock{
    private String name;
    private ThreatLevel threatLevel;

    public CurseBlock(){};

    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public ThreatLevel getThreatLevel(){return threatLevel;}
    public void setThreatLevel(ThreatLevel threatLevel){this.threatLevel = threatLevel;}

    public void setThreatLevel(String threatLevel){
        this.threatLevel = ThreatLevel.fromString(threatLevel);
    }

    @Override
    public String getBlockName(){
        return "curse";
    }

    @Override
    public String getSummary(){
        return name + " угроза: " + (threatLevel != null ? threatLevel : "?");
    }

}
