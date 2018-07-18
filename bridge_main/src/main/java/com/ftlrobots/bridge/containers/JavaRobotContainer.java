package com.ftlrobots.bridge.containers;

import edu.wpi.first.wpilibj.RobotBase;

public class JavaRobotContainer implements IRobotClassContainer {
    private final String mRobotClassName;
    private RobotBase mRobot;

    public JavaRobotContainer(String robotClassName) {
        mRobotClassName = robotClassName;
    }

    @Override
    public void constructRobot() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        RobotBase.initializeHardwareConfiguration();
        mRobot = (RobotBase) Class.forName(mRobotClassName).newInstance();
    }

    @Override
    public void startCompetition() {
        mRobot.startCompetition();
    }

    public RobotBase getJavaRobot() {
        return mRobot;
    }
}