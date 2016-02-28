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

    public String getmSdkVersion() {
        return mSdkVersion;
    }

    public void setmSdkVersion(String mSdkVersion) {
        this.mSdkVersion = mSdkVersion;
    }

    public String getmPlatform() {
        return mPlatform;
    }

    public void setmPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getmOsVersion() {
        return mOsVersion;
    }

    public void setmOsVersion(String mOsVersion) {
        this.mOsVersion = mOsVersion;
    }

}