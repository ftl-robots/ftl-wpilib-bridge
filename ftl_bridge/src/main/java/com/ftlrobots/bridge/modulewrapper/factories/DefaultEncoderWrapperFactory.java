package com.ftlrobots.bridge.modulewrapper.factories;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiEncoderWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultEncoderWrapperFactory extends BaseWrapperFactory {
    private static final Logger sLogger = LogManager.getLogger(DefaultEncoderWrapperFactory.class);

    public boolean create(int port, String type) {
        boolean success = true;

        if (WpiEncoderWrapper.class.getName().equals(type)) {
            SensorActuatorRegistry.get().register(new WpiEncoderWrapper(port), port);
        }
        // else if Talon (TBD)
        else {
            sLogger.log(Level.ERROR, "Could not create encoder of type " + type);
            success = false;
        }

        return success;
    }
}