package com.ftlrobots.bridge.jni.components;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public abstract class PortBasedNotifyCallback implements NotifyCallback {
    protected final int mPort;

    public PortBasedNotifyCallback(int index) {
        mPort = index;
    }
}