package com.ftlrobots.link;

import java.util.Map;

import com.ftlrobots.link.LinkCoreConstants.RobotMode;
import com.ftlrobots.link.data.JoystickData;
import com.ftlrobots.link.data.MatchInfo;

public interface ILinkListener {
    void onDigitalInputsChanged(Map<Integer, Boolean> changedInputs);
    void onAnalogInputsChanged(Map<Integer, Double> changedInputs);
    void onRobotDisabledChanged(boolean robotDisabled);
    void onRobotModeChanged(RobotMode robotMode);
    void onMatchInfoChanged(MatchInfo matchInfo);
    void onJoystickDataChanged(JoystickData[] joystickData);
}