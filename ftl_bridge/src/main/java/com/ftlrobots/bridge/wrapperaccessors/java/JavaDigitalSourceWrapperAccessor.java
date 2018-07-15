package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.Map;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.factories.DefaultDigitalIOWrapperFactory;
import com.ftlrobots.bridge.modulewrapper.interfaces.IDigitalIOWrapper;
import com.ftlrobots.bridge.wrapperaccessors.DigitalSourceWrapperAccessor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class JavaDigitalSourceWrapperAccessor extends BaseWrapperAccessor<IDigitalIOWrapper> implements DigitalSourceWrapperAccessor {
    private final DefaultDigitalIOWrapperFactory mFactory;

    public JavaDigitalSourceWrapperAccessor() {
        mFactory = new DefaultDigitalIOWrapperFactory();
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
        SensorActuatorRegistry.get().getDigitalSources().remove(port);
    }

    @Override
    protected Map<Integer, IDigitalIOWrapper> getMap() {
        return SensorActuatorRegistry.get().getDigitalSources();
    }

    @Override
    public boolean getState(int port) {
        return getValue(port).get();
    }

    @Override
    public void setState(int port, boolean value) {
        getValue(port).set(value);
    }
}