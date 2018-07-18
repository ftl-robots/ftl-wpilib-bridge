package com.ftlrobots.bridge.examplerobot;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;

public class ExampleRobot extends IterativeRobot {
    private static final Logger sLogger = LogManager.getLogger(ExampleRobot.class);
    public DigitalInput mDigitalInput;

    private boolean lastInputVal;

    @Override
    public void robotInit() {
        mDigitalInput = new DigitalInput(0);
        lastInputVal = mDigitalInput.get();
        sLogger.log(Level.INFO, "Finished initializing ExampleRobot");
    }

    @Override
    public void autonomousInit() {
        sLogger.log(Level.INFO, "Autonomous Mode Starting");
    }

    @Override
    public void teleopInit() {
        sLogger.log(Level.INFO, "Teleop Mode Starting");
    }

    @Override
    public void teleopPeriodic() {
        boolean currInputVal = mDigitalInput.get();
        if (currInputVal != lastInputVal) {
            lastInputVal = currInputVal;
            sLogger.log(Level.INFO, "Digital Input changed to " + currInputVal);
        }
    }
}