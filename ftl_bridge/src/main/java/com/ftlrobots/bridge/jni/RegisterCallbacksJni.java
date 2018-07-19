package com.ftlrobots.bridge.jni;

import com.ftlrobots.bridge.jni.components.AnalogInCallbackJni;
import com.ftlrobots.bridge.jni.components.AnalogOutCallbackJni;
import com.ftlrobots.bridge.jni.components.DigitalCallbackJni;
import com.ftlrobots.bridge.jni.components.PWMCallbackJni;

import edu.wpi.first.hal.sim.mockdata.SimulatorJNI;

public final class RegisterCallbacksJni extends BaseBridgeJni {
    private RegisterCallbacksJni() {}

    public static void reset() {
        SimulatorJNI.resetHandles();

        // TODO Everything else
        AnalogInCallbackJni.reset();
        AnalogOutCallbackJni.reset();
        DigitalCallbackJni.reset();
        PWMCallbackJni.reset();
    }
}