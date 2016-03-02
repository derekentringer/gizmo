package com.derekentringer.gizmo.analytics.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventRequestTest implements Serializable {

    @SerializedName("android_id")
    private String mAndroidId; //"d079bfdb8fb30d7e",

    @SerializedName("build")
    private String mBuild; //: "2.0.1",

    @SerializedName("category")
    private String mCategory; //"category": "user",

    @SerializedName("client_ts")
    private int mClientTs; //"client_ts": 1443669792,

    @SerializedName("connection_type")
    private String mConnectionType; //"connection_type": "wwan",

    @SerializedName("device")
    private String mDevice; //"device": "SGH-M919",

    @SerializedName("engine_version")
    private String mEngineVersion; //"engine_version": "unity 4.6.7",

    @SerializedName("google_aid")
    private String mGoogleAid; //"google_aid": "c1cb0331-920a-436a-8bc4-a2792ba464b9",

    @SerializedName("manufacturer")
    private String mManufacturer; //"manufacturer": "samsung",

    @SerializedName("os_version")
    private String mOsVersion; //"os_version": "android 4.4.4",

    @SerializedName("platform")
    private String mPlatform; //"platform": "android",

    @SerializedName("progression")
    private String mProgression; //"progression": "Modern:4.3",

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

    public String getAndroidId() {
        return mAndroidId;
    }

    public void setAndroidId(String mAndroidId) {
        this.mAndroidId = mAndroidId;
    }

    public String getBuild() {
        return mBuild;
    }

    public void setBuild(String mBuild) {
        this.mBuild = mBuild;
    }

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

    public String getConnectionType() {
        return mConnectionType;
    }

    public void setConnectionType(String mConnectionType) {
        this.mConnectionType = mConnectionType;
    }

    public String getDevice() {
        return mDevice;
    }

    public void setDevice(String mDevice) {
        this.mDevice = mDevice;
    }

    public String getEngineVersion() {
        return mEngineVersion;
    }

    public void setEngineVersion(String mEngineVersion) {
        this.mEngineVersion = mEngineVersion;
    }

    public String getGoogleAid() {
        return mGoogleAid;
    }

    public void setGoogleAid(String mGoogleAid) {
        this.mGoogleAid = mGoogleAid;
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

    public String getProgression() {
        return mProgression;
    }

    public void setProgression(String mProgression) {
        this.mProgression = mProgression;
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
                "\"android_id\":" + "\"" + mAndroidId + '\"' +
                ",\"build\":" + "\"" + mBuild + '\"' +
                ",\"category\":" + "\"" + mCategory + '\"' +
                ",\"client_ts\":" + mClientTs +
                ",\"connection_type\":" + "\"" + mConnectionType + '\"' +
                ",\"device\":" + "\"" + mDevice + '\"' +
                ",\"engine_version\":" + "\"" + mEngineVersion + '\"' +
                ",\"google_aid\":" + "\"" + mGoogleAid + '\"' +
                ",\"manufacturer\":" + "\"" + mManufacturer + '\"' +
                ",\"os_version\":" + "\"" + mOsVersion + '\"' +
                ",\"platform\":" + "\"" + mPlatform + '\"' +
                ",\"progression\":" + "\"" + mProgression + '\"' +
                ",\"sdk_version\":" + "\"" + mSdkVersion + '\"' +
                ",\"session_id\":" + "\"" + mSessionId + '\"' +
                ",\"session_num\":" + mSessionNum +
                ",\"user_id\":" + "\"" + mUserId + '\"' +
                ",\"v\":" + mEventVersion +
                '}';
    }

}