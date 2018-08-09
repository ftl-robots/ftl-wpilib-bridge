package com.ftlrobots.link.ros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ros.concurrent.CancellableLoop;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;


public class RosLinkHardwareNode implements NodeMain {
    private static final Logger sLogger = LogManager.getLogger(RosLinkHardwareNode.class);

    private List<IHardwareListener> mListeners = new ArrayList<>();

    private Object mDigitalOutLock = new Object();
    private Object mPWMOutLock = new Object();
    private boolean mNewDigitalOutData = false;
    private boolean mNewPWMOutData = false;
    private Map<Integer, Boolean> mDigitalOutValues = new HashMap<>();
    private Map<Integer, Double> mPWMOutValues = new HashMap<>();

    public void registerListener(IHardwareListener listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(IHardwareListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        // Create subscriber for ftl_hardware_inputs(DiagnosticStatus)
        Subscriber<diagnostic_msgs.DiagnosticStatus> inputSubscriber =
            connectedNode.newSubscriber("ftl_hardware_inputs", diagnostic_msgs.DiagnosticStatus._TYPE);

        inputSubscriber.addMessageListener(new MessageListener<diagnostic_msgs.DiagnosticStatus>(){

            @Override
            public void onNewMessage(diagnostic_msgs.DiagnosticStatus msg) {
                // digitalIn, analogIn
                if (msg.getName() == "digitalIn") {
                    // Handle Digital messages
                    Map<Integer, Boolean> dInMap = new HashMap<>();
                    for (diagnostic_msgs.KeyValue kvPair : msg.getValues()) {
                        int port = Integer.parseInt(kvPair.getKey());
                        boolean val = (kvPair.getValue() == "true" || kvPair.getValue() == "1");
                        dInMap.put(port, val);
                    }

                    for (IHardwareListener listener: mListeners) {
                        listener.onDigitalInputChanged(dInMap);
                    }
                }
                else if (msg.getName() == "analogIn") {
                    // Handle Analog messages
                    Map<Integer, Double> aInMap = new HashMap<>();
                    for (diagnostic_msgs.KeyValue kvPair : msg.getValues()) {
                        int port = Integer.parseInt(kvPair.getKey());
                        double val = Double.parseDouble(kvPair.getValue());
                        aInMap.put(port, val);
                    }

                    for (IHardwareListener listener: mListeners) {
                        listener.onAnalogInputChanged(aInMap);
                    }
                }
            }
        });

        // Also set up the publisher
        Publisher<diagnostic_msgs.DiagnosticStatus> outputPublisher =
            connectedNode.newPublisher("ftl_hardware_outputs", diagnostic_msgs.DiagnosticStatus._TYPE);

        // Set up the loop
        connectedNode.executeCancellableLoop(new CancellableLoop(){

            @Override
            protected void loop() throws InterruptedException {
                // Check to see if we have any new updates to send out
                if (mNewDigitalOutData) {
                    // Make a local copy
                    Map<Integer, Boolean> digitalOutCopy;

                    synchronized(mDigitalOutLock) {
                        digitalOutCopy = new HashMap<>();
                        digitalOutCopy.putAll(mDigitalOutValues);
                        mDigitalOutValues.clear();
                        mNewDigitalOutData = false;
                    }

                    // Send it out
                    if (digitalOutCopy != null) {
                        diagnostic_msgs.DiagnosticStatus digitalMsg = outputPublisher.newMessage();
                        digitalMsg.setName("digitalOut");
                        List<diagnostic_msgs.KeyValue> digitalOutKVs = new ArrayList<>();
                        for (Entry<Integer, Boolean> valueEntry : digitalOutCopy.entrySet()) {
                            String k = valueEntry.getKey().toString();
                            String v = valueEntry.getValue() ? "1" : "0";
                            diagnostic_msgs.KeyValue newKV =
                                connectedNode.getTopicMessageFactory()
                                             .newFromType(diagnostic_msgs.KeyValue._TYPE);
                            newKV.setKey(k);
                            newKV.setValue(v);
                            digitalOutKVs.add(newKV);
                        }
                        digitalMsg.setValues(digitalOutKVs);

                        outputPublisher.publish(digitalMsg);
                    }
                }

                if (mNewPWMOutData) {
                    // Make a local copy
                    Map<Integer, Double> pwmOutCopy;

                    synchronized(mPWMOutLock) {
                        pwmOutCopy = new HashMap<>();
                        pwmOutCopy.putAll(mPWMOutValues);
                        mPWMOutValues.clear();
                        mNewPWMOutData = false;
                    }

                    // Send it out
                    if (pwmOutCopy != null) {
                        diagnostic_msgs.DiagnosticStatus pwmMsg = outputPublisher.newMessage();
                        pwmMsg.setName("pwmOut");
                        List<diagnostic_msgs.KeyValue> pwmOutKVs = new ArrayList<>();
                        for (Entry<Integer, Double> valueEntry : pwmOutCopy.entrySet()) {
                            String k = valueEntry.getKey().toString();
                            String v = valueEntry.getValue().toString();
                            diagnostic_msgs.KeyValue newKV =
                                connectedNode.getTopicMessageFactory()
                                             .newFromType(diagnostic_msgs.KeyValue._TYPE);
                            newKV.setKey(k);
                            newKV.setValue(v);
                            pwmOutKVs.add(newKV);
                        }
                        pwmMsg.setValues(pwmOutKVs);

                        outputPublisher.publish(pwmMsg);
                    }
                }

                Thread.sleep(10);
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
        return GraphName.of("ftl_ros_link_hardware");
    }

    public void updateDigitalOutput(Map<Integer, Boolean> digitalOutValues) {
        synchronized(mDigitalOutLock) {
            mDigitalOutValues.putAll(digitalOutValues);
            mNewDigitalOutData = true;
        }
    }

    public void updatePWMOutput(Map<Integer, Double> pwmOutValues) {
        synchronized(mPWMOutLock) {
            mPWMOutValues.putAll(pwmOutValues);
            mNewPWMOutData = true;
        }
    }
}