package com.ftlrobots.bridge.jni.components;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiAnalogInWrapper;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.hal.sim.mockdata.AnalogInDataJNI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class AnalogInCallbackJni {
    private static final Logger sLogger = LogManager.getLogger(AnalogInCallbackJni.class);

    private AnalogInCallbackJni() {}

    private static class AnalogInCallback extends PortBasedNotifyCallback {
        public AnalogInCallback(int index) {
            super(index);
        }

        @Override
        public void callback(String callbackType, SimValue halValue) {
            if ("Initialized".equals(callbackType)) {
                System.out.println("AnalogIn(" + mPort + ") received Initialized callback");
                if (!DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList().contains(mPort)) {
                    DataAccessorFactory.getInstance().getAnalogInAccessor().createSimulator(mPort, WpiAnalogInWrapper.class.getName());
                    sLogger.log(Level.WARN, "Simulator on port " + mPort + " was not registered before starting the robot");
                }
                SensorActuatorRegistry.get().getAnalogIn().get(mPort).setInitialized(true);
            }
            else {
                System.out.println("Unknown Analog callback " + callbackType + " - " + halValue);
            }
        }
    }

    public static void reset() {
        for (int i = 0; i < SensorBase.kAnalogInputChannels; i++) {
            AnalogInDataJNI.resetData(i);

            AnalogInCallback callback = new AnalogInCallback(i);
            AnalogInDataJNI.registerInitializedCallback(i, callback, false);
        }
    }
}