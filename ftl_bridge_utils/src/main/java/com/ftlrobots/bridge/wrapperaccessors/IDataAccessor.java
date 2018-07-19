package com.ftlrobots.bridge.wrapperaccessors;

/**
 * Interface for a generic DataAccessor, which provides access to the various
 * I/O accessors
 */
public interface IDataAccessor {
    String getAccessorType();

    AccelerometerWrapperAccessor getAccelerometerAccessor();

    GyroWrapperAccessor getGyroAccessor();

    AnalogSourceWrapperAccessor getAnalogInAccessor();

    AnalogSourceWrapperAccessor getAnalogOutAccessor();

    DigitalSourceWrapperAccessor getDigitalAccessor();

    EncoderWrapperAccessor getEncoderAccessor();

    SpeedControllerWrapperAccessor getSpeedControllerAccessor();

    DriverStationDataAccessor getDriverStationAccessor();

    SimulatorDataAccessor getSimulatorDataAccessor();

    String getInitializationErrors();
}