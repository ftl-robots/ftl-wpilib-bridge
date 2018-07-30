package com.ftlrobots.link.zeromq;


import java.util.Map;

import com.ftlrobots.link.IHardwareInterface;
import com.ftlrobots.link.ISystemController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zeromq.ZMQ;
import org.zeromq.ZThread.IAttachedRunnable;
import org.zeromq.ZContext;

public class ZMQLink implements IHardwareInterface, ISystemController {
    private static final Logger sLogger = LogManager.getLogger(ZMQLink.class);

    private ZMQ.Socket mPubSocket;
    private ZMQ.Socket mSubSocket;

    private static class OutputPublisher implements IAttachedRunnable {

    }

    public ZMQLink() {
    }

    // =================================
    // IHardwareInterface Implementation
    // =================================
    @Override
    public void setDigitalOutput(int port, boolean value) {

    }

    @Override
    public void setDigitalOutput(Map<Integer, Boolean> doutMap) {

    }

    @Override
    public void setPWMOutput(int port, double value) {

    }

    @Override
    public void setPWMOutput(Map<Integer, Double> pwmMap) {

    }

    @Override
    public boolean getDigitalInputSingle(int port) {
        return false;
    }

    @Override
    public Map<Integer, Boolean> getDigitalInputMulti() {
        return null;
    }

    @Override
    public double getAnalogInputSingle(int port) {
        return 0.0;
    }

    @Override
    public Map<Integer, Double> getAnalogInputMulti() {
        return null;
    }

    // =================================
    // ISystemController Implementation
    // =================================
    @Override
    public boolean getRobotDisabled() {
        return false;
    }

    @Override
    public RobotMode getRobotMode() {
        return RobotMode.Teleop;
    }

    @Override
    public MatchInfo getMatchInfo() {
        return null;
    }

    @Override
    public double getMatchTime() {
        return 0.0;
    }

    @Override
    public JoystickData[] getJoystickData() {
        return null;
    }
}