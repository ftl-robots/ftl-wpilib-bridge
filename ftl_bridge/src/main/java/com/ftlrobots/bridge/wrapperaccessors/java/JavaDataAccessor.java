package com.ftlrobots.bridge.wrapperaccessors.java;

import com.ftlrobots.bridge.LogConfigurator;
import com.ftlrobots.bridge.jni.RegisterCallbacksJni;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;
import com.ftlrobots.bridge.wrapperaccessors.AnalogSourceWrapperAccessor;
import com.ftlrobots.bridge.wrapperaccessors.DigitalSourceWrapperAccessor;
import com.ftlrobots.bridge.wrapperaccessors.IDataAccessor;
import com.ftlrobots.bridge.wrapperaccessors.IBasicSensorActuatorWrapperAccessor;
import com.ftlrobots.bridge.wrapperaccessors.SimulatorDataAccessor;

public class JavaDataAccessor implements IDataAccessor {
    private final AnalogSourceWrapperAccessor mAnalogIn;
    private final DigitalSourceWrapperAccessor mDigital;
    private final SimulatorDataAccessor mSimulator;

    public JavaDataAccessor() {
        LogConfigurator.loadLog4jConfig();
        RegisterCallbacksJni.reset();

        mAnalogIn = new JavaAnalogInWrapperAccessor();
        mDigital = new JavaDigitalSourceWrapperAccessor();
        mSimulator = new JavaSimulatorDataAccessor();
    }

    @Override
    public String getAccessorType() {
        return "Java";
    }

    @Override
    public AnalogSourceWrapperAccessor getAnalogInAccessor() {
        return mAnalogIn;
    }

    @Override
    public DigitalSourceWrapperAccessor getDigitalAccessor() {
        return mDigital;
    }

    @Override
    public SimulatorDataAccessor getSimulatorDataAccessor() {
        return mSimulator;
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