package com.ftlrobots.bridge.containers;

import java.lang.reflect.InvocationTargetException;

public interface IRobotClassContainer {
    public void constructRobot()
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
                    IllegalArgumentException, InvocationTargetException;

    public void startCompetition()
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}