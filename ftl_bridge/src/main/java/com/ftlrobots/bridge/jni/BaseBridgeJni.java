package com.ftlrobots.bridge.jni;

import com.ftlrobots.bridge.JniLibraryResourceLoader;

import edu.wpi.first.wpilibj.hal.HAL;

public class BaseBridgeJni {
    static {
        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("wpiHal");
        JniLibraryResourceLoader.loadLibrary("hal_interface_jni");

        HAL.initialize(500, 0);
        RegisterCallbacksJni.reset();
    }
}