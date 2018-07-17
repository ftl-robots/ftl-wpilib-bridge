package com.ftlrobots.bridge.wrapperaccessors;

import java.util.Collection;

/**
 * Interface for an accessor to control the current robot state
 * TODO: This should get renamed to BridgeControlDataAccessor or something similar
 */
public interface SimulatorDataAccessor {
    public static final double sDEFAULT_LOOP_PERIOD = 0.02;

    String getNativeBuildVersion();

    void reset();

    Collection<Object> getSimulatorComponentConfigs();


    void removeSimulatorComponent(Object comp);

}