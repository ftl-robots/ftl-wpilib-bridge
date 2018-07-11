package com.ftlrobots.bridge.modulewrapper.interfaces;

public interface ISensorWrapper extends AutoCloseable {
    boolean isInitialized();
    void setInitialized(boolean initialized);
    String getName();
    void setName(String name);
}