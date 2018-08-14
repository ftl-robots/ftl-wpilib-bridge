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
    private double mMatchTime;

    public RosLink() {
        // === FTLLink Setup ===
        mMatchTime = 0;

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
    public double getMatchTime() {
        return mMatchTime;
    }

    @Override
    protected void onDigitalOutputValuesChanged() {
        // TODO Broadcast to ROS
    }

    @Override
    protected void onPWMOutputValuesChanged() {
        // TODO Broadcast to ROS
    }

    // === ISystemMessageListener Implementation ===
    @Override
    public void onSystemMessageReceived(SystemMessage msg) {
        // Messages with a source of "ftl-robot-mode" deal with disabled state and teleop/auto etc
        switch (msg.getSource()) {
            case "ftl-robot-mode": {
                sLogger.log(Level.INFO, msg);
                // TODO Grab the values for "disabled" and "mode"
                if (msg.getValues().containsKey("disabled")) {
                    notifyRobotDisabledChanged(msg.getValues().get("disabled"));
                }
                if (msg.getValues().containsKey("mode")) {
                    RobotMode newMode = null;
                    switch (msg.getValues().get("mode")) {
                        case "auto":
                            newMode = RobotMode.Autonomous;
                            break;
                        case "teleop":
                            newMode = RobotMode.Teleop;
                            break;
                    }

                    if (newMode != null) {
                        notifyRobotModeChanged(newMode);
                    }
                }
            } break;
        }
    }
}