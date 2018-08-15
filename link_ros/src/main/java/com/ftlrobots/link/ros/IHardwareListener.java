package com.ftlrobots.link.ros;

import java.util.Map;

public interface IHardwareListener {
    void onDigitalInputChanged(Map<Integer, Boolean> values);
    void onAnalogInputChanged(Map<Integer, Double> values);
}