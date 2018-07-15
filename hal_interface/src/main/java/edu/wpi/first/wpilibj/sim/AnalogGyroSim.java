package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.AnalogGyroDataJNI;

public class AnalogGyroSim {
    private final int mIndex;

    public AnalogGyroSim(int index) {
        mIndex = index;
    }

    public double getAngle() {
        return AnalogGyroDataJNI.getAngle(mIndex);
    }

    public void setAngle(double angle) {
        AnalogGyroDataJNI.setAngle(mIndex, angle);
    }
}