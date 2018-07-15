package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class AnalogGyroDataJNI {
    public static native double getAngle(int index);
    public static native void setAngle(int index, double angle);
    public static native void resetData(int port);
    public static native void registerInitializedCallback(int port, NotifyCallback callback, boolean initialNotify);
}