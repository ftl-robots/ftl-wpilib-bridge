package com.ftlrobots.bridge.wrapperaccessors;

/**
 * Interface for a generic DataAccessor, which provides access to the various
 * I/O accessors
 */
public interface IDataAccessor {
    String getAccessorType();

    AnalogSourceWrapperAccessor getAnalogInAccessor();

    DigitalSourceWrapperAccessor getDigitalAccessor();

    SimulatorDataAccessor getSimulatorDataAccessor();

    String getInitializationErrors();
}