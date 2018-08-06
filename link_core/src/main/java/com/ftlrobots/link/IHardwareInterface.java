package com.ftlrobots.link;

import java.util.List;
import java.util.Map;

public interface IHardwareInterface {
    // Robot code -> hardware
    void setDigitalOutput(int port, boolean value);
    void setDigitalOutput(Map<Integer, Boolean> digitalValueMap);

    void setPWMOutput(int port, double value);
    void setPWMOutput(Map<Integer, Double> pwmValueMap);

    // Hardware -> Robot Code
    boolean getDigitalInput(int port);
    Map<Integer, Boolean> getDigitalInputMulti(List<Integer> portList);

    double getAnalogInput(int port);
    Map<Integer, Boolean> getAnalogInputMulti(List<Integer> portList);
}