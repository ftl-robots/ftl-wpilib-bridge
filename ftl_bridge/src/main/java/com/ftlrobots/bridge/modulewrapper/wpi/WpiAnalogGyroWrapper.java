package com.ftlrobots.bridge.modulewrapper.wpi;

import com.ftlrobots.bridge.modulewrapper.SensorWrapper;
import com.ftlrobots.bridge.modulewrapper.interfaces.IGyroWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import edu.wpi.first.wpilibj.sim.AnalogGyroSim;
import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public class WpiAnalogGyroWrapper extends SensorWrapper implements IGyroWrapper, NotifyCallback {
    protected final AnalogGyroSim mWpiSimulator;

    public WpiAnalogGyroWrapper(int port, String name) {
        super(name);
        mWpiSimulator = new AnalogGyroSim(port);
    }

    @Override
    public void callback(String callbackType, SimValue halValue) {
        LogManager.getLogger(WpiAnalogGyroWrapper.class).log(Level.WARN, "Callback " + callbackType + " not supported");
    }

    @Override
    public void setAngle(double angle) {
        mWpiSimulator.setAngle(angle);
    }

    @Override
    public double getAngle() {
        return mWpiSimulator.getAngle();
    }
}