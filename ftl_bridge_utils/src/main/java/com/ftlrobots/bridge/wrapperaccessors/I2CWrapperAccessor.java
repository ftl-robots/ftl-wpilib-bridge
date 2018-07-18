package com.ftlrobots.bridge.wrapperaccessors;

import java.util.Collection;
import java.util.Map;

public interface I2CWrapperAccessor {
    Map<Integer, String> getI2CWrapperTypes();
    boolean createI2CSimulator(int port, String type);
    Collection<String> getAvailableI2CSimulators();
}