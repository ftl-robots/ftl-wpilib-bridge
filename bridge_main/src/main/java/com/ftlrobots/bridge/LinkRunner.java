package com.ftlrobots.bridge;

import java.lang.reflect.InvocationTargetException;

import com.ftlrobots.bridge.containers.IRobotClassContainer;
import com.ftlrobots.bridge.containers.JavaRobotContainer;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;
import com.ftlrobots.link.FTLLink;
import com.ftlrobots.link.data.JoystickData;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.wpilibj.DriverStation;

public class LinkRunner {
    private static final Logger sLogger = LogManager.getLogger(LinkRunner.class);

    private String mRobotClassName;
    private FTLLink mLink;

    private IRobotClassContainer mRobot;

    protected Thread mRobotThread;
    protected Thread mBridgeLinkThread;

    protected boolean mRunningLink;

    public LinkRunner(FTLLink link) {
        mLink = link;

        sLogger.log(Level.TRACE, "Initializing BridgeLink");

        // Set the default robot name
        mRobotClassName = "com.ftlrobots.bridge.examplerobot.ExampleRobot";

        // TODO Attempt to initialize the provided robot class name
    }

    private void createRobot()
        throws InstantiationException, IllegalAccessException, ClassNotFoundException,
                   NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

        mRobot = new JavaRobotContainer(mRobotClassName);
        mRobot.constructRobot();
    }

    private Runnable createLinkThread() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    sLogger.log(Level.INFO, "Bridge Link Started");
                    DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop();

                    // TODO Load list of ports

                    // Main event loop
                    while (mRunningLink) {
                        DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop();

                        // Set the robot mode
                        switch (mLink.getRobotMode()) {
                            case Teleop:
                                DataAccessorFactory.getInstance().getDriverStationAccessor().setAutonomous(false);
                                break;
                            case Autonomous:
                                DataAccessorFactory.getInstance().getDriverStationAccessor().setAutonomous(true);
                                break;
                        }

                        // Set enabled state
                        DataAccessorFactory.getInstance().getDriverStationAccessor().setDisabled(mLink.getRobotDisabled());

                        // Set Match Info
                        // TODO Implement

                        // Set Joystick info
                        JoystickData[] joystickInfo = mLink.getJoystickData();
                        if (joystickInfo != null) {
                            for (int i = 0; i < joystickInfo.length; i++) {
                                JoystickData stickInfo = joystickInfo[i];
                                int numButtons = stickInfo.getButtonValues().length;
                                int buttonMask = 0; // TODO Implement

                                DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(i, stickInfo.getAxisValues(), stickInfo.getPovValues(), numButtons, buttonMask);
                            }
                        }
                        // TODO Pick up new data from the system controller (current mode, position, etc)

                        // TODO Pick up new data from the FTL Hardware (e.g. sensor input, etc)

                        // TODO Write the new data to the FTL hardware (e.g. PWM, etc)
                    }
                }
                catch (Throwable e) {
                    sLogger.log(Level.ERROR, e);
                    exitWithError();
                }
            }
        };
    }

    private Runnable createRobotThread() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    DriverStation.getInstance();
                    DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop();
                    mRobot.startCompetition();
                }
                catch (Exception e) {
                    sLogger.log(Level.ERROR, e);
                    exitWithError();
                }
            }
        };
    }

    protected void stop() {
        if (mBridgeLinkThread != null) {
            try {
                mRunningLink = false;
                mBridgeLinkThread.join();
                mBridgeLinkThread = null;
            }
            catch (InterruptedException e) {
                sLogger.log(Level.ERROR, e);
            }
        }

        if (mRobotThread != null) {
            mRobotThread.interrupt();
            mRobotThread.stop();
            mRobotThread = null;
        }
    }

    private void exitWithError() {
        stop();
        System.exit(-1);
    }

    public void start()
        throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
                IllegalArgumentException, InvocationTargetException {

        createRobot();

        if (mRobot != null) {
            mRobotThread = new Thread(createRobotThread(), "RobotThread");
            mBridgeLinkThread = new Thread(createLinkThread(), "LinkThread");

            mRunningLink = true;

            mLink.start();

            mRobotThread.start();
            mBridgeLinkThread.start();
        }
    }

}