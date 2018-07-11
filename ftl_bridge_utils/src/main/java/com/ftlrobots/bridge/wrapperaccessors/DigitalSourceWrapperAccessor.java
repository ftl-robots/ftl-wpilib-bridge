package com.ftlrobots.bridge.wrapperaccessors;

public interface DigitalSourceWrapperAccessor extends IBasicSensorActuatorWrapperAccessor {
    public boolean getState(int port);
    public void setState(int port, boolean value);
}