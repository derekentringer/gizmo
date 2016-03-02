package com.derekentringer.gizmo.analytics.model;

import com.derekentringer.gizmo.analytics.request.EventRequestTest;
import com.derekentringer.gizmo.analytics.util.AnalyticsUtils;

import java.util.LinkedHashMap;

public class EventRequestDictionary {

    private static LinkedHashMap<String, String> dictionary = new LinkedHashMap<String, String>();

    public static void build() {
        dictionary.put("category", AnalyticsUtils.getCategory());
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

    public static EventRequestTest getDictionary() {
        EventRequestTest eventFields = new EventRequestTest();
        eventFields.setCategory(dictionary.get("category"));
        eventFields.setDevice(dictionary.get("device"));
        eventFields.setEventVersion(Integer.valueOf(dictionary.get("v")));
        eventFields.setUserId(dictionary.get("user_id"));
        eventFields.setClientTs(Integer.valueOf(dictionary.get("client_ts")));
        eventFields.setSdkVersion(dictionary.get("sdk_version"));
        eventFields.setOsVersion(dictionary.get("os_version"));
        eventFields.setManufacturer(dictionary.get("manufacturer"));
        eventFields.setPlatform(dictionary.get("platform"));
        eventFields.setSessionId(dictionary.get("session_id"));
        eventFields.setSessionNum(Integer.valueOf(dictionary.get("session_num")));
        return eventFields;
    }

    public static void buildTestData() {
        dictionary.put("android_id", "d079bfdb8fb30d7e");
        dictionary.put("build", "2.0.1");
        dictionary.put("category", "user");
        dictionary.put("client_ts", String.valueOf(AnalyticsUtils.getTimestamp()));
        dictionary.put("connection_type", "wwan");
        dictionary.put("device", "SGH-M919");
        dictionary.put("engine_version", "unity 4.6.7");
        dictionary.put("google_aid", "c1cb0331-920a-436a-8bc4-a2792ba464b9");
        dictionary.put("manufacturer", "samsung");
        dictionary.put("os_version", "android 4.4.4");
        dictionary.put("platform", "android");
        dictionary.put("progression", "Modern:4.3");
        dictionary.put("sdk_version", "unity 2.1.4");
        dictionary.put("session_id", "84d04731-1e8a-4b60-97db-5cd09900bc85");
        dictionary.put("session_num", "2");
        dictionary.put("user_id", "c1cb0331-920a-436a-8bc4-a2792ba464b9");
        dictionary.put("v", "2");
    }

    public static EventRequestTest getTestDictionary() {
        EventRequestTest eventFields = new EventRequestTest();
        eventFields.setAndroidId(dictionary.get("android_id"));
        eventFields.setBuild(dictionary.get("build"));
        eventFields.setCategory(dictionary.get("category"));
        eventFields.setClientTs(Integer.valueOf(dictionary.get("client_ts")));
        eventFields.setConnectionType(dictionary.get("connection_type"));
        eventFields.setDevice(dictionary.get("device"));
        eventFields.setEngineVersion(dictionary.get("engine_version"));
        eventFields.setGoogleAid(dictionary.get("google_aid"));
        eventFields.setManufacturer(dictionary.get("manufacturer"));
        eventFields.setOsVersion(dictionary.get("os_version"));
        eventFields.setPlatform(dictionary.get("platform"));
        eventFields.setProgression(dictionary.get("progression"));
        eventFields.setSdkVersion(dictionary.get("sdk_version"));
        eventFields.setSessionId(dictionary.get("session_id"));
        eventFields.setSessionNum(Integer.valueOf(dictionary.get("session_num")));
        eventFields.setUserId(dictionary.get("user_id"));
        eventFields.setEventVersion(Integer.valueOf(dictionary.get("v")));
        return eventFields;
    }

}