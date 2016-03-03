package com.derekentringer.gizmo.analytics.model;

import com.derekentringer.gizmo.analytics.request.EventRequest;
import com.derekentringer.gizmo.analytics.util.AnalyticsUtils;

import java.util.LinkedHashMap;

public class EventRequestDictionary {

    private static LinkedHashMap<String, String> dictionary = new LinkedHashMap<String, String>();

    public static void buildDefaultParameters() {
        dictionary.put("category", "user");
        dictionary.put("sdk_version", AnalyticsSettings.REST_API_VERSION);
        dictionary.put("v", AnalyticsSettings.REST_API_EVENT_VERSION);
        dictionary.put("user_id", AnalyticsUtils.getMacAddress());
        dictionary.put("session_id", AnalyticsUtils.getRandomUUID());
        dictionary.put("session_num", AnalyticsUtils.getSessionNum());
        dictionary.put("client_ts", String.valueOf(AnalyticsUtils.getTimestamp()));
        dictionary.put("device", AnalyticsUtils.getDevice());
        dictionary.put("manufacturer", AnalyticsUtils.getManufacturer());
        dictionary.put("os_version", AnalyticsUtils.getOsVersion());
        dictionary.put("platform", AnalyticsUtils.getPlatform());
    }

    public static EventRequest getDefaultParameters() {
        EventRequest eventFields = new EventRequest();
        eventFields.setCategory(dictionary.get("category"));
        eventFields.setSdkVersion(dictionary.get("sdk_version"));
        eventFields.setEventVersion(Integer.valueOf(dictionary.get("v")));
        eventFields.setUserId(dictionary.get("user_id"));
        eventFields.setSessionId(dictionary.get("session_id"));
        eventFields.setSessionNum(Integer.valueOf(dictionary.get("session_num")));
        eventFields.setClientTs(Integer.valueOf(dictionary.get("client_ts")));
        eventFields.setDevice(dictionary.get("device"));
        eventFields.setManufacturer(dictionary.get("manufacturer"));
        eventFields.setOsVersion(dictionary.get("os_version"));
        eventFields.setPlatform(dictionary.get("platform"));
        return eventFields;
    }

}