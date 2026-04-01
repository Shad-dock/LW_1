package org.example.model.blocks;

import java.time.LocalDateTime;

public class TimeLineEventBlock implements DataBlock{
    private LocalDateTime time;
    private String type;
    private String description;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setTime(String time){
        this.time = LocalDateTime.parse(time);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getBlockName(){
        return "timeLine";
    }

    @Override
    public String getSummary(){
        return "Время события: " + time + ", тип события: " + type + ", описание: " + description;
    }
}
