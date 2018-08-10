package com.ftlrobots.link;

import java.util.List;
import java.util.Map;

import com.ftlrobots.link.LinkCoreConstants.RobotMode;
import com.ftlrobots.link.data.JoystickData;
import com.ftlrobots.link.data.MatchInfo;

public abstract class FTLLink {

    public abstract void start();

    // System Controller
    public abstract boolean getRobotDisabled();
    public abstract RobotMode getRobotMode();
    public abstract MatchInfo getMatchInfo();
    public abstract double getMatchTime();
    public abstract JoystickData[] getJoystickData();

    // Hardware Interface
    public abstract void setDigitalOutput(int port, boolean value);
    public abstract void setDigitalOutput(Map<Integer, Boolean> digitalValueMap);
    public abstract void setPWMOutput(int port, double value);
    public abstract void setPWMOutput(Map<Integer, Double> pwmValueMap);

    public abstract boolean getDigitalInput(int port);
    public abstract Map<Integer, Boolean> getDigitalInputMulti(List<Integer> portList);
    public abstract double getAnalogInput(int port);
    public abstract Map<Integer, Boolean> getAnalogInputMulti(List<Integer> portList);
}