package com.ftlrobots.link.debug;

import com.ftlrobots.link.FTLLink;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugLink extends FTLLink {
    private static final Logger sLogger = LogManager.getLogger(DebugLink.class);

    public void start() {
        sLogger.log(Level.INFO, "Debug Link Started");
    }

    public double getMatchTime() {
        return 0.0;
    }

    protected void onDigitalOutputValuesChanged() {
        sLogger.log(Level.INFO, "Digital Outputs were changed");
    }

    protected void onPWMOutputValuesChanged() {
        sLogger.log(Level.INFO, "PWM Outputs Changed");
    }
}