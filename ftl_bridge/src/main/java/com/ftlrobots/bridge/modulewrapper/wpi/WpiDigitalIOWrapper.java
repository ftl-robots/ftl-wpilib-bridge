package com.ftlrobots.bridge.modulewrapper.wpi;

import com.ftlrobots.bridge.modulewrapper.SensorWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IDigitalIOWrapper;

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
        System.out.println("[WpiDigitalIOWrapper] Callback " + callbackType + " not supported");
    }

    @Override
    public boolean get() {
        System.out.println("[WpiDigitalIOWrapper] Requested to get State from simulated DigitalIO component");
        return mWpiSimulator.getValue();
    }

    @Override
    public void set(boolean state) {
        System.out.println("[WpiDigitalIOWrapper] Setting State on simulated DigitalIO component");
        mWpiSimulator.setValue(state);
    }
}