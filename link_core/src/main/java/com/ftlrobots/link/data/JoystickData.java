package com.ftlrobots.link.data;

public class JoystickData {
    private float[] mAxisValues;
    private short[] mPovValues;
    private boolean[] mButtonValues;

    public JoystickData(float[] axisValues, short[] povValues, boolean[] buttonValues) {
        mAxisValues = axisValues;
        mPovValues = povValues;
        mButtonValues = buttonValues;
    }

    public float[] getAxisValues() {
        return mAxisValues;
    }

    public short[] getPovValues() {
        return mPovValues;
    }

    public boolean[] getButtonValues() {
        return mButtonValues;
    }
}