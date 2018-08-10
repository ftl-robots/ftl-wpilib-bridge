package com.ftlrobots.link.ros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ftlrobots.link.ros.messages.SystemMessage;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Subscriber;



public class RosLinkSystemNode implements NodeMain {
    private static final Logger sLogger = LogManager.getLogger(RosLinkSystemNode.class);

    private List<ISystemMessageListener> mListeners = new ArrayList<>();

    public void registerListener(ISystemMessageListener listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(ISystemMessageListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        sLogger.log(Level.INFO, "ROS Node " + connectedNode.getName().toString() + " started");

        // Subscribe to the "ftl_sys_messages" topic
        Subscriber<diagnostic_msgs.DiagnosticStatus> sysMsgSubscriber =
            connectedNode.newSubscriber("ftl_sys_messages", diagnostic_msgs.DiagnosticStatus._TYPE);

        sysMsgSubscriber.addMessageListener(new MessageListener<diagnostic_msgs.DiagnosticStatus>(){

            @Override
            public void onNewMessage(diagnostic_msgs.DiagnosticStatus msg) {
                // Convert the message into a SystemMessage
                String msgSrc = msg.getName();
                long msgRcvTime = System.currentTimeMillis();
                Map<String, String> msgValues = new HashMap<>();

                for (diagnostic_msgs.KeyValue kvPair : msg.getValues()) {
                    msgValues.put(kvPair.getKey(), kvPair.getValue());
                }

                SystemMessage sysMsg = new SystemMessage(msgSrc, msgRcvTime, msgValues);

                for (ISystemMessageListener listener : mListeners) {
                    listener.onSystemMessageReceived(sysMsg);
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
        return GraphName.of("ftl_ros_link_system");
    }
}