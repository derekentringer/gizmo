package com.derekentringer.gizmo.analytics.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventRequest implements Serializable {

    @SerializedName("category")
    private String mCategory;

    @SerializedName("client_ts")
    private int mClientTs;

    @SerializedName("device")
    private String mDevice;

    @SerializedName("manufacturer")
    private String mManufacturer;

    @SerializedName("os_version")
    private String mOsVersion;

    @SerializedName("platform")
    private String mPlatform;

    @SerializedName("sdk_version")
    private String mSdkVersion;

    @SerializedName("session_id")
    private String mSessionId;

    @SerializedName("session_num")
    private int mSessionNum;

    @SerializedName("user_id")
    private String mUserId;

    @SerializedName("v")
    private int mEventVersion;

    @SerializedName("length")
    private int mLength;

    @SerializedName("event_id")
    private String mEventId;

    @SerializedName("attempt_num")
    private int mAttemptNum;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public int getClientTs() {
        return mClientTs;
    }

    public void setClientTs(int clientTs) {
        mClientTs = clientTs;
    }

    public String getDevice() {
        return mDevice;
    }

    public void setDevice(String device) {
        mDevice = device;
    }

    public String getManufacturer() {
        return mManufacturer;
    }

    public void setManufacturer(String manufacturer) {
        mManufacturer = manufacturer;
    }

    public String getOsVersion() {
        return mOsVersion;
    }

    public void setOsVersion(String osVersion) {
        mOsVersion = osVersion;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String platform) {
        mPlatform = platform;
    }

    public String getSdkVersion() {
        return mSdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        mSdkVersion = sdkVersion;
    }

    public String getSessionId() {
        return mSessionId;
    }

    public void setSessionId(String sessionId) {
        mSessionId = sessionId;
    }

    public int getSessionNum() {
        return mSessionNum;
    }

    public void setSessionNum(int sessionNum) {
        mSessionNum = sessionNum;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public int getEventVersion() {
        return mEventVersion;
    }

    public void setEventVersion(int eventVersion) {
        mEventVersion = eventVersion;
    }

    public int getLength() {
        return mLength;
    }

    public void setLength(int length) {
        mLength = length;
    }

    public String getEventId() {
        return mEventId;
    }

    public void setEventId(String eventId) {
        mEventId = eventId;
    }

    public int getAttemptNum() {
        return mAttemptNum;
    }

    public void setAttemptNum(int attemptNum) {
        mAttemptNum = attemptNum;
    }

}