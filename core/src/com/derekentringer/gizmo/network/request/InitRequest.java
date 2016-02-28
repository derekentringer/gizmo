package com.derekentringer.gizmo.network.request;

import com.google.gson.annotations.SerializedName;

public class InitRequest {

    @SerializedName("platform")
    private String mPlatform;

    @SerializedName("os_version")
    private String mOsVersion;

    @SerializedName("sdk_version")
    private String mSdkVersion;

    public InitRequest(String platform, String osVersion, String sdkVersion) {
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

}