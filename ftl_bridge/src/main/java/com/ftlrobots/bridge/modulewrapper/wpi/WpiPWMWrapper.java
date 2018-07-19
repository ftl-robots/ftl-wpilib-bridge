package com.ftlrobots.bridge.modulewrapper.wpi;

import com.ftlrobots.bridge.modulewrapper.BasePWMWrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import edu.wpi.first.wpilibj.sim.NotifyCallback;
import edu.wpi.first.wpilibj.sim.SimValue;

public class WpiPWMWrapper extends BasePWMWrapper implements NotifyCallback {
    public WpiPWMWrapper(int port) {
        super(port, "PWM " + port);
    }

    @Override
    public void callback(String callbackType, SimValue halValue) {
        LogManager.getLogger(WpiPWMWrapper.class).log(Level.WARN, "Callback " + callbackType + " not supported");
    }
}