package com.ftlrobots.bridge.modulewrapper;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.ftlrobots.bridge.modulewrapper.interfaces.IGyroWrapper;

public class BaseGyroWrapper extends SensorWrapper implements IGyroWrapper {
    private final Supplier<Double> mGetter;
    private final Consumer<Double> mSetter;

    public BaseGyroWrapper(String extraName, Supplier<Double> getter, Consumer<Double> setter) {
        super(extraName);
        mGetter = getter;
        mSetter = setter;
    }

    @Override
    public double getAngle() {
        return mGetter.get();
    }

    @Override
    public void setAngle(double angle) {
        mSetter.accept(angle);
    }
}