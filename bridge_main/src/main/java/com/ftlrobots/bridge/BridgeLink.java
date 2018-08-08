package com.ftlrobots.bridge;

import java.lang.reflect.InvocationTargetException;

import com.ftlrobots.bridge.containers.IRobotClassContainer;
import com.ftlrobots.bridge.containers.JavaRobotContainer;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;
import com.ftlrobots.link.IHardwareInterface;
import com.ftlrobots.link.ISystemController;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.wpilibj.DriverStation;

public class BridgeLink {
    private static final Logger sLogger = LogManager.getLogger(BridgeLink.class);

    private String mRobotClassName;
    private IHardwareInterface mHardwareInterface;
    private ISystemController mSystemController;

    private IRobotClassContainer mRobot;

    protected Thread mRobotThread;
    protected Thread mBridgeLinkThread;

    protected boolean mRunningLink;

    public BridgeLink(IHardwareInterface hwIface, ISystemController sysController) {
        mHardwareInterface = hwIface;
        mSystemController = sysController;

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

            mRobotThread.start();
            mBridgeLinkThread.start();
        }
    }

}