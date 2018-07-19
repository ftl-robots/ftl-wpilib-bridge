package com.ftlrobots.bridge.wrapperaccessors;

public interface SpeedControllerWrapperAccessor extends IBasicSensorActuatorWrapperAccessor {
    public double getVoltagePercentage(int port);
    public void setVoltagePercentage(int port, double pct);
}