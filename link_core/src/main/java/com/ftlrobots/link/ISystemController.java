package com.ftlrobots.link;

import com.ftlrobots.link.LinkCoreConstants.RobotMode;
import com.ftlrobots.link.data.JoystickData;
import com.ftlrobots.link.data.MatchInfo;

/**
 * Interface defining a WPILib compatible System Controller
 *
 * A class implementing this interface will serve as a source of controller information, including
 * robot activation state and joystick data, among others.
 */
public interface ISystemController {
    boolean getRobotDisabled();
    RobotMode getRobotMode();
    MatchInfo getMatchInfo();
    double getMatchTime();
    JoystickData[] getJoystickData();
}