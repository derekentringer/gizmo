package com.derekentringer.gizmo.analytics.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AnalyticsModel implements Serializable {

    @SerializedName("session_num")
    private int mSessionNum;

    public int getSessionNum() {
        return mSessionNum;
    }

    public void setSessionNum(int mSessionNum) {
        this.mSessionNum = mSessionNum;
    }

}