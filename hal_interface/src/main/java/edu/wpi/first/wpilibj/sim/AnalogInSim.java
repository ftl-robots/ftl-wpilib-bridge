package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.AnalogInDataJNI;

public class AnalogInSim {
    private final int mIndex;

    public AnalogInSim(int index) {
        mIndex = index;
    }

    public double getVoltage() {
        return AnalogInDataJNI.getVoltage(mIndex);
    }

    public void setVoltage(double voltage) {
        AnalogInDataJNI.setVoltage(mIndex, voltage);
    }
}