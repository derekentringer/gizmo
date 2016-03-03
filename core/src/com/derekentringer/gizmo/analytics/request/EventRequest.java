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

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public int getClientTs() {
        return mClientTs;
    }

    public void setClientTs(int mClientTs) {
        this.mClientTs = mClientTs;
    }

    public String getDevice() {
        return mDevice;
    }

    public void setDevice(String mDevice) {
        this.mDevice = mDevice;
    }

    public String getManufacturer() {
        return mManufacturer;
    }

    public void setManufacturer(String mManufacturer) {
        this.mManufacturer = mManufacturer;
    }

    public String getOsVersion() {
        return mOsVersion;
    }

    public void setOsVersion(String mOsVersion) {
        this.mOsVersion = mOsVersion;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getSdkVersion() {
        return mSdkVersion;
    }

    public void setSdkVersion(String mSdkVersion) {
        this.mSdkVersion = mSdkVersion;
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

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public int getEventVersion() {
        return this.mEventVersion;
    }

    public void setEventVersion(int mEventVersion) {
        this.mEventVersion = mEventVersion;
    }

    @Override
    public String toString() {
        return "{" +
                "\"category\":" + "\"" + mCategory + '\"' +
                ",\"client_ts\":" + mClientTs +
                ",\"device\":" + "\"" + mDevice + '\"' +
                ",\"manufacturer\":" + "\"" + mManufacturer + '\"' +
                ",\"os_version\":" + "\"" + mOsVersion + '\"' +
                ",\"platform\":" + "\"" + mPlatform + '\"' +
                ",\"sdk_version\":" + "\"" + mSdkVersion + '\"' +
                ",\"session_id\":" + "\"" + mSessionId + '\"' +
                ",\"session_num\":" + mSessionNum +
                ",\"user_id\":" + "\"" + mUserId + '\"' +
                ",\"v\":" + mEventVersion +
                '}';
    }

}