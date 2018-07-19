package com.ftlrobots.bridge.modulewrapper.factories;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiPWMWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultPWMWrapperFactory extends BaseWrapperFactory {
    private static final Logger sLogger = LogManager.getLogger(DefaultPWMWrapperFactory.class);

    public boolean create(int port, String type) {
        boolean success = true;

        if (WpiPWMWrapper.class.getName().equals(type)) {
            SensorActuatorRegistry.get().register(new WpiPWMWrapper(port), port);
        }
        // else if talon speed controller
        else {
            sLogger.log(Level.ERROR, "Could not create speed controller of type " + type);
            success = false;
        }

        return success;
    }
}