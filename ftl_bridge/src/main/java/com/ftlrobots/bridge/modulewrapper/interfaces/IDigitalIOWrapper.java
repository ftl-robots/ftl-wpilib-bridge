package com.ftlrobots.bridge.modulewrapper.interfaces;

public interface IDigitalIOWrapper extends ISensorWrapper {
    boolean get();
    void set(boolean state);
}