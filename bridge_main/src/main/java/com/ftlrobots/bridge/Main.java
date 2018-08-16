package com.ftlrobots.bridge;

import com.ftlrobots.link.FTLLink;
import com.ftlrobots.link.debug.DebugLink;
import com.ftlrobots.link.ros.RosLink;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger sLogger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        DefaultDataAccessorFactory.initialize();

        FTLLink ftlLink = new DebugLink();

        try {
            sLogger.log(Level.INFO, "Starting BridgeLink");
            BridgeLink bridgeLink = new BridgeLink(ftlLink);
            bridgeLink.start();

            // TEST
            RosLink rosLink = new RosLink();
            rosLink.start();
        }
        catch (Exception e) {
            sLogger.log(Level.FATAL, e);
            System.exit(-1);
        }
    }
}