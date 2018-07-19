package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.Map;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.factories.DefaultAnalogOutWrapperFactory;
import com.ftlrobots.bridge.modulewrapper.interfaces.IAnalogOutWrapper;
import com.ftlrobots.bridge.wrapperaccessors.AnalogSourceWrapperAccessor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class JavaAnalogOutWrapperAccessor extends BaseWrapperAccessor<IAnalogOutWrapper> implements AnalogSourceWrapperAccessor {
    private final DefaultAnalogOutWrapperFactory mFactory;

    public JavaAnalogOutWrapperAccessor() {
        mFactory = new DefaultAnalogOutWrapperFactory();
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
        catch(Exception e) {
            LogManager.getLogger().log(Level.WARN, "Could not close simulator", e);
        }
        SensorActuatorRegistry.get().getAnalogOut().remove(port);
    }

    @Override
    protected Map<Integer, IAnalogOutWrapper> getMap() {
        return SensorActuatorRegistry.get().getAnalogOut();
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