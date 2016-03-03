package com.derekentringer.gizmo.analytics.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SessionEndRequest implements Serializable {

    @SerializedName("category")
    private String mCategory;

    @SerializedName("length")
    private int mLength;

    @SerializedName("user_id")
    private String mUserId;

    @SerializedName("v")
    private int mV;

    public SessionEndRequest(String category, int length, String userId, int v) {
        mCategory = category;
        mLength = length;
        mUserId = userId;
        mV = v;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public int getLength() {
        return mLength;
    }

    public void setLength(int length) {
        mLength = length;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public int getmV() {
        return mV;
    }

    public void setmV(int mV) {
        this.mV = mV;
    }

    @Override
    public String toString() {
        return "{" +
                "\"category\":" + "\"" + mCategory + '\"' +
                ",\"length\":" + mLength +
                ",\"user_id\":" + "\"" + mUserId + '\"' +
                ",\"v\":" + mV +
                '}';
    }

}