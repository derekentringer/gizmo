package com.derekentringer.gizmo.analytics.util;

public class GameInfo {

    public static String getPlatform() {
        String platform = System.getProperty("os.name").toLowerCase();
        return platform;
    }

    public static String getOsVersion() {
        String operatingSystem = System.getProperty("os.version").toLowerCase();
        return operatingSystem;
    }

}