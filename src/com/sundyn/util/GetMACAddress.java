package com.sundyn.util;

import java.io.*;

public class GetMACAddress
{
    public String getMACAddress(final String ipAddress) {
        String str = "";
        String strMAC = "";
        String macAddress = "";
        try {
            final Process pp = Runtime.getRuntime().exec("nbtstat -a " + ipAddress);
            final InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            final LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; ++i) {
                str = input.readLine();
                if (str != null && str.indexOf("MAC Address") > 1) {
                    strMAC = str.substring(str.indexOf("MAC Address") + 14, str.length());
                    break;
                }
            }
        }
        catch (IOException ex) {
            return "Can't Get MAC Address!";
        }
        if (strMAC.length() < 17) {
            return "Error!";
        }
        macAddress = String.valueOf(strMAC.substring(0, 2)) + strMAC.substring(3, 5) + strMAC.substring(6, 8) + strMAC.substring(9, 11) + strMAC.substring(12, 14) + strMAC.substring(15, 17);
        return macAddress;
    }
    
    public static void main(final String[] args) {
        final GetMACAddress getMACAddress = new GetMACAddress();
        System.out.println(getMACAddress.getMACAddress("192.168.100.45"));
    }
    
    public static String procAll(final String str) {
        return procStringEnd(procFirstMac(procAddress(str)));
    }
    
    public static String procAddress(final String str) {
        final int indexof = str.indexOf("Physical Address");
        if (indexof > 0) {
            return str.substring(indexof, str.length());
        }
        return str;
    }
    
    public static String procFirstMac(final String str) {
        final int indexof = str.indexOf(":");
        if (indexof > 0) {
            return str.substring(indexof + 1, str.length()).trim();
        }
        return str;
    }
    
    public static String procStringEnd(final String str) {
        final int indexof = str.indexOf("\r");
        if (indexof > 0) {
            return str.substring(0, indexof).trim();
        }
        return str;
    }
}
