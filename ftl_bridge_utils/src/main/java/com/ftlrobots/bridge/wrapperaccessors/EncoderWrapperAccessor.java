package com.ftlrobots.bridge.wrapperaccessors;

public interface EncoderWrapperAccessor extends IBasicSensorActuatorWrapperAccessor {
    // TODO Need to hook up actual hardware
    // TODO This really should match the interface of the wpilib Encoder
    public double getDistance(int port);
    public void setDistance(int port, double distance);
}