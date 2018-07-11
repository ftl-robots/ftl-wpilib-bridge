package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.AnalogInDataJNI;

public class AnalogInSim {
    private final int m_index;

    public AnalogInSim(int index) {
        m_index = index;
    }

    public double getVoltage() {
        return AnalogInDataJNI.getVoltage(m_index);
    }

    public void setVoltage(double voltage) {
        AnalogInDataJNI.setVoltage(m_index, voltage);
    }
}