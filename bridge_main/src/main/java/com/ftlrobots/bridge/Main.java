package com.ftlrobots.bridge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger sLogger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        DefaultDataAccessorFactory.initialize();

        try {
            sLogger.log(Level.INFO, "Starting Simulator");
            Simulator simulator = new Simulator();
            simulator.startSimulation();
        }
        catch (Exception e) {
            sLogger.log(Level.FATAL, e);
            System.exit(-1);
        }
    }
}