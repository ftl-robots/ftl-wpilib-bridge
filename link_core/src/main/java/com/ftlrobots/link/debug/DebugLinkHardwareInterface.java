package com.ftlrobots.link.debug;

import java.util.List;
import java.util.Map;

import com.ftlrobots.link.IHardwareInterface;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugLinkHardwareInterface implements IHardwareInterface {
    private static final Logger sLogger = LogManager.getLogger(DebugLinkHardwareInterface.class);

    @Override
    public void setPWMOutput(Map<Integer, Double> pwmValueMap) {
        sLogger.log(Level.INFO, "Setting PWM Outputs", pwmValueMap);
    }

    @Override
    public void setPWMOutput(int port, double value) {
        sLogger.log(Level.INFO, "Setting PWM (" + port + ") to " + value);
    }

    @Override
    public void setDigitalOutput(Map<Integer, Boolean> digitalValueMap) {
        sLogger.log(Level.INFO, "Setting Digital Outputs", digitalValueMap);
    }

    @Override
    public void setDigitalOutput(int port, boolean value) {
        sLogger.log(Level.INFO, "Setting Digital Output (" + port + ") to " + value);
    }

    @Override
    public Map<Integer, Boolean> getDigitalInputMulti(List<Integer> portList) {
        return null;
    }

    @Override
    public boolean getDigitalInput(int port) {
        return false;
    }

    @Override
    public Map<Integer, Boolean> getAnalogInputMulti(List<Integer> portList) {
        return null;
    }

    @Override
    public double getAnalogInput(int port) {
        return 0;
    }

}