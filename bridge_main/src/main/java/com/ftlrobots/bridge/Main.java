package com.ftlrobots.bridge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger sLogger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        DefaultDataAccessorFactory.initialize();
    }
}