package com.ftlrobots.bridge.jni.components;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiAnalogOutWrapper;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.hal.sim.mockdata.AnalogOutDataJNI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class AnalogOutCallbackJni {
    private static final Logger sLogger = LogManager.getLogger(AnalogOutCallbackJni.class);

    private AnalogOutCallbackJni() {}

    private static class AnalogOutCallback extends PortBasedNotifyCallback {
        public AnalogOutCallback(int index) {
            super(index);
        }

        @Override
        public void callback(String callbackType, SimValue halValue) {
            if ("Initialized".equals(callbackType)) {
                if (!DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList().contains(mPort)) {
                    DataAccessorFactory.getInstance().getAnalogOutAccessor().createSimulator(mPort, WpiAnalogOutWrapper.class.getName());
                    sLogger.log(Level.WARN, "Simulator on port " + mPort + " was not registered before starting the robot");
                }
                SensorActuatorRegistry.get().getAnalogOut().get(mPort).setInitialized(true);
            }
            else {
                sLogger.log(Level.ERROR, "Unknown Analog callback " + callbackType + " - " + halValue);
            }
        }
    }

    public static void reset() {
        for (int i = 0; i < SensorBase.kAnalogOutputChannels; i++) {
            AnalogOutDataJNI.resetData(i);

            AnalogOutCallback callback = new AnalogOutCallback(i);
            AnalogOutDataJNI.registerInitializedCallback(i, callback, false);
        }
    }
}