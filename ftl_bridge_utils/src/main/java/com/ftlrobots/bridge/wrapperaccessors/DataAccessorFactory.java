package com.ftlrobots.bridge.wrapperaccessors;

/**
 * Provides access to language/implementation specific data accessors
 */
public final class DataAccessorFactory {
    private static IDataAccessor sInstance = null;

    private DataAccessorFactory() {}

    public static void setAccessor(IDataAccessor accessor) {
        sInstance = accessor;
    }

    public static IDataAccessor getInstance() {
        return sInstance;
    }
}