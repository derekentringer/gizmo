package com.derekentringer.gizmo.analytics.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LocalAnalyticsModel implements Serializable {

    @SerializedName("deviceId")
    private String mDeviceId;

    @SerializedName("session_num")
    private int mSessionNum;

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public int getSessionNum() {
        return mSessionNum;
    }

    public void setSessionNum(int sessionNum) {
        mSessionNum = sessionNum;
    }

}