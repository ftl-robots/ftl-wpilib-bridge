package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.DIODataJNI;

public class DIOSim {
    private final int mIndex;

    public DIOSim(int index) {
        mIndex = index;
    }

    public boolean getValue() {
        return DIODataJNI.getValue(mIndex);
    }

    public void setValue(boolean value) {
        DIODataJNI.setValue(mIndex, value);
    }
}