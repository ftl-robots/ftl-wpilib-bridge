package com.ftlrobots.link.ros;

import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

public class RosLink {
    private Thread mNodeThread;

    public void start() {
        mNodeThread = new Thread(new Runnable(){

            @Override
            public void run() {
                // TODO provide configuration
                NodeConfiguration config = NodeConfiguration.newPrivate();
                NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
                RosLinkNode linkNode = new RosLinkNode();
                nodeMainExecutor.execute(linkNode, config);
            }
        });

        mNodeThread.start();
    }
}