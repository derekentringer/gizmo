package com.derekentringer.gizmo.network.request;

import com.google.gson.annotations.SerializedName;

public class EventFieldsRequest {

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

    public EventFieldsRequest(String device,
                              int eventVersion,
                              String userId,
                              int clientTimestamp,
                              String sdkVersion,
                              String osVersion,
                              String manufacturer,
                              String platform,
                              String sessionId,
                              int sessionNum) {
        mDevice = device;
        mEventVersion = eventVersion;
        mUserId = userId;
        mClientTimestamp = clientTimestamp;
        mSdkVersion = sdkVersion;
        mOsVersion = osVersion;
        mManufacturer = manufacturer;
        mPlatform = platform;
        mSessionId = sessionId;
        mSessionNum = sessionNum;
    }

    public String getmDevice() {
        return mDevice;
    }

    public void setmDevice(String mDevice) {
        this.mDevice = mDevice;
    }

    public int getmEventVersion() {
        return mEventVersion;
    }

    public void setmEventVersion(int mEventVersion) {
        this.mEventVersion = mEventVersion;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public int getmClientTimestamp() {
        return mClientTimestamp;
    }

    public void setmClientTimestamp(int mClientTimestamp) {
        this.mClientTimestamp = mClientTimestamp;
    }

    public String getmSdkVersion() {
        return mSdkVersion;
    }

    public void setmSdkVersion(String mSdkVersion) {
        this.mSdkVersion = mSdkVersion;
    }

    public String getmOsVersion() {
        return mOsVersion;
    }

    public void setmOsVersion(String mOsVersion) {
        this.mOsVersion = mOsVersion;
    }

    public String getmManufacturer() {
        return mManufacturer;
    }

    public void setmManufacturer(String mManufacturer) {
        this.mManufacturer = mManufacturer;
    }

    public String getmPlatform() {
        return mPlatform;
    }

    public void setmPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getmSessionId() {
        return mSessionId;
    }

    public void setmSessionId(String mSessionId) {
        this.mSessionId = mSessionId;
    }

    public int getmSessionNum() {
        return mSessionNum;
    }

    public void setmSessionNum(int mSessionNum) {
        this.mSessionNum = mSessionNum;
    }

}