package com.ftlrobots.bridge.modulewrapper.wpi;

import com.ftlrobots.bridge.modulewrapper.BaseEncoderWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import edu.wpi.first.wpilibj.sim.EncoderSim;
import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public class WpiEncoderWrapper extends BaseEncoderWrapper implements NotifyCallback {
    private final EncoderSim mWpiSimulator;
    private double mDistancePerPulse;

    public WpiEncoderWrapper(int port) {
        this(port, "Encoder " + port);
    }

    public WpiEncoderWrapper(int port, String name) {
        super(name);

        mDistancePerPulse = 1;
        mWpiSimulator = new EncoderSim(port);
    }

    @Override
    public void callback(String callbackType, SimValue halValue) {
        LogManager.getLogger(WpiEncoderWrapper.class).log(Level.WARN, "Callback " + callbackType + " not supported");
    }

    @Override
    public void setPosition(double position) {
        super.setPosition(position);
        mWpiSimulator.setCount((int) (position / mDistancePerPulse));
    }

    @Override
    public void setVelocity(double velocity) {
        super.setVelocity(velocity);
        mWpiSimulator.setPeriod(1.0 / velocity);
    }

    @Override
    public void reset() {
        super.reset();
        mWpiSimulator.setReset(true);
        mWpiSimulator.setReset(false);
    }

    public void setDistancePerTick(double distPerPulse) {
        mDistancePerPulse = distPerPulse;
    }
}