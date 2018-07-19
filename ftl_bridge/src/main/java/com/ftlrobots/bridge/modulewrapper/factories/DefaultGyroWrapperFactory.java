package com.ftlrobots.bridge.modulewrapper.factories;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiAnalogGyroWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultGyroWrapperFactory extends BaseWrapperFactory {
    private static final Logger sLogger = LogManager.getLogger(DefaultGyroWrapperFactory.class);

    public boolean create(int port, String type) {
        boolean success = true;

        if (WpiAnalogGyroWrapper.class.getName().equals(type)) {
            WpiAnalogGyroWrapper wrapper = new WpiAnalogGyroWrapper(port, "Analog Gyro");
            SensorActuatorRegistry.get().register(wrapper, port);
        }
        // else if ADXS gyro
        // else if CTRE Pigeon
        else {
            sLogger.log(Level.ERROR, "Could not create gyro of type " + type);
            success = false;
        }

        return success;
    }
}