package com.derekentringer.gizmo.analytics.model;

public class AnalyticsSettings {

    public static final String ENDPOINT_PROD = "http://api.gameanalytics.com/";
    public static final String API_GAME_KEY_PROD = "82575771c5ec54ea225d9b3c44551e37";
    public static final String API_SECRET_KEY_PROD = "542ff23ea46e45810ae24666ddcc7c8a1fbe1f06";

    public static final String ENDPOINT_DEV = "http://api.gameanalytics.com/";
    public static final String API_GAME_KEY_DEV = "789958d397c847c99b89321d32d4262b";
    public static final String API_SECRET_KEY_DEV = "2e7195dfd9c3221b9aa55dfde87846fdbc0de914";

    public static final String ENDPOINT_SANDBOX = "http://sandbox-api.gameanalytics.com/";
    public static final String API_GAME_KEY_SANDBOX = "5c6bcb5402204249437fb5a7a80a4959";
    public static final String API_SECRET_KEY_SANDBOX = "16813a12f718bc5c620f56944e1abc3ea13ccbac";

    public static final String REST_API_VERSION = "rest api v2";
    public static final String REST_API_EVENT_VERSION = "2";

    private static boolean mIsAnalyticsAvailable;
    private static int mSessionStart;
    private static String mSessionId;

    public static boolean getIsAnalyticsAvailable() {
        return mIsAnalyticsAvailable;
    }

    public static void setIsAnalyticsAvailable(boolean isAnalyticsAvailable) {
        mIsAnalyticsAvailable = isAnalyticsAvailable;
    }

    public static int getSessionStartTimestamp() {
        return mSessionStart;
    }

    public static void setSessionStartTimestamp(int sessionStart) {
        mSessionStart = sessionStart;
    }

    public static String getSessionId() {
        return mSessionId;
    }

    public static void setSessionId(String sessionId) {
        mSessionId = sessionId;
    }
}