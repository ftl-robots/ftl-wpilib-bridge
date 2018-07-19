package com.ftlrobots.bridge.modulewrapper.factories;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiAnalogOutWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultAnalogOutWrapperFactory extends BaseWrapperFactory {
    private static final Logger sLogger = LogManager.getLogger(DefaultAnalogOutWrapperFactory.class);

    public boolean create(int port, String type) {
        boolean success = true;

        if (WpiAnalogOutWrapper.class.getName().equals(type)) {
            SensorActuatorRegistry.get().register(new WpiAnalogOutWrapper(port), port);
        }
        else {
            sLogger.log(Level.ERROR, "Could not create analog source of type " + type);
            success = false;
        }

        return success;
    }
}