package com.ftlrobots.link;

import java.util.List;
import java.util.Map;

/**
 * Interface defining a logical representation of actual robot hardware
 *
 * A class implementing this interface is responsible for managing the interactions between
 * the FTL Bridge and actual robot hardware (or some other abstraction of robot hardware).
 * This includes reading sensor data, and writing output PWM data, etc.
 *
 * Potential implementationf of this interface include a Java program running on actual robot
 * hardware, with direct access to said hardware, or a simulator based abstraction of a robot.
 */
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