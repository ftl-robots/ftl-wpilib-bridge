package com.ftlrobots.bridge.wrapperaccessors;

import java.util.Collection;

/**
 * Interface for an accessor to control the current robot state
 * TODO: This should get renamed to BridgeControlDataAccessor or something similar
 */
public interface SimulatorDataAccessor {
    public static final double sDEFAULT_LOOP_PERIOD = 0.02;

    String getNativeBuildVersion();

    void reset();

    Collection<Object> getSimulatorComponentConfigs();

    void setDisabled(boolean disabled);

    void setAutonomous(boolean autonomous);

    double getMatchTime();

    void waitForProgramToStart();

    // TODO - Decide if we need to use this, or can just depend on getting updates
    // via network
    default void waitForNextUpdateLoop() {
        waitForNextUpdateLoop(0.02);
    }

    void waitForNextUpdateLoop(double updatePeriod);

    void notifyNewData();

    enum MatchType {
        None, Practice, Qualification, Elimination
    }

    void setMatchInfo(String eventName, MatchType matchType, int matchNumber, int replayNumner, String gameSpecificMessage);

    void removeSimulatorComponent(Object comp);

    double getTimeSinceEnabled();


}