package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ftlrobots.bridge.SensorActuatorRegistry;
import com.ftlrobots.bridge.jni.RegisterCallbacksJni;
import com.ftlrobots.bridge.modulewrapper.interfaces.ISimulatorUpdater;
import com.ftlrobots.bridge.wrapperaccessors.SimulatorDataAccessor;

import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
import edu.wpi.first.hal.sim.mockdata.SimulatorJNI;

public class JavaSimulatorDataAccessor implements SimulatorDataAccessor {
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
    public void setDisabled(boolean disabled) {
        DriverStationDataJNI.setEnabled(!disabled);
        DriverStationDataJNI.setDsAttached(!disabled);
        mEnabledTime = System.currentTimeMillis() * 1e-3;
    }

    @Override
    public void setAutonomous(boolean autonomous) {
        DriverStationDataJNI.setAutonomous(autonomous);
        mEnabledTime = System.currentTimeMillis() * 1e-3;
    }

    @Override
    public double getMatchTime() {
        return 0;
    }

    @Override
    public void waitForProgramToStart() {
        SimulatorJNI.waitForProgramStart();
    }

    @Override
    public void notifyNewData() {
        DriverStationDataJNI.notifyNewData();
    }

    private double mNextExpectedTime = System.nanoTime() * 1e-9;

    @Override
    public void waitForNextUpdateLoop(double updatePeriod) {
        double currentTime = System.nanoTime() * 1e-9;
        double diff = currentTime - mNextExpectedTime;
        double timeToWait = updatePeriod - diff;

        mNextExpectedTime += updatePeriod;

        try {
            if (timeToWait > 0) {
                Thread.sleep((long) (timeToWait * 1000));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        DriverStationDataJNI.notifyNewData();
    }

    @Override
    public void setMatchInfo(String eventName, MatchType matchType, int matchNumber, int replayNumner, String gameSpecificMessage) {
        // TODO
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

    @Override
    public double getTimeSinceEnabled() {
        double currentTime = System.currentTimeMillis() * 1e-3;
        return currentTime - mEnabledTime;
    }

}