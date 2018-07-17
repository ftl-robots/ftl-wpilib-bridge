package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.hal.MatchInfoData;

public class DriverStationDataJNI {
    public static native void setEnabled(boolean b);
    public static native void setDsAttached(boolean b);
    public static native void setAutonomous(boolean autonomous);
    public static native void notifyNewData();
    public static native void setJoystickAxes(byte joystickHandle, float[] axesArray);
    public static native void setJoystickPOVs(byte joystickHandle, short[] povsArray);
    public static native void setJoystickButtons(byte joystickHandle, int buttonMask, int buttonCount);
    public static native void setMatchInfo(MatchInfoData info);
}