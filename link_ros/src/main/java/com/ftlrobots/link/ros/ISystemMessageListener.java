package com.ftlrobots.link.ros;

import com.ftlrobots.link.ros.messages.SystemMessage;

public interface ISystemMessageListener {
    void onSystemMessageReceived(SystemMessage msg);
}