package com.ftlrobots.bridge.wrapperaccessors;

public interface AnalogSourceWrapperAccessor extends IBasicSensorActuatorWrapperAccessor {
    public double getVoltage(int port);
    public void setVoltage(int port, double voltage);
}