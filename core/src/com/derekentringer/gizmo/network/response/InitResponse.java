package com.derekentringer.gizmo.network.response;

import com.google.gson.annotations.SerializedName;

public class InitResponse {

    @SerializedName("enabled")
    private boolean mIsEnabled;

    @SerializedName("server_ts")
    private int mServerTs;

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setIsEnabled(boolean mIsEnabled) {
        this.mIsEnabled = mIsEnabled;
    }

    public int getServerTs() {
        return mServerTs;
    }

    public void setServerTs(int mServerTs) {
        this.mServerTs = mServerTs;
    }

}