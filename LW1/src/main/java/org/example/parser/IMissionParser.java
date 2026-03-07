package org.example.parser;

import org.example.model.Mission;

import java.io.File;

public interface IMissionParser {
    Mission parse(File file)  throws Exception;
    boolean support(File file);
}
