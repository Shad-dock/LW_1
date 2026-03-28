package org.example.observer;

import org.example.model.Mission;

import java.io.File;
import java.util.ArrayList;

public class MissionEventManager {
    private ArrayList<IMissionObserver> observers = new ArrayList<>();

    public MissionEventManager(){}

    public void addObserver(IMissionObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IMissionObserver observer) {
        observers.remove(observer);
    }

    public void notifyFileLoadStarted(File file){
        for (IMissionObserver observer : observers){
            observer.onFileLoadStarted(file);
        }
    }

    public void notifyParseComplete(Mission mission) {
        for (IMissionObserver observer : observers) {
            observer.onParseComplete(mission);
        }
    }

    public void notifyError(Exception e) {
        for (IMissionObserver observer : observers) {
            observer.onError(e);
        }
    }

}
