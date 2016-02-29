package com.derekentringer.gizmo.analytics.util;

import com.derekentringer.gizmo.analytics.model.AnalyticsModel;
import com.derekentringer.gizmo.analytics.model.AnalyticsSettings;
import com.derekentringer.gizmo.manager.LocalDataManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

public class AnalyticsUtils {

    public static String getPlatform() {
        String platform = System.getProperty("os.name").toLowerCase();
        return platform;
    }

    public static String getPlatformManufacturer() {
        if (getPlatform().contains("win")) {
            return "microsoft";
        }
        else if (getPlatform().contains("mac")) {
            return "apple";
        }
        else if (getPlatform().contains("nix") ||
                getPlatform().contains("nux") ||
                getPlatform().contains("aix")) {
            return "unix";
        }
        else {
            return "other";
        }
    }

    public static String getOsVersion() {
        String operatingSystem = System.getProperty("os.version").toLowerCase();
        return operatingSystem;
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

    public static String getTimestamp() {
        Date date = new Date();
        long localTime = date.getTime();
        long serverTime = AnalyticsSettings.getServerTimestampOffset();
        long timeOffset = localTime - serverTime;

        if (serverTime != 0) {
            return String.valueOf(timeOffset);
        }
        else {
            return "0";
        }
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

}