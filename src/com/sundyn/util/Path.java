package com.sundyn.util;

import java.net.*;

public class Path
{
    public static final String getRootPath() {
        URL url = null;
        url = Path.class.getResource("Path.class");
        String strURL = url.toString();
        strURL = strURL.substring(strURL.indexOf(47) + 1);
        strURL = strURL.substring(0, strURL.indexOf("WEB-INF/classes/"));
        return strURL;
    }
}
