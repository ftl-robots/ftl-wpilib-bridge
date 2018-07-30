package com.ftlrobots.link;

import java.util.Map;

/**
 * Interface defining a logical representation of actual robot hardware
 *
 * A class implementing this interface is responsible for managing the
 * interactions between the FTL Bridge, and the actual robot hardware
 * (or some other abstraction of robot hardware). This includes reading
 * sensor data, and writing output PWM data, etc.
 */
public interface IHardwareInterface {
    // Robot Code -> Hardware (output)
    void setDigitalOutput(int port, boolean value);
    void setDigitalOutput(Map<Integer, Boolean> doutMap);

    void setPWMOutput(int port, double value);
    void setPWMOutput(Map<Integer, Double> pwmMap);

    // TODO Others?

    // Hardware -> Robot Code (input)
    boolean getDigitalInputSingle(int port);
    Map<Integer, Boolean> getDigitalInputMulti();

    double getAnalogInputSingle(int port);
    Map<Integer, Double> getAnalogInputMulti();
}