package com.ftlrobots.bridge.modulewrapper.factories;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiDigitalIOWrapper;

public class DefaultDigitalIOWrapperFactory extends BaseWrapperFactory {
    public boolean create(int port, String type) {
        System.out.println("[DefaultDigitalIOWrapperFactory] Asked to create new DigitalIOWrapper of type " + type + " on port " + port);
        boolean success = true;

        if (WpiDigitalIOWrapper.class.getName().equals(type)) {
            SensorActuatorRegistry.get().register(new WpiDigitalIOWrapper(port), port);
        }
        else {
            System.out.println("Could not create digital source of type " + type);
            success = false;
        }

        return success;
    }
}