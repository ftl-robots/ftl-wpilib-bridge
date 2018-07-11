package com.ftlrobots.bridge.modulewrapper.factories;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiAnalogInWrapper;
// TODO need something to simulate the Talon?

public class DefaultAnalogInWrapperFactory extends BaseWrapperFactory {

    public boolean create(int port, String type) {
        boolean success = true;

        if (WpiAnalogInWrapper.class.getName().equals(type)) {
            SensorActuatorRegistry.get().register(new WpiAnalogInWrapper(port), port);
        }
        // else if, if it's a talon, do something else
        else {
            System.out.println("Could not create analog source of type " + type);
            success = false;
        }

        return success;
    }
}