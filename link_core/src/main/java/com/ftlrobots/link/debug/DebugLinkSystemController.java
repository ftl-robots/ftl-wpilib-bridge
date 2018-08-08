package com.ftlrobots.link.debug;

import com.ftlrobots.link.ISystemController;
import com.ftlrobots.link.LinkCoreConstants.RobotMode;
import com.ftlrobots.link.data.JoystickData;
import com.ftlrobots.link.data.MatchInfo;

public class DebugLinkSystemController implements ISystemController {

    @Override
    public RobotMode getRobotMode() {
        return RobotMode.Teleop;
    }

    @Override
    public boolean getRobotDisabled() {
        return false;
    }

    @Override
    public double getMatchTime() {
        return 0;
    }

    @Override
    public MatchInfo getMatchInfo() {
        return new MatchInfo("DEBUG", "DEBUG", 0, "");
    }

    @Override
    public JoystickData[] getJoystickData() {
        return null;
    }
}