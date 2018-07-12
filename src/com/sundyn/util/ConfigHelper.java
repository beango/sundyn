package com.sundyn.util;

import org.jfree.util.Log;

import java.io.Serializable;
import java.util.Properties;

public class ConfigHelper implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Properties props = new Properties();
    static
    {
        try
        {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.txt"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getValue(String key)
    {
        Log.debug("?>>>>>>>>>>>>>>>>" + key);
        return props.getProperty(key);
    }

    public static void updateProperties(String key, String value)
    {
        props.setProperty(key, value);
    }

    public static void main(String[] args)
    {
        String v = ConfigHelper.getValue("retime");
        long l = Long.parseLong(v);
        System.out.println(l);
    }
}
