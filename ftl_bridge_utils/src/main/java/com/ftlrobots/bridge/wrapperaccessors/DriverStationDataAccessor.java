package com.ftlrobots.bridge.wrapperaccessors;

public interface DriverStationDataAccessor {
    public static final double kDefaultLoopPeriod = 0.02;

    void setDisabled(boolean disabled);
    void setAutonomous(boolean autonomous);
    double getMatchTime();
    void waitForProgramToStart();

    default void waitForNextUpdateLoop() {
        waitForNextUpdateLoop(kDefaultLoopPeriod);
    }

    void waitForNextUpdateLoop(double updatePeriod);

    enum MatchType {
        None, Practice, Qualification, Elimination
    }

    void setMatchInfo(String eventName, MatchType matchType, int matchNumber, int replayNumber, String gameSpecificMessage);

    void setJoystickInformation(int joystick, float[] axisValues, short[] povValues, int buttonCount, int buttonMask);

    double getTimeSinceEnabled();
}