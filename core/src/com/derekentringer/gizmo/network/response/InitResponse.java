package com.derekentringer.gizmo.network.response;

import com.google.gson.annotations.SerializedName;

public class InitResponse {

    @SerializedName("enabled")
    private boolean mIsEnabled;

    @SerializedName("server_ts")
    private int mServerTs;

    public boolean ismIsEnabled() {
        return mIsEnabled;
    }

    public void setmIsEnabled(boolean mIsEnabled) {
        this.mIsEnabled = mIsEnabled;
    }

    public int getmServerTs() {
        return mServerTs;
    }

    public void setmServerTs(int mServerTs) {
        this.mServerTs = mServerTs;
    }

}