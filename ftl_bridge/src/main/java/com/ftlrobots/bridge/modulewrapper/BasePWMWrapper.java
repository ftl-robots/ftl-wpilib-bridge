package com.ftlrobots.bridge.modulewrapper;

import com.ftlrobots.bridge.modulewrapper.interfaces.IPWMWrapper;

public class BasePWMWrapper extends SensorWrapper implements IPWMWrapper {
    protected final int mHandle;

    private double mPwmValue = 0;

    public BasePWMWrapper(int handle, String name) {
        super(name);

        mHandle = handle;

    }

    @Override
    public void set(double speed) {
        mPwmValue = speed;
    }

    @Override
    public double get() {
        return mPwmValue;
    }

    @Override
    public int getHandle() {
        return mHandle;
    }
}