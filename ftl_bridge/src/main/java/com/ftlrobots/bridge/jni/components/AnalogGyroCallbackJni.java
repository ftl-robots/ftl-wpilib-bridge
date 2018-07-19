package com.ftlrobots.bridge.jni.components;

import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.wpilibj.sim.SimValue;

public final class AnalogGyroCallbackJni {
    private static final Logger sLogger = LogManager.getLogger(AnalogGyroCallbackJni.class);

    private AnalogGyroCallbackJni() {}

    private static class AnalogGyroCallback extends PortBasedNotifyCallback {
        public AnalogGyroCallback(int index) {
            super(index);
        }

        @Override
        public void callback(String callbackType, SimValue halValue) {
            if ("Initialized".equals(callbackType)) {
                //if (!DataAccessorFactory.getInstance().get)
            }
        }
    }
}