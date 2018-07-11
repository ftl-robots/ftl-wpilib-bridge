package edu.wpi.first.wpilibj.sim;

import java.nio.ByteBuffer;

public interface BufferCallback {
    void callback(String name, ByteBuffer buffer);
}