package com.ftlrobots.bridge.wrapperaccessors.java;

import com.ftlrobots.bridge.LogConfigurator;
import com.ftlrobots.bridge.jni.RegisterCallbacksJni;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;
import com.ftlrobots.bridge.wrapperaccessors.AccelerometerWrapperAccessor;
import com.ftlrobots.bridge.wrapperaccessors.AnalogSourceWrapperAccessor;
import com.ftlrobots.bridge.wrapperaccessors.DigitalSourceWrapperAccessor;
import com.ftlrobots.bridge.wrapperaccessors.DriverStationDataAccessor;
import com.ftlrobots.bridge.wrapperaccessors.EncoderWrapperAccessor;
import com.ftlrobots.bridge.wrapperaccessors.GyroWrapperAccessor;
import com.ftlrobots.bridge.wrapperaccessors.IDataAccessor;
import com.ftlrobots.bridge.wrapperaccessors.IBasicSensorActuatorWrapperAccessor;
import com.ftlrobots.bridge.wrapperaccessors.SimulatorDataAccessor;
import com.ftlrobots.bridge.wrapperaccessors.SpeedControllerWrapperAccessor;

public class JavaDataAccessor implements IDataAccessor {
    private final AccelerometerWrapperAccessor mAccelerometer;
    private final GyroWrapperAccessor mGyro;
    private final AnalogSourceWrapperAccessor mAnalogIn;
    private final AnalogSourceWrapperAccessor mAnalogOut;
    private final DigitalSourceWrapperAccessor mDigital;
    private final EncoderWrapperAccessor mEncoder;
    private final SpeedControllerWrapperAccessor mPWM;
    private final SimulatorDataAccessor mSimulator;
    private final DriverStationDataAccessor mDriverStation;

    public JavaDataAccessor() {
        LogConfigurator.loadLog4jConfig();
        RegisterCallbacksJni.reset();

        mAccelerometer = new JavaAccelerometerWrapperAccessor();
        mGyro = new JavaGyroWrapperAccessor();
        mAnalogIn = new JavaAnalogInWrapperAccessor();
        mAnalogOut = new JavaAnalogOutWrapperAccessor();
        mDigital = new JavaDigitalSourceWrapperAccessor();
        mEncoder = new JavaEncoderWrapperAccessor();
        mPWM = new JavaSpeedControllerWrapperAccessor();
        mSimulator = new JavaSimulatorDataAccessor();
        mDriverStation = new JavaDriverStationWrapperAccessor();
    }

    @Override
    public String getAccessorType() {
        return "Java";
    }

    @Override
    public AccelerometerWrapperAccessor getAccelerometerAccessor() {
        return mAccelerometer;
    }

    @Override
    public GyroWrapperAccessor getGyroAccessor() {
        return mGyro;
    }

    @Override
    public AnalogSourceWrapperAccessor getAnalogInAccessor() {
        return mAnalogIn;
    }

    @Override
    public AnalogSourceWrapperAccessor getAnalogOutAccessor() {
        return mAnalogOut;
    }

    @Override
    public DigitalSourceWrapperAccessor getDigitalAccessor() {
        return mDigital;
    }

    @Override
    public EncoderWrapperAccessor getEncoderAccessor() {
        return mEncoder;
    }

    @Override
    public SpeedControllerWrapperAccessor getSpeedControllerAccessor() {
        return mPWM;
    }

    @Override
    public SimulatorDataAccessor getSimulatorDataAccessor() {
        return mSimulator;
    }

    @Override
    public DriverStationDataAccessor getDriverStationAccessor() {
        return mDriverStation;
    }

    private String getInitializationError(String name, IBasicSensorActuatorWrapperAccessor accessor) {
        StringBuilder errorMessage = new StringBuilder(64);

        for (int port : accessor.getPortList()) {
            if (!accessor.isInitialized(port)) {
                accessor.removeSimulator(port);
                errorMessage.append("  -").append(name).append(port).append("\n");
            }
        }

        return errorMessage.toString();
    }

    @Override
    public String getInitializationErrors() {
        StringBuilder errorMessage = new StringBuilder(256);

        errorMessage
            .append(getInitializationError("Analog In", DataAccessorFactory.getInstance().getAnalogInAccessor()))
            .append(getInitializationError("Digital IO", DataAccessorFactory.getInstance().getDigitalAccessor()));

        return errorMessage.toString();
    }
}