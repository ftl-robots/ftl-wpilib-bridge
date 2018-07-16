package com.ftlrobots.bridge.modulewrapper.interfaces;

public interface IAnalogOutWrapper extends ISensorWrapper {
    void setVoltage(double voltage);
    double getVoltage();
}