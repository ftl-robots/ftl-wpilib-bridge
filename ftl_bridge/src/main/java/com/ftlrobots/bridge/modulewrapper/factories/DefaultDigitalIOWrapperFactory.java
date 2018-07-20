package com.ftlrobots.bridge.modulewrapper.factories;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiDigitalIOWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class DefaultDigitalIOWrapperFactory extends BaseWrapperFactory {
    public boolean create(int port, String type) {
        boolean success = true;

        if (WpiDigitalIOWrapper.class.getName().equals(type)) {
            SensorActuatorRegistry.get().register(new WpiDigitalIOWrapper(port), port);
        }
        else {
            LogManager.getLogger().log(Level.ERROR, "Could not create digital source of type " + type);
            success = false;
        }

        return success;
    }
}