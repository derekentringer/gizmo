package com.derekentringer.gizmo.analytics;

import com.derekentringer.gizmo.analytics.util.AnalyticsInfo;
import com.derekentringer.gizmo.network.request.EventFieldsRequest;

import java.util.LinkedHashMap;

public class EventFieldsDictionary {

    private static LinkedHashMap<String, String> dictionary = new LinkedHashMap<String, String>();

    public EventFieldsDictionary() {
    }

    public static void initiate() {
        dictionary.put("device", AnalyticsInfo.getPlatform());
        dictionary.put("v", AnalyticsSettings.REST_API_EVENT_VERSION);
        dictionary.put("user_id", AnalyticsInfo.getMacAddress());
        dictionary.put("client_ts", "1");
        dictionary.put("sdk_version", AnalyticsSettings.REST_API_VERSION);
        dictionary.put("os_version", AnalyticsInfo.getOsVersion());
        dictionary.put("manufacturer", "manufacturer");
        dictionary.put("platform", AnalyticsInfo.getPlatform());
        dictionary.put("session_id", "1");
        dictionary.put("session_num", "1");
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
        eventFields.setSessionNum(Integer.valueOf(dictionary.get("session_id")));
        return eventFields;
    }

    /*ArrayList<Object> list = new ArrayList<>();
    HashMap<String, String> map = new HashMap<>();
    Integer i = 0;
    for (Object s:list) {
        map.put(String.valueOf(i), s.toString());
        i++;
    }*/

}