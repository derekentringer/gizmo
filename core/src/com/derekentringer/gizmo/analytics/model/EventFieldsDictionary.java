package com.derekentringer.gizmo.analytics.model;

import com.derekentringer.gizmo.analytics.util.AnalyticsUtils;
import com.derekentringer.gizmo.network.request.EventFieldsRequest;

import java.util.LinkedHashMap;

public class EventFieldsDictionary {

    private static LinkedHashMap<String, String> dictionary = new LinkedHashMap<String, String>();

    public static void create() {
        dictionary.put("device", AnalyticsUtils.getDevice());
        dictionary.put("v", AnalyticsSettings.REST_API_EVENT_VERSION);
        dictionary.put("user_id", AnalyticsUtils.getMacAddress());
        dictionary.put("client_ts", String.valueOf(AnalyticsUtils.getTimestamp()));
        dictionary.put("sdk_version", AnalyticsSettings.REST_API_VERSION);
        dictionary.put("os_version", AnalyticsUtils.getOsVersion());
        dictionary.put("manufacturer", AnalyticsUtils.getManufacturer());
        dictionary.put("platform", AnalyticsUtils.getPlatform());
        dictionary.put("session_id", AnalyticsUtils.getRandomUUID());
        dictionary.put("session_num", AnalyticsUtils.getSessionNum());
    }

    public static EventFieldsRequest getDictionary() {
        EventFieldsRequest eventFields = new EventFieldsRequest();
        eventFields.setDevice(dictionary.get("device"));
        eventFields.setEventVersion(Integer.valueOf(dictionary.get("v")));
        eventFields.setUserId(dictionary.get("user_id"));
        eventFields.setClientTimestamp(Integer.valueOf(dictionary.get("client_ts")));
        eventFields.setSdkVersion(dictionary.get("sdk_version"));
        eventFields.setOsVersion(dictionary.get("os_version"));
        eventFields.setManufacturer(dictionary.get("manufacturer"));
        eventFields.setPlatform(dictionary.get("platform"));
        eventFields.setSessionId(dictionary.get("session_id"));
        eventFields.setSessionNum(Integer.valueOf(dictionary.get("session_num")));
        return eventFields;
    }

}