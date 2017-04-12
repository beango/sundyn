package com.sundyn.utils;

import java.net.*;
import sun.net.www.protocol.http.*;
import java.io.*;

public class GetWeatherString
{
    public static void main(final String[] s) {
    }
    
    public static String getPageContent(final String strUrl, final String strPostRequest, final int maxLength) {
        final StringBuffer buffer = new StringBuffer();
        System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
        System.setProperty("sun.net.client.defaultReadTimeout", "5000");
        try {
            final URL newUrl = new URL(strUrl);
            final java.net.HttpURLConnection hConnect = (java.net.HttpURLConnection)newUrl.openConnection();
            if (strPostRequest.length() > 0) {
                hConnect.setDoOutput(true);
                final OutputStreamWriter out = new OutputStreamWriter(hConnect.getOutputStream());
                out.write(strPostRequest);
                out.flush();
                out.close();
            }
            final BufferedReader rd = new BufferedReader(new InputStreamReader(hConnect.getInputStream(), "UTF-8"));
            int ch;
            for (int length = 0; (ch = rd.read()) > -1 && (maxLength <= 0 || length < maxLength); ++length) {
                buffer.append((char)ch);
            }
            rd.close();
            hConnect.disconnect();
            return buffer.toString().trim();
        }
        catch (Exception e) {
            return null;
        }
    }
}
