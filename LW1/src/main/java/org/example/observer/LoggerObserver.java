package org.example.observer;

import org.example.model.Mission;

import java.io.File;

public class LoggerObserver implements IMissionObserver {
    @Override
    public void onFileLoadStarted(File file){
        System.out.println("[LOG] Загрузка: " + file.getName());
    }

    @Override
    public void onParseComplete(Mission mission){
        System.out.println("[LOG] Парсинг завершен: " + mission.getMissionId());
    }

    @Override
    public void onError(Exception e){
        System.err.println("[ERROR]" + e.getMessage());
    }
}
