package com.ftlrobots.bridge.modulewrapper.interfaces;

// TODO ZQ - This should get renamed to IBridgeComponentUpdater or something similar
public interface ISimulatorUpdater {
    public abstract void update();
    public Object getConfig();
}