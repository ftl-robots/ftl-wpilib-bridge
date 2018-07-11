package com.ftlrobots.bridge.modulewrapper;

import com.ftlrobots.bridge.modulewrapper.interfaces.ISensorWrapper;

public class SensorWrapper implements ISensorWrapper {
    protected String mName;
    protected boolean mIsInitialized;

    public SensorWrapper(String name) {
        mName = name;
    }

    @Override
    public boolean isInitialized() {
        return mIsInitialized;
    }

    @Override
    public void setInitialized(boolean initialized) {
        mIsInitialized = initialized;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public void setName(String name) {
        mName = name;
    }

    @Override
    public void close() throws Exception {
        // No-op
    }
}