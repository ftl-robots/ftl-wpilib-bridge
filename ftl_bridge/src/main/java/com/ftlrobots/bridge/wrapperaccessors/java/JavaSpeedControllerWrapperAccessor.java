package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.Map;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.factories.DefaultPWMWrapperFactory;
import com.ftlrobots.bridge.modulewrapper.interfaces.IPWMWrapper;
import com.ftlrobots.bridge.wrapperaccessors.SpeedControllerWrapperAccessor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaSpeedControllerWrapperAccessor extends BaseWrapperAccessor<IPWMWrapper> implements SpeedControllerWrapperAccessor {
    private static final Logger sLogger = LogManager.getLogger(JavaSpeedControllerWrapperAccessor.class);

    private final DefaultPWMWrapperFactory mFactory;

    public JavaSpeedControllerWrapperAccessor() {
        mFactory = new DefaultPWMWrapperFactory();
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
            sLogger.log(Level.WARN, "Could not close simulator", e);
        }
        SensorActuatorRegistry.get().getSpeedControllers().remove(port);
    }

    @Override
    protected Map<Integer, IPWMWrapper> getMap() {
        return SensorActuatorRegistry.get().getSpeedControllers();
    }

    @Override
    public double getVoltagePercentage(int port) {
        return getValue(port).get();
    }

    @Override
    public void setVoltagePercentage(int port, double value) {
        getValue(port).set(value);
    }
}