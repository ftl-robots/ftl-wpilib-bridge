package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.jni.RegisterCallbacksJni;
import com.ftlrobots.bridge.modulewrapper.interfaces.ISimulatorUpdater;
import com.ftlrobots.bridge.wrapperaccessors.SimulatorDataAccessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaSimulatorDataAccessor implements SimulatorDataAccessor {

    private static final Logger sLogger = LogManager.getLogger(JavaSimulatorDataAccessor.class);
    private double mEnabledTime = -1;

    @Override
    public String getNativeBuildVersion() {
        return "1.0.0";
    }

    @Override
    public void reset() {
        SensorActuatorRegistry.get().reset();
        RegisterCallbacksJni.reset();
    }

    @Override
    public Collection<Object> getSimulatorComponentConfigs() {
        Collection<Object> output = new ArrayList<>();

        for (ISimulatorUpdater sim : SensorActuatorRegistry.get().getSimulatorComponents()) {
            output.add(sim.getConfig());
        }

        return output;
    }

    @Override
    public void removeSimulatorComponent(Object component) {
        for (ISimulatorUpdater sim : SensorActuatorRegistry.get().getSimulatorComponents()) {
            if (sim.getConfig().equals(component)) {
                SensorActuatorRegistry.get().getSimulatorComponents().remove(sim);
                break;
            }
        }
    }

}