package com.derekentringer.gizmo.analytics.util;

import com.derekentringer.gizmo.analytics.model.AnalyticsModel;
import com.derekentringer.gizmo.analytics.model.AnalyticsSettings;
import com.derekentringer.gizmo.manager.LocalDataManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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
        return getPlatform() + " " + operatingSystem;
    }

    public static String getMacAddress() {
        InetAddress ip;
        StringBuilder sb = new StringBuilder();
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static int getTimestamp() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public static String getRandomUUID() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString();
    }

    public static String getSessionNum() {
        int sessionNum;
        AnalyticsModel analyticsModel = new AnalyticsModel();
        if (LocalDataManager.loadGameAnalyticsData() != null) {
            sessionNum = LocalDataManager.loadGameAnalyticsData().getSessionNum() + 1;
            analyticsModel.setSessionNum(sessionNum);
        }
        else {
            sessionNum = 1;
            analyticsModel.setSessionNum(sessionNum);
        }

        LocalDataManager.saveGameAnalyticsData(analyticsModel);
        return String.valueOf(sessionNum);
    }

    public static int calculateSessionLength() {
        return getTimestamp() - AnalyticsSettings.getSessionStart();
    }

}