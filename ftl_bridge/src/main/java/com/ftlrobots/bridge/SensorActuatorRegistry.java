package com.ftlrobots.bridge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.ftlrobots.bridge.modulewrapper.interfaces.IAccelerometerWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IAnalogInWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IAnalogOutWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IDigitalIOWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IEncoderWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IGyroWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IPWMWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.ISimulatorUpdater;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SensorActuatorRegistry {

    private static final Logger sLogger = LogManager.getLogger(SensorActuatorRegistry.class);

    private static SensorActuatorRegistry sInstance = new SensorActuatorRegistry();

    private final Map<Integer, IPWMWrapper> mSpeedControllerMap = new TreeMap<>();
    private final Map<Integer, IDigitalIOWrapper> mDigitalSourceWrapperMap = new TreeMap<>();
    private final Map<Integer, IAnalogInWrapper> mAnalogInWrapperMap = new TreeMap<>();
    private final Map<Integer, IAnalogOutWrapper> mAnalogOutWrapperMap = new TreeMap<>();
    private final Map<Integer, IEncoderWrapper> mEncoderWrapperMap = new TreeMap<>();

    private final Map<Integer, IGyroWrapper> mGyroWrapperMap = new TreeMap<>();
    private final Map<Integer, IAccelerometerWrapper> mAccelerometerWrapperMap = new TreeMap<>();
    // TODO I2C, SPI

    private final Collection<ISimulatorUpdater> mSimulatorComponents = new ArrayList<>();

    private SensorActuatorRegistry() {}

    public static SensorActuatorRegistry get() {
        return sInstance;
    }

    public <ItemType> boolean registerItem(ItemType item, int port, Map<Integer, ItemType> compMap, String message) {
        if (compMap.containsKey(port)) {
            sLogger.log(Level.WARN, "Endpoint already exists for port " + port);
        }
        compMap.put(port, item);
        return true;
    }

    public boolean register(IAnalogInWrapper actuator, int port) {
        return registerItem(actuator, port, mAnalogInWrapperMap, "Analog");
    }

    public boolean register(IAnalogOutWrapper actuator, int port) {
        return registerItem(actuator, port, mAnalogOutWrapperMap, "Analog");
    }

    public boolean register(IPWMWrapper actuator, int port) {
        return registerItem(actuator, port, mSpeedControllerMap, "Speed Controller");
    }

    public boolean register(IDigitalIOWrapper actuator, int port) {
        return registerItem(actuator, port, mDigitalSourceWrapperMap, "Digital IO");
    }

    public boolean register(IEncoderWrapper encoder, int port) {
        return registerItem(encoder, port, mEncoderWrapperMap, "Encoder");
    }

    public boolean register(IAccelerometerWrapper sensor, int port) {
        return registerItem(sensor, port, mAccelerometerWrapperMap, "Accelerometer");
    }

    public boolean register(IGyroWrapper sensor, int port) {
        return registerItem(sensor, port, mGyroWrapperMap, "Gyro");
    }

    public boolean register(ISimulatorUpdater updater) {
        mSimulatorComponents.add(updater);
        return true;
    }

    public Map<Integer, IPWMWrapper> getSpeedControllers() {
        return mSpeedControllerMap;
    }

    public Map<Integer, IDigitalIOWrapper> getDigitalSources() {
        return mDigitalSourceWrapperMap;
    }

    public Map<Integer, IAnalogInWrapper> getAnalogIn() {
        return mAnalogInWrapperMap;
    }

    public Map<Integer, IAnalogOutWrapper> getAnalogOut() {
        return mAnalogOutWrapperMap;
    }

    public Map<Integer, IEncoderWrapper> getEncoders() {
        return mEncoderWrapperMap;
    }

    public Map<Integer, IAccelerometerWrapper> getAccelerometers() {
        return mAccelerometerWrapperMap;
    }

    public Map<Integer, IGyroWrapper> getGyros() {
        return mGyroWrapperMap;
    }

    public Collection<ISimulatorUpdater> getSimulatorComponents() {
        return mSimulatorComponents;
    }

    public void reset() {
        mSpeedControllerMap.clear();
        mDigitalSourceWrapperMap.clear();
        mAnalogInWrapperMap.clear();
        mAnalogOutWrapperMap.clear();
        mEncoderWrapperMap.clear();
        mGyroWrapperMap.clear();
        mAccelerometerWrapperMap.clear();

        mSimulatorComponents.clear();
    }
}