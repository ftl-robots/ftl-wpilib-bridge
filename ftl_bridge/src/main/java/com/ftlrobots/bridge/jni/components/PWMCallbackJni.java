package com.ftlrobots.bridge.jni.components;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.wpi.WpiPWMWrapper;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.hal.sim.mockdata.PWMDataJNI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.sim.SimValue;

public final class PWMCallbackJni {
    private static final Logger sLogger = LogManager.getLogger(PWMCallbackJni.class);

    private PWMCallbackJni() {}

    private static class PWMCallback extends PortBasedNotifyCallback {
        public PWMCallback(int index) {
            super(index);
        }

        @Override
        public void callback(String callbackType, SimValue halValue) {
            if ("Initialized".equals(callbackType)) {
                if (!DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().contains(mPort)) {
                    DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(mPort, WpiPWMWrapper.class.getName());
                    sLogger.log(Level.WARN, "Simulator on port " + mPort + " was not registered before starting the robot");
                }
                SensorActuatorRegistry.get().getSpeedControllers().get(mPort).setInitialized(true);
            }
            else if ("Speed".equals(callbackType)) {
                SensorActuatorRegistry.get().getSpeedControllers().get(mPort).set(halValue.getDouble());
            }
            else if ("ZeroLatch".equals(callbackType)) {
                sLogger.log(Level.DEBUG, "ZeroLatch ignored");
            }
            else {
                sLogger.log(Level.ERROR, "Unknown PWM Callback " + callbackType + " - " + halValue);
            }
        }
    }

    public static void reset() {
        for (int i = 0; i < SensorBase.kPwmChannels; i++) {
            PWMDataJNI.resetData(i);

            PWMCallback callback = new PWMCallback(i);
            PWMDataJNI.registerInitializedCallback(i, callback, false);
            PWMDataJNI.registerSpeedCallback(i, callback, false);
        }
    }
}