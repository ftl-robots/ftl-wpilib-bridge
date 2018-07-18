package com.ftlrobots.bridge.wrapperaccessors;

import java.util.Collection;
import java.util.Map;

public interface SPIWrapperAccessor {
    Map<Integer, String> getSPIWrapperTypes();
    boolean createSPISimulator(int port, String type);
    Collection<String> getAvailableSPISimulators();
}