package com.ftlrobots.bridge;

import java.util.List;
import java.util.Map;

import com.ftlrobots.link.IHardwareInterface;
import com.ftlrobots.link.ISystemController;
import com.ftlrobots.link.debug.DebugLinkHardwareInterface;
import com.ftlrobots.link.debug.DebugLinkSystemController;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger sLogger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        DefaultDataAccessorFactory.initialize();

        // TODO Initialize the required hardware and system controllers
        IHardwareInterface hwIface = new DebugLinkHardwareInterface();
        ISystemController sysController = new DebugLinkSystemController();

        try {
            sLogger.log(Level.INFO, "Starting BridgeLink");
            BridgeLink bridgeLink = new BridgeLink(hwIface, sysController);
            bridgeLink.start();
        }
        catch (Exception e) {
            sLogger.log(Level.FATAL, e);
            System.exit(-1);
        }
    }
}