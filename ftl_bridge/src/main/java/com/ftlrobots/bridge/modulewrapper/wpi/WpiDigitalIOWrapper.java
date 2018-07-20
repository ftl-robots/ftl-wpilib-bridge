package com.ftlrobots.bridge.modulewrapper.wpi;

import com.ftlrobots.bridge.modulewrapper.SensorWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IDigitalIOWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import edu.wpi.first.wpilibj.sim.DIOSim;
import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public class WpiDigitalIOWrapper extends SensorWrapper implements IDigitalIOWrapper, NotifyCallback {
    private final DIOSim mWpiSimulator;

    public WpiDigitalIOWrapper(int idx) {
        super("Digital IO " + idx);
        mWpiSimulator = new DIOSim(idx);
    }

    @Override
    public void callback(String callbackType, SimValue halValue) {
        LogManager.getLogger().log(Level.ERROR, "Callback " + callbackType + " not supported");
    }

    @Override
    public boolean get() {
        return mWpiSimulator.getValue();
    }

    @Override
    public void set(boolean state) {
        mWpiSimulator.setValue(state);
    }
}