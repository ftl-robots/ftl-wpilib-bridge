package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.AnalogOutDataJNI;

public class AnalogOutSim {
    private final int mIndex;

    public AnalogOutSim(int index) {
        mIndex = index;
    }

    public double getVoltage() {
        return AnalogOutDataJNI.getVoltage(mIndex);
    }

    public void setVoltage(double voltage) {
        AnalogOutDataJNI.setVoltage(mIndex, voltage);
    }
}