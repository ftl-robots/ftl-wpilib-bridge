package com.ftlrobots.bridge.wrapperaccessors;

public interface EncoderWrapperAccessor extends IBasicSensorActuatorWrapperAccessor {
    // TODO Need to hook up actual hardware
    public double getDistance(int port);
}