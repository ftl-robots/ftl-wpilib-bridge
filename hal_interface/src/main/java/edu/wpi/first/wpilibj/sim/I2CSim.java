package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.I2CDataJNI;

public class I2CSim {
    private final int mIndex;

    public I2CSim(int index) {
        mIndex = index;
    }

    public void registerReadCallback(BufferCallback callback) {
        I2CDataJNI.registerReadCallback(mIndex, callback);
    }

    public void registerWriteCallback(ConstBufferCallback callback) {
        I2CDataJNI.registerWriteCallback(mIndex, callback);
    }

    public void resetData() {
        I2CDataJNI.resetData(mIndex);
    }
}