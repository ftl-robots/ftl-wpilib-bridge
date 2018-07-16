package com.ftlrobots.bridge.modulewrapper.wpi;

import com.ftlrobots.bridge.modulewrapper.BasePWMWrapper;

import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public class WpiPWMWrapper extends BasePWMWrapper implements NotifyCallback {
    public WpiPWMWrapper(int port) {
        super(port, "PWM " + port);
    }

    @Override
    public void callback(String callbackType, SimValue halValue) {
        // TODO log
    }
}