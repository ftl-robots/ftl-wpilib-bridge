package com.ftlrobots.bridge.modulewrapper;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.ftlrobots.bridge.modulewrapper.interfaces.IAccelerometerWrapper;

public class BaseAccelerometerWrapper extends SensorWrapper implements IAccelerometerWrapper {
    private final Supplier<Double> mGetter;
    private final Consumer<Double> mSetter;

    public BaseAccelerometerWrapper(String name, Supplier<Double> getter, Consumer<Double> setter) {
        super(name);

        mGetter = getter;
        mSetter = setter;
    }

    @Override
    public void setAcceleration(double acceleration) {
        mSetter.accept(acceleration);
    }

    @Override
    public double getAcceleration() {
        return mGetter.get();
    }
}