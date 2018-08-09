package com.ftlrobots.link.ros;

import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;


public class RosLinkNode implements NodeMain {
    @Override
    public void onStart(ConnectedNode connectedNode) {
        // Publish on chatter AND subscribe
        // As a test
        Subscriber<std_msgs.String> subscriber = connectedNode.newSubscriber("chatter", std_msgs.String._TYPE);
        subscriber.addMessageListener(new MessageListener<std_msgs.String>(){

            @Override
            public void onNewMessage(std_msgs.String message) {
                System.out.println("I heard: \"" + message.getData() + "\"");
            }
        });

        final Publisher<std_msgs.String> publisher =
            connectedNode.newPublisher("chatter", std_msgs.String._TYPE);

        connectedNode.executeCancellableLoop(new CancellableLoop(){
            private int sequenceNumber;

            @Override
            protected void setup() {
                sequenceNumber = 0;
            }

            @Override
            protected void loop() throws InterruptedException {
                std_msgs.String str = publisher.newMessage();
                str.setData("Hello world! " + sequenceNumber);
                publisher.publish(str);
                sequenceNumber++;

                Thread.sleep(1000);
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

    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("FTLBridge/RosLink");
    }
}