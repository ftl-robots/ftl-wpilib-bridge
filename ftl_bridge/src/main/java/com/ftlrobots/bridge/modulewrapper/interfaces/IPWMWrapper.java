package com.ftlrobots.bridge.modulewrapper.interfaces;

public interface IPWMWrapper extends ISensorWrapper {
    void set(double speed);
    double get();

    int getHandle();
}