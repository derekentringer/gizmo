package com.derekentringer.gizmo.network.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventFieldsRequest implements Serializable {

    @SerializedName("device")
    private String mDevice;

    @SerializedName("v")
    private int mEventVersion;

    @SerializedName("user_id")
    private String mUserId;

    @SerializedName("client_ts")
    private int mClientTimestamp;

    @SerializedName("sdk_version")
    private String mSdkVersion;

    @SerializedName("os_version")
    private String mOsVersion;

    @SerializedName("manufacturer")
    private String mManufacturer;

    @SerializedName("platform")
    private String mPlatform;

    @SerializedName("session_id")
    private String mSessionId;

    @SerializedName("session_num")
    private int mSessionNum;

    public String getDevice() {
        return mDevice;
    }

    public void setDevice(String mDevice) {
        this.mDevice = mDevice;
    }

    public int getEventVersion() {
        return mEventVersion;
    }

    public void setEventVersion(int mEventVersion) {
        this.mEventVersion = mEventVersion;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public int getClientTimestamp() {
        return mClientTimestamp;
    }

    public void setClientTimestamp(int mClientTimestamp) {
        this.mClientTimestamp = mClientTimestamp;
    }

    public String getSdkVersion() {
        return mSdkVersion;
    }

    public void setSdkVersion(String mSdkVersion) {
        this.mSdkVersion = mSdkVersion;
    }

    public String getOsVersion() {
        return mOsVersion;
    }

    public void setOsVersion(String mOsVersion) {
        this.mOsVersion = mOsVersion;
    }

    public String getManufacturer() {
        return mManufacturer;
    }

    public void setManufacturer(String mManufacturer) {
        this.mManufacturer = mManufacturer;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getSessionId() {
        return mSessionId;
    }

    public void setSessionId(String mSessionId) {
        this.mSessionId = mSessionId;
    }

    public int getSessionNum() {
        return mSessionNum;
    }

    public void setSessionNum(int mSessionNum) {
        this.mSessionNum = mSessionNum;
    }

}