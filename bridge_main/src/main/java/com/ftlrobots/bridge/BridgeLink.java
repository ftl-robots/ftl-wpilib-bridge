package com.ftlrobots.bridge;

import java.lang.reflect.InvocationTargetException;
import com.ftlrobots.bridge.containers.IRobotClassContainer;
import com.ftlrobots.bridge.containers.JavaRobotContainer;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Interface between a WPILibJ robot and the FTL Infrastructure
 */
public class BridgeLink {
    private static final Logger sLogger = LogManager.getLogger(BridgeLink.class);
    
    private String mRobotClassName;
    // TODO FTL Interface

    private IRobotClassContainer mRobot;

    protected Thread mRobotThread;
    protected Thread mBridgeLinkThread;

    protected boolean mRunningLink;

    // TODO This should take in a config object
    public BridgeLink() {
        sLogger.log(Level.TRACE, "Initializing BridgeLink");
        // TODO Take in the robot class name and attempt to load it
    }

    private void createRobot()
        throws InstantiationException, IllegalAccessException, ClassNotFoundException,
                   NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

        mRobot = new JavaRobotContainer(mRobotClassName);
        mRobot.constructRobot();
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
    
    /**
     * Creates the thread that interfaces with the FTL infrastructure
     */
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

                        // TODO Pick up new data from the FTL System Controller (e.g. current mode, position, etc)

                        // TODO Pick up new data from the FTL hardware (e.g. sensor INPUT, battery voltage)

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

    /**
     * Creates the thread that runs the actual robot code
     */
    private Runnable createRobotThread() {
        return new Runnable(){
        
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
}