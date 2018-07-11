package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class DIODataJNI {
    public static native void resetData(int port);
    public static native void registerInitializedCallback(int port, NotifyCallback callback, boolean initialNotify);
    public static native boolean getValue(int port);
    public static native void setValue(int port, boolean value);
}