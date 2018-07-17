package com.ftlrobots.bridge.wrapperaccessors.java;

import com.ftlrobots.bridge.wrapperaccessors.DriverStationDataAccessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
import edu.wpi.first.hal.sim.mockdata.SimulatorJNI;
import edu.wpi.first.wpilibj.hal.MatchInfoData;

public class JavaDriverStationWrapperAccessor implements DriverStationDataAccessor {
    private static final Logger sLogger = LogManager.getLogger(JavaDriverStationWrapperAccessor.class);
    private double mEnabledTime = -1;
    private double mNextExpectedTime = System.nanoTime() * 1e-9;

    @Override
    public void waitForProgramToStart() {
        SimulatorJNI.waitForProgramStart();
    }

    @Override
    public void setDisabled(boolean disabled) {
        DriverStationDataJNI.setEnabled(!disabled);
        DriverStationDataJNI.setDsAttached(!disabled);
        mEnabledTime = System.currentTimeMillis() * 1e-3;
    }

    @Override
    public void setAutonomous(boolean autonomous) {
        DriverStationDataJNI.setAutonomous(autonomous);
        mEnabledTime = System.currentTimeMillis() * 1e-3;
    }

    @Override
    public double getMatchTime() {
        return 0;
    }

    @Override
    public void waitForNextUpdateLoop(double updatePeriod) {
        double currentTime = System.nanoTime() * 1e-9;
        double diff = currentTime - mNextExpectedTime;
        double timeToWait = updatePeriod - diff;

        mNextExpectedTime += updatePeriod;

        try {
            if (timeToWait > 0) {
                Thread.sleep((long) (timeToWait * 1000));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        DriverStationDataJNI.notifyNewData();
    }

    @Override
    public void setJoystickInformation(int joystickHandle, float[] axesArray, short[] povsArray, int buttonCount, int buttonMask) {
        DriverStationDataJNI.setJoystickAxes((byte)joystickHandle, axesArray);
        DriverStationDataJNI.setJoystickPOVs((byte)joystickHandle, povsArray);
        DriverStationDataJNI.setJoystickButtons((byte)joystickHandle, buttonMask, buttonCount);
    }

    @Override
    public void setMatchInfo(String eventName, MatchType matchType, int matchNumber, int replayNumber, String gameSpecificMessage) {
        MatchInfoData data = new MatchInfoData();
        data.eventName = eventName;
        data.matchType = matchType.ordinal();
        data.matchNumber = matchNumber;
        data.replayNumber = replayNumber;
        data.gameSpecificMessage = gameSpecificMessage;
        DriverStationDataJNI.setMatchInfo(data);
    }

    @Override
    public double getTimeSinceEnabled() {
        double currentTime = System.currentTimeMillis() * 1e-3;
        return currentTime - mEnabledTime;
    }
}