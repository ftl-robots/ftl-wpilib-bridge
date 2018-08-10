package com.ftlrobots.link.ros.messages;

import java.util.Map;
import java.util.Map.Entry;

public class SystemMessage {
    private String mMsgSource;
    private long mReceivedTime;
    private Map<String, String> mValues;

    public SystemMessage(String src, long rcvTime, Map<String, String> vals) {
        mMsgSource = src;
        mReceivedTime = rcvTime;
        mValues = vals;
    }

    public String getSource() {
        return mMsgSource;
    }

    public long getReceivedTime() {
        return mReceivedTime;
    }

    public Map<String, String> getValues() {
        return mValues;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("SysMessage(" + this.mMsgSource + ") @ [" + this.mReceivedTime + "]\n");
        for (Entry<String, String> pair : this.mValues.entrySet()) {
            str.append("\t" + pair.getKey() + ": " + pair.getValue() + "\n");
        }
        return str.toString();
    }
}