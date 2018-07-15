package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.EncoderDataJNI;

public class EncoderSim {
    private final int mIndex;

    public EncoderSim(int index) {
        mIndex = index;
    }

    public int getCount() {
        return EncoderDataJNI.getCount(mIndex);
    }

    public void setCount(int count) {
        EncoderDataJNI.setCount(mIndex, count);
    }

    public double getPeriod() {
        return EncoderDataJNI.getPeriod(mIndex);
    }

    public void setPeriod(double period) {
        EncoderDataJNI.setPeriod(mIndex, period);
    }

    public double getDistancePerPulse() {
        return EncoderDataJNI.getDistancePerPulse(mIndex);
    }

    public void setDistancePerPulse(double distancePerPulse) {
        EncoderDataJNI.setDistancePerPulse(mIndex, distancePerPulse);
    }

    public boolean getReset() {
        return EncoderDataJNI.getReset(mIndex);
    }

    public void setReset(boolean reset) {
        EncoderDataJNI.setReset(mIndex, reset);
    }
}