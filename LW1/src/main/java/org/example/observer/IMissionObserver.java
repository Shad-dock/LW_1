package org.example.observer;

import org.example.model.Mission;

import java.io.File;

public interface IMissionObserver {
    void onFileLoadStarted(File file);
    void onParseComplete(Mission mission);
    void onError(Exception e);
}
