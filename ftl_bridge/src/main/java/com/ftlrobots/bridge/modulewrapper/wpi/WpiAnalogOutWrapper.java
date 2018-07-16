package com.ftlrobots.bridge.modulewrapper.wpi;

import com.ftlrobots.bridge.modulewrapper.SensorWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IAnalogOutWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import edu.wpi.first.wpilibj.sim.AnalogOutSim;
import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public class WpiAnalogOutWrapper extends SensorWrapper implements IAnalogOutWrapper, NotifyCallback {
    private final AnalogOutSim mWpiSimulator;

    public WpiAnalogOutWrapper(int port) {
        super("Analog Out " + port);
        mWpiSimulator = new AnalogOutSim(port);
    }

    @Override
    public void callback(String callbackType, SimValue halValue) {
        LogManager.getLogger(WpiAnalogOutWrapper.class).log(Level.WARN, "Callback " + callbackType + " not supported");
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