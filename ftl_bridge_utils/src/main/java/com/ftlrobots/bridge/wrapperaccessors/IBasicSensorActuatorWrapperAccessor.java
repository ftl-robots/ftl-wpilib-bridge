package com.ftlrobots.bridge.wrapperaccessors;

import java.util.List;

public interface IBasicSensorActuatorWrapperAccessor {
    boolean isInitialized(int port);

    void setName(int port, String name);

    String getName(int port);

    String getType(int port);

    public List<Integer> getPortList();

    boolean createSimulator(int port, String type);

    void removeSimulator(int port);
}