package com.derekentringer.gizmo.analytics.util;

import com.derekentringer.gizmo.analytics.model.AnalyticsSettings;
import com.derekentringer.gizmo.analytics.model.LocalAnalyticsModel;
import com.derekentringer.gizmo.manager.LocalDataManager;

import java.util.UUID;

public class AnalyticsUtils {

    public static String getDevice() {
        String platform = System.getProperty("os.name").toLowerCase();
        return platform;
    }

    public static String getPlatform() {
        String platform = System.getProperty("os.name").toLowerCase();
        if (platform.contains("win")) {
            return "windows";
        }
        else if (platform.contains("mac")) {
            return "mac_osx";
        }
        else if (platform.contains("android")) {
            return "android";
        }
        else if (platform.contains("ios")) {
            return "ios";
        }
        else {
            return "nacl";
        }
    }

    public static String getManufacturer() {
        String platform = System.getProperty("os.name").toLowerCase();
        if (platform.contains("win")) {
            return "windows";
        }
        else if (platform.contains("mac")
            || platform.contains("ios")
                || platform.contains("apple")) {
            return "apple";
        }
        else if (platform.contains("nix") ||
                platform.contains("nux") ||
                platform.contains("aix")) {
            return "unix";
        }
        else if (platform.contains("android")) {
            return "android";
        }
        else {
            return "other";
        }
    }

    public static String getOsVersion() {
        String operatingSystem = System.getProperty("os.version").toLowerCase();
        String os = getPlatform() + " " + operatingSystem;
        //os.replace(" ", "-");

        if (os.contains("-")) {
            os = os.replace(os.substring(os.indexOf("-"), os.length()), "");
        }
        return os;
    }

    public static int getTimestamp() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public static String getRandomSessionUUID() {
        UUID randomUUID = UUID.randomUUID();
        AnalyticsSettings.setSessionId(randomUUID.toString());
        return randomUUID.toString();
    }

    public static String getDeviceId() {
        String deviceId = "";
        LocalAnalyticsModel analyticsModel = LocalDataManager.loadGameAnalyticsData();
        if (analyticsModel != null) {
            if (analyticsModel.getDeviceId() != null) {
                deviceId = analyticsModel.getDeviceId();
            }
            else {
                String uuid = UUID.randomUUID().toString();
                analyticsModel.setDeviceId(uuid);
                deviceId = uuid;
            }
        }
        else {
            String uuid = UUID.randomUUID().toString();
            analyticsModel = new LocalAnalyticsModel();
            analyticsModel.setDeviceId(uuid);
            deviceId = uuid;
        }

        LocalDataManager.saveGameAnalyticsData(analyticsModel);
        return deviceId;
    }

    public static String getSessionNum() {
        int sessionNum = 0;
        LocalAnalyticsModel analyticsModel = LocalDataManager.loadGameAnalyticsData();
        if (analyticsModel != null) {
            if (analyticsModel.getSessionNum() != 0) {
                sessionNum = analyticsModel.getSessionNum();
            }
            else {
                sessionNum = 1;
                analyticsModel.setSessionNum(sessionNum);
            }
        }
        else {
            analyticsModel = new LocalAnalyticsModel();
            sessionNum = 1;
            analyticsModel.setSessionNum(sessionNum);
        }

        LocalDataManager.saveGameAnalyticsData(analyticsModel);
        return String.valueOf(sessionNum);
    }

    public static void incrementSessionNum() {
        LocalAnalyticsModel analyticsModel;
        if (LocalDataManager.loadGameAnalyticsData() != null) {
            analyticsModel = LocalDataManager.loadGameAnalyticsData();
            int sessionNum = analyticsModel.getSessionNum() + 1;
            analyticsModel.setSessionNum(sessionNum);
            LocalDataManager.saveGameAnalyticsData(analyticsModel);
        }
    }

    public static int calculateSessionLength() {
        return getTimestamp() - AnalyticsSettings.getSessionStartTimestamp();
    }

}