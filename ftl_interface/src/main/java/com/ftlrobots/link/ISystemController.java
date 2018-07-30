package com.ftlrobots.link;

/**
 * Interface defining a WPILib compatible System Controller
 * 
 * A class implementing this interface will serve as a source of 
 * controller information, including robot activation state and 
 * joystick data, among others.
 */
public interface ISystemController {
    public static enum RobotMode {
        Autonomous,
        Teleop,
        Test
    }

    public static class MatchInfo {
        String eventName;
        String matchType;
        int matchNumber;
        String additionalData;
    }

    public static class JoystickData {
        float[] axisValues;
        short[] povValues;
        boolean[] buttonValues;
    }

    boolean getRobotDisabled();
    RobotMode getRobotMode();
    MatchInfo getMatchInfo();
    double getMatchTime();
    JoystickData[] getJoystickData();
}