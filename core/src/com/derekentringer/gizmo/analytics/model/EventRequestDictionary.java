package com.derekentringer.gizmo.analytics.model;

import com.derekentringer.gizmo.analytics.request.EventRequest;
import com.derekentringer.gizmo.analytics.util.AnalyticsUtils;

import java.util.LinkedHashMap;

public class EventRequestDictionary {

    private static LinkedHashMap<String, String> dictionary = new LinkedHashMap<String, String>();

    public static EventRequest buildEventRequestParameters(String category, String eventId, int attemptNum) {
        EventRequest eventFields = new EventRequest();
        dictionary.put("category", category);
        dictionary.put("sdk_version", AnalyticsSettings.REST_API_VERSION);
        dictionary.put("v", AnalyticsSettings.REST_API_EVENT_VERSION);
        dictionary.put("user_id", AnalyticsUtils.getDeviceId());
        dictionary.put("session_num", AnalyticsUtils.getSessionNum());
        dictionary.put("client_ts", String.valueOf(AnalyticsSettings.getSessionStartTimestamp()));
        dictionary.put("device", AnalyticsUtils.getDevice());
        dictionary.put("manufacturer", AnalyticsUtils.getManufacturer());
        dictionary.put("os_version", AnalyticsUtils.getOsVersion());
        dictionary.put("platform", AnalyticsUtils.getPlatform());
        dictionary.put("length", String.valueOf(AnalyticsUtils.calculateSessionLength()));

        if (dictionary.get("session_id") == null
                || dictionary.get("session_id") == "") {
            dictionary.put("session_id", AnalyticsUtils.getRandomSessionUUID());
        }
        else {
            dictionary.put("session_id", AnalyticsSettings.getSessionId());
        }

        eventFields.setCategory(dictionary.get("category"));
        eventFields.setSdkVersion(dictionary.get("sdk_version"));
        eventFields.setEventVersion(Integer.valueOf(dictionary.get("v")));
        eventFields.setUserId(dictionary.get("user_id"));
        eventFields.setSessionNum(Integer.valueOf(dictionary.get("session_num")));
        eventFields.setClientTs(Integer.valueOf(dictionary.get("client_ts")));
        eventFields.setDevice(dictionary.get("device"));
        eventFields.setManufacturer(dictionary.get("manufacturer"));
        eventFields.setOsVersion(dictionary.get("os_version"));
        eventFields.setPlatform(dictionary.get("platform"));
        eventFields.setLength(Integer.valueOf(dictionary.get("length")));
        eventFields.setSessionId(dictionary.get("session_id"));

        if (category.equals("progression")) {
            dictionary.put("event_id", eventId);
            eventFields.setEventId(dictionary.get("event_id"));
            if (attemptNum > 0) {
                dictionary.put("attempt_num", String.valueOf(attemptNum));
                eventFields.setAttemptNum(Integer.valueOf(dictionary.get("attempt_num")));
            }
        }

        return eventFields;
    }

}