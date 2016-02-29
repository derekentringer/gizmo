package com.derekentringer.gizmo.analytics.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class AnalyticsInfo {

    public static String getPlatform() {
        String platform = System.getProperty("os.name").toLowerCase();
        return platform;
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
            System.out.println(sb.toString());

        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}