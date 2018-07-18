package com.ftlrobots.bridge;

import java.lang.reflect.InvocationTargetException;
import com.ftlrobots.bridge.containers.IRobotClassContainer;
import com.ftlrobots.bridge.containers.JavaRobotContainer;
import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.wpilibj.DriverStation;

public class Simulator {
    private static final Logger sLogger = LogManager.getLogger(Simulator.class);
    private String mRobotClassName;

    private IRobotClassContainer mRobot;

    protected Thread mRobotThread;
    protected Thread mSimulatorThread;
    protected boolean mRunningSimulator;

    public Simulator() {
        sLogger.log(Level.INFO, "Initializing Simulator");
        mRobotClassName = "com.ftlrobots.bridge.examplerobot.ExampleRobot";
    }

    private void createRobot()
        throws InstantiationException, IllegalAccessException, ClassNotFoundException,
                   NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

        mRobot = new JavaRobotContainer(mRobotClassName);

        mRobot.constructRobot();
    }

    public void startSimulation()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
                    IllegalArgumentException, InvocationTargetException {
        createRobot();

        if (mRobot != null) {
            mRobotThread = new Thread(createRobotThread(), "RobotThread");
            mSimulatorThread = new Thread(createSimulatorThread(), "SimulatorThread");

            mRunningSimulator = true;

            mRobotThread.start();
            mSimulatorThread.start();
        }
    }

    // Experimental
    private boolean inputVal = false;

    private Runnable createSimulatorThread() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    DataAccessorFactory.getInstance().getDriverStationAccessor().waitForProgramToStart();

                    DataAccessorFactory.getInstance().getDriverStationAccessor().setDisabled(false);
                    DataAccessorFactory.getInstance().getDriverStationAccessor().setAutonomous(false);

                    while (mRunningSimulator) {
                        DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop();

                        // long seconds = (long)Math.floor(System.currentTimeMillis() / 1000);
                        // boolean val = (seconds % 2 == 0);
                        // if (val != inputVal) {
                        //     inputVal = val;
                        //     sLogger.log(Level.INFO, "Setting data for digital channel 0 to " + inputVal);
                        //     DataAccessorFactory.getInstance().getDigitalAccessor().setState(0, inputVal);
                        // }
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
                catch (UnsatisfiedLinkError e) {
                    sLogger.log(Level.ERROR, e);
                    exitWithError();
                }
                catch (Exception e) {
                    sLogger.log(Level.ERROR, e);
                    exitWithError();
                }
            }
        };
    }

    protected void stop() {
        if (mSimulatorThread != null) {
            try {
                mRunningSimulator = false;
                mSimulatorThread.join();
                mSimulatorThread = null;
            }
            catch(InterruptedException e) {
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