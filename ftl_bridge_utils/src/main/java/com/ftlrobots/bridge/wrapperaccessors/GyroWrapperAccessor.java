package com.ftlrobots.bridge.wrapperaccessors;

public interface GyroWrapperAccessor extends IBasicSensorActuatorWrapperAccessor {
    public double getAngle(int port);
    public void setAngle(int port, double angle);
    public void reset(int port);
}