package com.derekentringer.gizmo.network.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventRequest implements Serializable {

    @SerializedName("category")
    private String mCategory;

    @SerializedName("event_fields")
    private EventFieldsRequest mEventFields;

    public EventRequest(String category, EventFieldsRequest eventFields) {
        mCategory = category;
        mEventFields = eventFields;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        mCategory = mCategory;
    }

    public EventFieldsRequest getEventFields() {
        return mEventFields;
    }

    public void setEventFields(EventFieldsRequest eventFields) {
        mEventFields = eventFields;
    }

    @Override
    public String toString() {
        return "{" +
                "\"category\":" + "\"" + mCategory + '\"' +
                ",\"event_fields\":" + mEventFields.toString() +
                '}';
    }

}