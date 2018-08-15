package com.ftlrobots.link.ros;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;

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