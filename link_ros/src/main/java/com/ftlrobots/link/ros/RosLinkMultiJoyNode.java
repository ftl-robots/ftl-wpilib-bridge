package com.ftlrobots.link.ros;

import java.util.ArrayList;
import java.util.List;

import com.ftlrobots.link.data.JoystickData;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Subscriber;


public class RosLinkMultiJoyNode implements NodeMain {
    private static final Logger sLogger = LogManager.getLogger(RosLinkMultiJoyNode.class);

    private List<IMultiJoyListener> mListeners = new ArrayList<>();

    public void registerListener(IMultiJoyListener listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(IMultiJoyListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        sLogger.log(Level.INFO, "ROS Node " + connectedNode.getName().toString() + " started");

        // Subscribe to the "multijoy" topic
        Subscriber<ftl_bridge_msgs.MultiJoy> multijoySubscriber =
            connectedNode.newSubscriber("multijoy", ftl_bridge_msgs.MultiJoy._TYPE);

        multijoySubscriber.addMessageListener(new MessageListener<ftl_bridge_msgs.MultiJoy>(){

            @Override
            public void onNewMessage(ftl_bridge_msgs.MultiJoy msg) {
                // Create individual JoystickData objects
                int numJoysticks = (int)msg.getNjoys().getData();
                JoystickData[] joystickData = new JoystickData[numJoysticks];
                for (int i = 0; i < numJoysticks; i++) {
                    sensor_msgs.Joy stick = msg.getJoysticks().get(i);
                    float[] axes = stick.getAxes();
                    short[] povs = new short[0]; // TODO This doesn't look like it's supported by Joy right now
                    boolean[] buttons = new boolean[stick.getButtons().length];
                    // populate the buttons array
                    for (int j = 0; j < buttons.length; j++) {
                        buttons[j] = stick.getButtons()[j] != 0;
                    }
                    joystickData[i] = new JoystickData(axes, povs, buttons);
                }

                for (IMultiJoyListener listener : mListeners) {
                    listener.onJoystickDataReceived(joystickData);
                }
            }
        });
    }

    @Override
    public void onShutdownComplete(Node node) {

    }

    @Override
    public void onShutdown(Node node) {

    }

    @Override
    public void onError(Node node, Throwable err) {
        sLogger.log(Level.ERROR, err);
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("ftl_ros_link_multijoy");
    }
}