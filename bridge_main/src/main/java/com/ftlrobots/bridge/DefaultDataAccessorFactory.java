package com.ftlrobots.bridge;

import com.ftlrobots.bridge.wrapperaccessors.DataAccessorFactory;
import com.ftlrobots.bridge.wrapperaccessors.java.JavaDataAccessor;

public final class DefaultDataAccessorFactory {
    private static final boolean sInitialized = false;

    private DefaultDataAccessorFactory() {}

    public static void initialize() {
        if (!sInitialized) {
            DataAccessorFactory.setAccessor(new JavaDataAccessor());
        }
    }
}