package com.ftlrobots.bridge.wrapperaccessors.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ftlrobots.bridge.modulewrapper.interfaces.ISensorWrapper;

public abstract class BaseWrapperAccessor<Type extends ISensorWrapper> {
    protected abstract Map<Integer, Type> getMap();

    protected Type getValue(int port) {
        return getMap().get(port);
    }

    public void setName(int port, String name) {
        Type object = getValue(port);
        if (object != null) {
            object.setName(name);
        }
    }

    public String getName(int port) {
        return getValue(port).getName();
    }

    public List<Integer> getPortList() {
        return new ArrayList<>(getMap().keySet());
    }

    public String getType(int port) {
        return getMap().get(port).getClass().getName();
    }
}