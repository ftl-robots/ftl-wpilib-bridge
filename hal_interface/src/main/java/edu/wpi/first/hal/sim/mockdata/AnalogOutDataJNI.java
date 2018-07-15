package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class AnalogOutDataJNI {
    public static native double getVoltage(int index);
    public static native void setVoltage(int index, double voltage);
    public static native void resetData(int port);
    public static native void registerInitializedCallback(int port, NotifyCallback callback, boolean initialNotify);
}