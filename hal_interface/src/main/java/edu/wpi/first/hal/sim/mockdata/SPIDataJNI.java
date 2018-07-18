package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.BufferCallback;
import edu.wpi.first.wpilibj.sim.ConstBufferCallback;
import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class SPIDataJNI {
    public static native void resetData(int port);
    public static native void registerInitializedCallback(int port, NotifyCallback callback, boolean initialNotify);
    public static native void registerReadCallback(int port, BufferCallback callback);
    public static native void registerWriteCallback(int port, ConstBufferCallback callback);
}