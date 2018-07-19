package com.ftlrobots.bridge.modulewrapper.interfaces;

public interface IAccelerometerWrapper extends ISensorWrapper {
    void setAcceleration(double accel);
    double getAcceleration();
}