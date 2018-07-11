package com.ftlrobots.bridge.modulewrapper.wpi;

import com.ftlrobots.bridge.modulewrapper.SensorWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IAnalogInWrapper;

import edu.wpi.first.wpilibj.sim.AnalogInSim;
import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public class WpiAnalogInWrapper extends SensorWrapper implements IAnalogInWrapper, NotifyCallback {
    private final AnalogInSim mWpiSimulator;

    public WpiAnalogInWrapper(int port) {
        super("Analog In " + port);
        mWpiSimulator = new AnalogInSim(port);
    }

    @Override
    public void callback(String callbackType, SimValue halValue) {
        System.out.println("[WpiAnalogInWrapper] Callback " + callbackType + " not supported");
    }

    @Override
    public void setVoltage(double voltage) {
        mWpiSimulator.setVoltage(voltage);
    }

    @Override
    public double getVoltage() {
        return mWpiSimulator.getVoltage();
    }
}