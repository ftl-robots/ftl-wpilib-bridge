package com.ftlrobots.link.ros;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ftlrobots.link.FTLLink;
import com.ftlrobots.link.LinkCoreConstants.RobotMode;
import com.ftlrobots.link.data.JoystickData;
import com.ftlrobots.link.data.MatchInfo;
import com.ftlrobots.link.ros.messages.SystemMessage;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

public class RosLink extends FTLLink implements ISystemMessageListener {
    private static final Logger sLogger = LogManager.getLogger(RosLink.class);

    private NodeConfiguration mNodeConfig;
    private NodeMainExecutor mNodeExecutor;
    private RosLinkSystemNode mSysNode;

    private boolean mLinkActive;

    // FTLLink properties
    private boolean mRobotDisabled;
    private RobotMode mRobotMode;
    private MatchInfo mMatchInfo;
    private double mMatchTime;
    private JoystickData[] mJoystickData;

    private Map<Integer, Boolean> mDigitalOutputValues;
    private Map<Integer, Double> mPWMOutputValues;
    private Map<Integer, Boolean> mDigitalInputValues;
    private Map<Integer, Double> mAnalogInputValues;

    public RosLink() {
        // === FTLLink Setup ===
        mRobotDisabled = false;
        mRobotMode = RobotMode.Autonomous;
        mMatchInfo = null;
        mMatchTime = 0;
        mJoystickData = null;

        mDigitalOutputValues = new HashMap<>();
        mPWMOutputValues = new HashMap<>();
        mDigitalInputValues = new HashMap<>();
        mAnalogInputValues = new HashMap<>();

        // === ROS Node Setup ===
        mNodeConfig = NodeConfiguration.newPrivate();
        mNodeExecutor = DefaultNodeMainExecutor.newDefault();

        // Set up the node links
        mSysNode = new RosLinkSystemNode();
        mSysNode.registerListener(this);
    }

    // === FTLLink Abstract Methods ===
    @Override
    public void start() {
        mNodeExecutor.execute(mSysNode, mNodeConfig);
    }

    @Override
    public boolean getRobotDisabled() {
        return this.mRobotDisabled;
    }

    @Override
    public RobotMode getRobotMode() {
        return this.mRobotMode;
    }

    @Override
    public MatchInfo getMatchInfo() {
        return this.mMatchInfo;
    }

    @Override
    public double getMatchTime() {
        return 0;
    }

    @Override
    public JoystickData[] getJoystickData() {
        return this.mJoystickData;
    }

    @Override
    public synchronized void setDigitalOutput(int port, boolean value) {
        mDigitalOutputValues.put(port, value);
    }

    @Override
    public synchronized void setDigitalOutput(Map<Integer, Boolean> digitalValueMap) {
        for (Entry<Integer, Boolean> valEntry : digitalValueMap.entrySet()) {
            mDigitalOutputValues.put(valEntry.getKey(), valEntry.getValue());
        }
    }

    @Override
    public synchronized void setPWMOutput(int port, double value) {
        mPWMOutputValues.put(port, value);
    }

    @Override
    public synchronized void setPWMOutput(Map<Integer, Double> pwmValueMap) {
        for (Entry<Integer, Double> valEntry : pwmValueMap.entrySet()) {
            mPWMOutputValues.put(valEntry.getKey(), valEntry.getValue());
        }
    }

    @Override
    public synchronized boolean getDigitalInput(int port) {
        if (!mDigitalInputValues.containsKey(port)) {
            return false;
        }
        return mDigitalInputValues.get(port);
    }

    @Override
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

    @Override
    public synchronized double getAnalogInput(int port) {
        if (!mAnalogInputValues.containsKey(port)) {
            return 0.0;
        }
        return mAnalogInputValues.get(port);
    }

    @Override
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

    // === ISystemMessageListener Implementation ===
    @Override
    public void onSystemMessageReceived(SystemMessage msg) {
        // Messages with a source of "ftl-robot-mode" deal with disabled state and teleop/auto etc
        switch (msg.getSource()) {
            case "ftl-robot-mode": {
                sLogger.log(Level.INFO, msg);
            } break;
        }
    }
}