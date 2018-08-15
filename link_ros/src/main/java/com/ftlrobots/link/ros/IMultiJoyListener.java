package com.ftlrobots.link.ros;

import com.ftlrobots.link.data.JoystickData;

public interface IMultiJoyListener {
    void onJoystickDataReceived(JoystickData[] data);
}