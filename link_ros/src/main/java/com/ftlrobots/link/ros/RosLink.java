package com.ftlrobots.link.ros;

import java.util.HashMap;
import java.util.Map;

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