package com.ftlrobots.bridge.wrapperaccessors;

public interface AccelerometerWrapperAccessor extends IBasicSensorActuatorWrapperAccessor {
    public double getAcceleration(int port);
    public void setAcceleration(int port, double acceleration);
}