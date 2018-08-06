package com.ftlrobots.link.data;

/**
 * Class representing information about a specific match
 */
public class MatchInfo {
    private String mEventName;
    private String mMatchType;
    private int mMatchNumber;
    private String mAdditionalData;

    public MatchInfo(String eventName, String matchType, int matchNum, String additionalData) {
        mEventName = eventName;
        mMatchType = matchType;
        mMatchNumber = matchNum;
        mAdditionalData = additionalData;
    }

    public String getEventName() {
        return mEventName;
    }

    public String getMatchType() {
        return mMatchType;
    }

    public int getMatchNumber() {
        return mMatchNumber;
    }

    public String getAdditionalData() {
        return mAdditionalData;
    }
}