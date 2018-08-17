package com.ftlrobots.link.ros;


import java.util.Map;

import com.ftlrobots.link.FTLLink;
import com.ftlrobots.link.LinkCoreConstants.RobotMode;
import com.ftlrobots.link.data.JoystickData;
import com.ftlrobots.link.ros.messages.SystemMessage;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

public class RosLink
    extends FTLLink
    implements ISystemMessageListener,
               IMultiJoyListener,
               IHardwareListener {

    private static final Logger sLogger = LogManager.getLogger(RosLink.class);

    private NodeConfiguration mNodeConfig;
    private NodeMainExecutor mNodeExecutor;

    // ROS Nodes
    private RosLinkSystemNode mSysNode;
    private RosLinkMultiJoyNode mJoyNode;
    private RosLinkHardwareNode mHardwareNode;

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

        mJoyNode = new RosLinkMultiJoyNode();
        mJoyNode.registerListener(this);

        mHardwareNode = new RosLinkHardwareNode();
        mHardwareNode.registerListener(this);
    }

    // === FTLLink Abstract Methods ===
    @Override
    public void start() {
        // Start the nodes
        mNodeExecutor.execute(mSysNode, mNodeConfig);
        mNodeExecutor.execute(mJoyNode, mNodeConfig);
        mNodeExecutor.execute(mHardwareNode, mNodeConfig);

    }

    @Override
    public double getMatchTime() {
        return mMatchTime;
    }

    @Override
    protected void onDigitalOutputValuesChanged() {
        // Robot Code -> setDigitalOutput -> HardwareNode -> ROS
        mHardwareNode.updateDigitalOutput(getDigitalOutputMulti());
    }

    @Override
    protected void onPWMOutputValuesChanged() {
        mHardwareNode.updatePWMOutput(getPWMOutputMulti());
    }

    // === ISystemMessageListener Implementation ===
    @Override
    public void onSystemMessageReceived(SystemMessage msg) {
        // Messages with a source of "ftl-robot-mode" deal with disabled state and teleop/auto etc
        switch (msg.getSource()) {
            case "ftl-robot-mode": {
                if (msg.getValues().containsKey("disabled")) {
                    boolean isDisabled = (msg.getValues().get("disabled") == "true");
                    setRobotDisabled(isDisabled);
                    notifyRobotDisabledChanged(isDisabled);
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
                        setRobotMode(newMode);
                        notifyRobotModeChanged(newMode);
                    }
                }
            } break;
        }
    }

    // === IMultiJoyListener Implementation ===
    @Override
    public void onJoystickDataReceived(JoystickData[] data) {
        // update the joystick data on ourselves
        setJoystickData(data);
        notifyJoystickDataChanged(data);
    }

    // === IHardwareListener Implementation ===
    // This is where we get inputs from the actual hardware and have to inform our listeners
    @Override
    public void onAnalogInputChanged(Map<Integer, Double> values) {
        // Handle analog input messages from the hardware
        setAnalogInput(values);
        notifyAnalogInputsChanged(values);
    }

    @Override
    public void onDigitalInputChanged(Map<Integer, Boolean> values) {
        setDigitalInput(values);
        notifyDigitalInputsChanged(values);
    }
}