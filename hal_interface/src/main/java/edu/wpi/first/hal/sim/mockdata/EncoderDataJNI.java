package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class EncoderDataJNI {
    public static native void resetData(int port);
    public static native void registerInitializedCallback(int port, NotifyCallback callback, boolean initialNotify);
    public static native void registerResetCallback(int port, NotifyCallback callback, boolean initialNotify);
    
    public static native int getCount(int index);
    public static native void setCount(int index, int count);
    
    public static native double getPeriod(int index);
    public static native void setPeriod(int index, double period);
    
    public static native double getDistancePerPulse(int index);
    public static native void setDistancePerPulse(int index, double distancePerPulse);

    public static native boolean getReset(int index);
    public static native void setReset(int index, boolean reset);
}