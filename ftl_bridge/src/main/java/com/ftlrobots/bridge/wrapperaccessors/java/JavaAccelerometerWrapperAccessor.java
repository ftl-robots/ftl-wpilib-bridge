package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.Map;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.interfaces.IAccelerometerWrapper;
import com.ftlrobots.bridge.wrapperaccessors.AccelerometerWrapperAccessor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class JavaAccelerometerWrapperAccessor extends BaseWrapperAccessor<IAccelerometerWrapper> implements AccelerometerWrapperAccessor {
    @Override
    public boolean isInitialized(int port) {
        return getValue(port).isInitialized();
    }

    @Override
    public double getAcceleration(int port) {
        return getValue(port).getAcceleration();
    }

    @Override
    public void setAcceleration(int port, double acceleration) {
        getValue(port).setAcceleration(acceleration);
    }

    @Override
    public Map<Integer, IAccelerometerWrapper> getMap() {
        return SensorActuatorRegistry.get().getAccelerometers();
    }

    @Override
    public boolean createSimulator(int port, String type) {
        // TODO implement
        return false;
    }

    @Override
    public void removeSimulator(int port) {
        try {
            getValue(port).close();
        }
        catch (Exception e) {
            LogManager.getLogger().log(Level.WARN, "Could not close simulator", e);
        }
        SensorActuatorRegistry.get().getAccelerometers().remove(port);
    }
}