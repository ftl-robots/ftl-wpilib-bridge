package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.Map;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.modulewrapper.factories.DefaultEncoderWrapperFactory;
import com.ftlrobots.bridge.modulewrapper.interfaces.IEncoderWrapper;
import com.ftlrobots.bridge.wrapperaccessors.EncoderWrapperAccessor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaEncoderWrapperAccessor extends BaseWrapperAccessor<IEncoderWrapper> implements EncoderWrapperAccessor {
    private static final Logger sLogger = LogManager.getLogger(JavaEncoderWrapperAccessor.class);

    private final DefaultEncoderWrapperFactory mFactory;

    public JavaEncoderWrapperAccessor() {
        mFactory = new DefaultEncoderWrapperFactory();
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
        SensorActuatorRegistry.get().getEncoders().remove(port);
    }

    @Override
    protected Map<Integer, IEncoderWrapper> getMap() {
        return SensorActuatorRegistry.get().getEncoders();
    }

    @Override
    public void setDistance(int port, double distance) {
        getValue(port).setPosition(distance);
    }

    @Override
    public double getDistance(int port) {
        return getValue(port).getPosition();
    }
}