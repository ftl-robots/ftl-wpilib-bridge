package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.Map;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.factories.DefaultAnalogInWrapperFactory;
import com.ftlrobots.bridge.modulewrapper.interfaces.IAnalogInWrapper;
import com.ftlrobots.bridge.wrapperaccessors.AnalogSourceWrapperAccessor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class JavaAnalogInWrapperAccessor extends BaseWrapperAccessor<IAnalogInWrapper> implements AnalogSourceWrapperAccessor {
    private final DefaultAnalogInWrapperFactory mFactory;

    public JavaAnalogInWrapperAccessor() {
        mFactory = new DefaultAnalogInWrapperFactory();
    }

    @Override
    public boolean isInitialized(int port) {
        return getValue(port).isInitialized();
    }

    @Override
    public boolean createSimulator(int port, String type) {
        return mFactory.create(port, type);
    }

    @Override
    public void removeSimulator(int port) {
        try {
            getValue(port).close();
        }
        catch (Exception e) {
            LogManager.getLogger().log(Level.WARN, "Could not close endpoint", e);
        }
        SensorActuatorRegistry.get().getAnalogIn().remove(port);
    }

    @Override
    protected Map<Integer, IAnalogInWrapper> getMap() {
        return SensorActuatorRegistry.get().getAnalogIn();
    }

    @Override
    public double getVoltage(int port) {
        return getValue(port).getVoltage();
    }

    @Override
    public void setVoltage(int port, double voltage) {
        getValue(port).setVoltage(voltage);
    }
}