package com.ftlrobots.bridge.modulewrapper.interfaces;

public interface IGyroWrapper extends ISensorWrapper {
    double getAngle();
    void setAngle(double angle);
}