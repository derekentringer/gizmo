package com.derekentringer.gizmo.analytics.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventRequest implements Serializable {

    @SerializedName("category")
    private String mCategory; //"category": "user",

    @SerializedName("client_ts")
    private int mClientTs; //"client_ts": 1443669792,

    @SerializedName("device")
    private String mDevice; //"device": "SGH-M919",

    @SerializedName("manufacturer")
    private String mManufacturer; //"manufacturer": "samsung",

    @SerializedName("os_version")
    private String mOsVersion; //"os_version": "android 4.4.4",

    @SerializedName("platform")
    private String mPlatform; //"platform": "android",

    @SerializedName("sdk_version")
    private String mSdkVersion; //"sdk_version": "unity 2.1.4",

    @SerializedName("session_id")
    private String mSessionId; //"session_id": "84d04731-1e8a-4b60-97db-5cd09900bc85",

    @SerializedName("session_num")
    private int mSessionNum; //"session_num": 16,

    @SerializedName("user_id")
    private String mUserId; //"user_id": "c1cb0331-920a-436a-8bc4-a2792ba464b9",

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