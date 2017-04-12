package com.sundyn.util;

import java.net.*;
import java.io.*;

public class Html
{
    String HTMLtext;
    
    public Html(final String urlAddress) {
        this.HTMLtext = "";
        try {
            URL url = new URL(urlAddress);
            URLConnection uc = url.openConnection();
            InputStream raw = uc.getInputStream();
            int c = -1;
            StringBuffer sb = new StringBuffer();
            while ((c = raw.read()) != -1) {
                sb.append((char)c);
            }
            this.HTMLtext = new String(sb);
            this.HTMLtext = new String(this.HTMLtext.getBytes("iso8859_1"), "UTF-8");
            sb = null;
            raw.close();
            raw = null;
            uc = null;
            url = null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getHTML() {
        return this.HTMLtext;
    }
}
