package com.derekentringer.gizmo.analytics.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SessionStartRequest implements Serializable {

    @SerializedName("platform")
    private String mPlatform;

    @SerializedName("os_version")
    private String mOsVersion;

    @SerializedName("sdk_version")
    private String mSdkVersion;

    public SessionStartRequest(String platform, String osVersion, String sdkVersion) {
        mPlatform = platform;
        mOsVersion = osVersion;
        mSdkVersion = sdkVersion;
    }

    public String getSdkVersion() {
        return mSdkVersion;
    }

    public void setSdkVersion(String mSdkVersion) {
        this.mSdkVersion = mSdkVersion;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getOsVersion() {
        return mOsVersion;
    }

    public void setOsVersion(String mOsVersion) {
        this.mOsVersion = mOsVersion;
    }

    @Override
    public String toString() {
        return "{" +
                "\"platform\":" + "\"" + mPlatform + '\"' +
                ",\"os_version\":" + "\"" + mOsVersion + '\"' +
                ",\"sdk_version\":" + "\"" + mSdkVersion + '\"' +
                '}';
    }

}