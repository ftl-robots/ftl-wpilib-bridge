package com.ftlrobots.bridge.modulewrapper.interfaces;

public interface IAnalogInWrapper extends ISensorWrapper {
    void setVoltage(double voltage);
    double getVoltage();
}