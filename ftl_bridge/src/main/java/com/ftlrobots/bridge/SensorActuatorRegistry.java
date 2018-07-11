package com.ftlrobots.bridge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ftlrobots.bridge.modulewrapper.interfaces.IAnalogInWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IDigitalIOWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.ISimulatorUpdater;

public final class SensorActuatorRegistry {
    private static SensorActuatorRegistry sInstance = new SensorActuatorRegistry();

    private final Map<Integer, IDigitalIOWrapper> mDigitalSourceWrapperMap = new HashMap<>();
    private final Map<Integer, IAnalogInWrapper> mAnalogInWrapperMap = new HashMap<>();

    private final Collection<ISimulatorUpdater> mSimulatorComponents = new ArrayList<>();

    private SensorActuatorRegistry() {}

    public static SensorActuatorRegistry get() {
        return sInstance;
    }

    public <ItemType> boolean registerItem(ItemType item, int port, Map<Integer, ItemType> compMap, String message) {
        if (compMap.containsKey(port)) {
            System.out.println("Endpoint already exists for port " + port);
        }
        compMap.put(port, item);
        return true;
    }

    public boolean register(IAnalogInWrapper actuator, int port) {
        return registerItem(actuator, port, mAnalogInWrapperMap, "Analog");
    }

    public boolean register(IDigitalIOWrapper actuator, int port) {
        return registerItem(actuator, port, mDigitalSourceWrapperMap, "Digital IO");
    }

    public boolean register(ISimulatorUpdater updater) {
        mSimulatorComponents.add(updater);
        return true;
    }

    public Map<Integer, IDigitalIOWrapper> getDigitalSources() {
        return mDigitalSourceWrapperMap;
    }

    public Map<Integer, IAnalogInWrapper> getAnalogIn() {
        return mAnalogInWrapperMap;
    }

    public Collection<ISimulatorUpdater> getSimulatorComponents() {
        return mSimulatorComponents;
    }

    public void reset() {
        mDigitalSourceWrapperMap.clear();
        mAnalogInWrapperMap.clear();

        mSimulatorComponents.clear();
    }
}