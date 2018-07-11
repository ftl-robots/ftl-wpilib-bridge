package edu.wpi.first.wpilibj.sim;

import java.nio.ByteBuffer;

public interface ConstBufferCallback {
    void callback(String name, ByteBuffer buffer);
}