package com.ftlrobots.bridge.jni.components;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiDigitalIOWrapper;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;

import edu.wpi.first.hal.sim.mockdata.DIODataJNI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class DigitalCallbackJni {
    private DigitalCallbackJni() {}

    private static class DigitalIOCallback extends PortBasedNotifyCallback {
        public DigitalIOCallback(int index) {
            super(index);
        }

        @Override
        public void callback(String callbackType, SimValue halValue) {
            if ("Initialized".equals(callbackType)) {
                System.out.println("DigitalIO(" + mPort + ") received Initialized callback. Creating Simulator");
                // ZQ: Looks like the actual hardware getting initialized is what kicks the entire process off
                if (!DataAccessorFactory.getInstance().getDigitalAccessor().getPortList().contains(mPort)) {
                    DataAccessorFactory.getInstance().getDigitalAccessor().createSimulator(mPort, WpiDigitalIOWrapper.class.getName());
                    System.out.println("Simulator on port " + mPort + " was not registered before starting the robot");
                }
                SensorActuatorRegistry.get().getDigitalSources().get(mPort).setInitialized(true);
            }
            else {
                System.out.println("Unknown Digital callback " + callbackType + " - " + halValue);
            }
        }
    }

    public static void reset() {
        for (int i = 0; i < SensorBase.kDigitalChannels; i++) {
            DIODataJNI.resetData(i);

            DigitalIOCallback callback = new DigitalIOCallback(i);
            DIODataJNI.registerInitializedCallback(i, callback, false);
        }
    }
}