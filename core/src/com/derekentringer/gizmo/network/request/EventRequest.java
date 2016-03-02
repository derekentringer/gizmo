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

    /*@Override
    public String toString() {
        return "{" +
                "\"category\":" + "\"" + mCategory + '\"' +
                ",\"event_fields\":" + mEventFields.toString() +
                '}';
    }*/

    @Override
    public String toString() {
        return "{\n" +
                "        \"android_id\": \"d079bfdb8fb30d7e\",\n" +
                "        \"build\": \"2.0.1\",\n" +
                "        \"category\": \"user\",\n" +
                "        \"client_ts\": 1443669792,\n" +
                "        \"connection_type\": \"wwan\",\n" +
                "        \"device\": \"SGH-M919\",\n" +
                "        \"engine_version\": \"unity 4.6.7\",\n" +
                "        \"google_aid\": \"c1cb0331-920a-436a-8bc4-a2792ba464b9\",\n" +
                "        \"manufacturer\": \"samsung\",\n" +
                "        \"os_version\": \"android 4.4.4\",\n" +
                "        \"platform\": \"android\",\n" +
                "        \"progression\": \"Modern:4.3\",\n" +
                "        \"sdk_version\": \"unity 2.1.4\",\n" +
                "        \"session_id\": \"84d04731-1e8a-4b60-97db-5cd09900bc85\",\n" +
                "        \"session_num\": 16,\n" +
                "        \"user_id\": \"c1cb0331-920a-436a-8bc4-a2792ba464b9\",\n" +
                "        \"v\": 2\n" +
                "}";
    }

}