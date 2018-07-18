package com.ftlrobots.bridge.wrapperaccessors;

public interface SolenoidWrapperAccessor extends IBasicSensorActuatorWrapperAccessor {
    public boolean get(int port);
}