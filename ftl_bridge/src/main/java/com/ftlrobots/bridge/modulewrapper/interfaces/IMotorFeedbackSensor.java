package com.ftlrobots.bridge.modulewrapper.interfaces;

public interface IMotorFeedbackSensor {
    void setPosition(double position);
    void setVelocity(double velocity);

    double getPosition();
    double getVelocity();

    public static class NullFeedbackSensor implements IMotorFeedbackSensor {
        @Override
        public double getPosition() {
            return 0;
        }

        @Override
        public double getVelocity() {
            return 0;
        }

        @Override
        public void setPosition(double position) {}

        @Override
        public void setVelocity(double velocity) {}
    }
}