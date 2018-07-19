package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class PWMDataJNI {
    public static native void resetData(int port);
    public static native void registerInitializedCallback(int port, NotifyCallback callback, boolean initialNotify);
    public static native void registerSpeedCallback(int port, NotifyCallback callback, boolean initialNotify);
}