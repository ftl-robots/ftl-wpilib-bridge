package com.ftlrobots.link;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.ftlrobots.link.LinkCoreConstants.RobotMode;
import com.ftlrobots.link.data.JoystickData;
import com.ftlrobots.link.data.MatchInfo;

public abstract class FTLLink {

    private Map<Integer, Boolean> mDigitalOutputValues = new HashMap<>();
    private Map<Integer, Double> mPWMOutputValues = new HashMap<>();
    private Map<Integer, Boolean> mDigitalInputValues = new HashMap<>();
    private Map<Integer, Double> mAnalogInputValues = new HashMap<>();

    private boolean mRobotDisabled = true;
    private RobotMode mRobotMode = RobotMode.Autonomous;
    private MatchInfo mMatchInfo;
    private JoystickData[] mJoystickData;

    private List<ILinkListener> mListeners = new ArrayList<>();

    public abstract void start();

    // =======================================================
    // LISTENER REGISTRATION
    // =======================================================

    public void registerListener(ILinkListener listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(ILinkListener listener) {
        mListeners.remove(listener);
    }

    // =======================================================
    // PUBLIC INTERFACES TO DATA
    //========================================================

    // System Controller
    public synchronized boolean getRobotDisabled() {
        return mRobotDisabled;
    }

    public synchronized RobotMode getRobotMode() {
        return mRobotMode;
    }

    public synchronized MatchInfo getMatchInfo() {
        return mMatchInfo;
    }

    public synchronized JoystickData[] getJoystickData() {
        return mJoystickData;
    }

    public abstract double getMatchTime();

    // Hardware Interface
    public synchronized void setDigitalOutput(int port, boolean value) {
        mDigitalOutputValues.put(port, value);
        onDigitalOutputValuesChanged();
    }

    public synchronized void setDigitalOutput(Map<Integer, Boolean> digitalValueMap) {
        for (Entry<Integer, Boolean> valEntry : digitalValueMap.entrySet()) {
            mDigitalOutputValues.put(valEntry.getKey(), valEntry.getValue());
        }
        onDigitalOutputValuesChanged();
    }

    public synchronized void setPWMOutput(int port, double value) {
        mPWMOutputValues.put(port, value);
        onPWMOutputValuesChanged();
    }

    public synchronized void setPWMOutput(Map<Integer, Double> pwmValueMap) {
        for (Entry<Integer, Double> valEntry : pwmValueMap.entrySet()) {
            mPWMOutputValues.put(valEntry.getKey(), valEntry.getValue());
        }
        onPWMOutputValuesChanged();
    }

    public synchronized boolean getDigitalInput(int port) {
        if (!mDigitalInputValues.containsKey(port)) {
            return false;
        }
        return mDigitalInputValues.get(port);
    }

    public synchronized Map<Integer, Boolean> getDigitalInputMulti(List<Integer> portList) {
        Map<Integer, Boolean> result = new HashMap<>();

        if (portList == null) {
            result.putAll(mDigitalInputValues);
        }
        else {
            for (Integer wantedPort : portList) {
                if (mDigitalInputValues.containsKey(wantedPort)) {
                    result.put(wantedPort, mDigitalInputValues.get(wantedPort));
                }
            }
        }
        return result;
    }

    public synchronized double getAnalogInput(int port) {
        if (!mAnalogInputValues.containsKey(port)) {
            return 0.0;
        }
        return mAnalogInputValues.get(port);
    }

    public synchronized Map<Integer, Double> getAnalogInputMulti(List<Integer> portList) {
        Map<Integer, Double> result = new HashMap<>();

        if (portList == null) {
            result.putAll(mAnalogInputValues);
        }
        else {
            for (Integer wantedPort : portList) {
                if (mAnalogInputValues.containsKey(wantedPort)) {
                    result.put(wantedPort, mAnalogInputValues.get(wantedPort));
                }
            }
        }
        return result;
    }

    // =====================================================
    // SUBCLASS-ACCESSIBLE SETTERS FOR DATA
    // =====================================================
    protected synchronized void setRobotDisabled(boolean disabled) {
        mRobotDisabled = disabled;
    }

    protected synchronized void setRobotMode(RobotMode mode) {
        mRobotMode = mode;
    }

    protected synchronized void setMatchInfo(MatchInfo info) {
        mMatchInfo = info;
    }

    protected synchronized void setJoystickData(JoystickData[] data) {
        mJoystickData = data;
    }

    // =====================================================
    // SUBCLASS-ACCESSIBLE LISTENER NOTIFICATION METHODS
    // =====================================================
    protected void notifyDigitalInputsChanged(Map<Integer, Boolean> updates) {
        Map<Integer, Boolean> updateCopy = new HashMap<>();
        updateCopy.putAll(updates);

        for (ILinkListener listener : mListeners) {
            listener.onDigitalInputsChanged(updateCopy);
        }
    }

    protected void notifyAnalogInputsChanged(Map<Integer, Double> updates) {
        Map<Integer, Double> updateCopy = new HashMap<>();
        updateCopy.putAll(updates);

        for (ILinkListener listener : mListeners) {
            listener.onAnalogInputsChanged(updates);
        }
    }

    protected void notifyRobotDisabledChanged(boolean disabled) {
        for (ILinkListener listener : mListeners) {
            listener.onRobotDisabledChanged(disabled);
        }
    }

    protected void notifyRobotModeChanged(RobotMode mode) {
        for (ILinkListener listener : mListeners) {
            listener.onRobotModeChanged(mode);
        }
    }

    protected void notifyMatchInfoChanged(MatchInfo matchInfo) {
        for (ILinkListener listener : mListeners) {
            listener.onMatchInfoChanged(matchInfo);
        }
    }

    protected void notifyJoystickDataChanged(JoystickData[] joystickData) {
        for (ILinkListener listener : mListeners) {
            listener.onJoystickDataChanged(joystickData);
        }
    }

    // =====================================================
    // SUBCLASS-IMPLEMENTED EVENT HANDLERS
    // =====================================================
    protected abstract void onDigitalOutputValuesChanged();
    protected abstract void onPWMOutputValuesChanged();

}