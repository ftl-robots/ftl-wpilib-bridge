package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.Map;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.factories.DefaultGyroWrapperFactory;
import com.ftlrobots.bridge.modulewrapper.interfaces.IGyroWrapper;
import com.ftlrobots.bridge.wrapperaccessors.GyroWrapperAccessor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class JavaGyroWrapperAccessor extends BaseWrapperAccessor<IGyroWrapper> implements GyroWrapperAccessor {
    private final DefaultGyroWrapperFactory mFactory;

    public JavaGyroWrapperAccessor() {
        mFactory = new DefaultGyroWrapperFactory();
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
            LogManager.getLogger().log(Level.WARN, "Could not close simulator");
        }
        SensorActuatorRegistry.get().getGyros().remove(port);
    }

    @Override
    public double getAngle(int port) {
        return getValue(port).getAngle();
    }

    @Override
    public void setAngle(int port, double angle) {
        getValue(port).setAngle(angle);
    }

    @Override
    public void reset(int port) {
        // TODO Implement
    }

    @Override
    protected Map<Integer, IGyroWrapper> getMap() {
        return SensorActuatorRegistry.get().getGyros();
    }
}