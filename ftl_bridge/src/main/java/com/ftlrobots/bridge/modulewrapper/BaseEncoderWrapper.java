package com.ftlrobots.bridge.modulewrapper;

import com.ftlrobots.bridge.modulewrapper.interfaces.IEncoderWrapper;

public class BaseEncoderWrapper extends SensorWrapper implements IEncoderWrapper {
    protected double mPosition;
    protected double mVelocity;

    public BaseEncoderWrapper(String name) {
        super(name);
    }

    @Override
    public void setPosition(double position) {
        mPosition = position;
    }

    @Override
    public void setVelocity(double velocity) {
        mVelocity = velocity;
    }

    @Override
    public double getPosition() {
        return mPosition;
    }

    @Override
    public double getVelocity() {
        return mVelocity;
    }

    @Override
    public void reset() {
        mPosition = 0;
        mVelocity = 0;
    }
}