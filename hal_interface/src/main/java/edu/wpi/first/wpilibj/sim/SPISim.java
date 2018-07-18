package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.SPIDataJNI;

public class SPISim {
    private final int mIndex;

    public SPISim(int index) {
        mIndex = index;
    }

    public void registerReadCallback(BufferCallback callback) {
        SPIDataJNI.registerReadCallback(mIndex, callback);
    }

    public void registerWriteCallback(ConstBufferCallback callback) {
        SPIDataJNI.registerWriteCallback(mIndex, callback);
    }

    public void resetData() {
        SPIDataJNI.resetData(mIndex);
    }
}