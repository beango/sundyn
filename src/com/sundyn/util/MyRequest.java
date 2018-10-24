package com.sundyn.util;

import net.sf.json.JSONArray;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class MyRequest
{
    private HttpServletRequest request;

    public MyRequest(HttpServletRequest request)
    {
        this.request = request;
    }


    public String getCurrentUrlUniteParameter()
    {
        return getCurrentUrlUniteParameter(",");
    }

    public String getCurrentUrlUniteParameter(String separator)
    {
        StringBuilder urlThisPage = new StringBuilder();
        urlThisPage.append(this.request.getRequestURI());
        Enumeration e = this.request.getParameterNames();

        urlThisPage.append("?");
        if (e.hasMoreElements())
        {
            while (e.hasMoreElements())
            {
                String key = String.valueOf(e.nextElement());
                String values = getStringValues(key, separator);
                urlThisPage.append(key);
                urlThisPage.append("=");
                urlThisPage.append(values);
                urlThisPage.append("&");
            }
        }
        return urlThisPage.substring(0, urlThisPage.length() - 1);
    }

    public double getDouble(String key)
    {
        return getDouble(key, 0.0D);
    }

    public double getDouble(String key, double defaultValue)
    {
        try
        {
            String str = this.request.getParameter(key);
            return (str == null) || (str.trim().equals("")) ? defaultValue : Double.parseDouble(str.trim());
        }
        catch (Exception ex) {
        }
        return defaultValue;
    }

    public double[] getDoubleArray(String key, double defaultValue)
    {
        try
        {
            String[] _v = this.request.getParameterValues(key);
            if ((_v != null) && (_v.length > 0))
            {
                double[] _numArr = new double[_v.length];
                for (int i = 0; i < _v.length; i++)
                {
                    try
                    {
                        _numArr[i] = Double.parseDouble(_v[i].trim());
                    }
                    catch (NumberFormatException e)
                    {
                        _numArr[i] = defaultValue;
                    }
                }
                return _numArr;
            }
        }
        catch (Exception localException)
        {
        }
        return new double[0];
    }

    public void getFillObject(Object o)
    {
        getFillObject(o, "");
    }

    public void getFillObject(Object o, String clazzName)
    {
        try
        {
            BeanInfo info = Introspector.getBeanInfo(o.getClass());
            PropertyDescriptor[] descritors = info.getPropertyDescriptors();
            String name = ""; String typeName = "";
            for (int i = 0; i < descritors.length; i++)
            {
                try
                {
                    name = descritors[i].getName();
                    String pre_name = clazzName + name;
                    typeName = descritors[i].getReadMethod().getReturnType().getName();
                    String _tmp = String.valueOf(this.request.getParameter(pre_name));
                    if ((!name.equals("class")) && (!_tmp.equals("null")))
                    {
                        if (typeName.equals(String.class.getName()))
                        {
                            descritors[i].getWriteMethod().invoke(o, new Object[] { _tmp });
                        }
                        else if ((typeName.equals(Long.class.getName())) || (typeName.equals("long")))
                        {
                            descritors[i].getWriteMethod().invoke(o, new Object[] { Long.valueOf(Long.parseLong(_tmp)) });
                        }
                        else if ((typeName.equals(Integer.class.getName())) || (typeName.equals("int")))
                        {
                            descritors[i].getWriteMethod().invoke(o, new Object[] { Integer.valueOf(Integer.parseInt(_tmp)) });
                        }
                        else if ((typeName.equals(Float.class.getName())) || (typeName.equals("float")))
                        {
                            descritors[i].getWriteMethod().invoke(o, new Object[] { Float.valueOf(Float.parseFloat(_tmp)) });
                        }
                        else if ((typeName.equals(Double.class.getName())) || (typeName.equals("double")))
                        {
                            descritors[i].getWriteMethod().invoke(o, new Object[] { Double.valueOf(Double.parseDouble(_tmp)) });
                        }
                        else if (typeName.equals(Date.class.getName()))
                        {
                            descritors[i].getWriteMethod().invoke(o, new Object[] { toDate(_tmp) });
                        }
                        else
                        {
                            Object obj = descritors[i].getReadMethod().invoke(o, new Object[0]);
                            if (obj == null)
                            {
                                obj = Class.forName(descritors[i].getReadMethod().getReturnType().getName()).newInstance();
                                descritors[i].getWriteMethod().invoke(o, new Object[] { obj });
                            }
                            getFillObject(obj, pre_name + ".");
                        }
                    }
                }
                catch (Exception ex)
                {
                    System.out.println(typeName + ":" + clazzName + name + ":" + ex);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public float getFloat(String key)
    {
        return getFloat(key, 0.0F);
    }

    public float getFloat(String key, float defaultValue)
    {
        try
        {
            String str = this.request.getParameter(key);
            return (str == null) || (str.trim().equals("")) ? defaultValue : Float.parseFloat(str.trim());
        }
        catch (Exception ex) {
        }
        return defaultValue;
    }

    public float[] getFloatArray(String key, float defaultValue)
    {
        try
        {
            String[] _v = this.request.getParameterValues(key);
            if ((_v != null) && (_v.length > 0))
            {
                float[] _numArr = new float[_v.length];
                for (int i = 0; i < _v.length; i++)
                {
                    try
                    {
                        _numArr[i] = Float.parseFloat(_v[i].trim());
                    }
                    catch (NumberFormatException e)
                    {
                        _numArr[i] = defaultValue;
                    }
                }
                return _numArr;
            }
        }
        catch (Exception localException)
        {
        }
        return new float[0];
    }

    public String getInputValue(String key)
    {
        return getString(key, "").replaceAll("\"", "&quot;").replaceAll("'", "&#039;");
    }

    public int getInt(String key)
    {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue)
    {
        try
        {
            String str = this.request.getParameter(key);
            return (str == null) || (str.trim().equals("")) ? defaultValue : Integer.parseInt(str.trim());
        }
        catch (Exception ex) {
        }
        return defaultValue;
    }

    public int[] getIntArray(String key, int defaultValue)
    {
        try
        {
            String[] _v = this.request.getParameterValues(key);
            if ((_v != null) && (_v.length > 0))
            {
                int[] _numArr = new int[_v.length];
                for (int i = 0; i < _v.length; i++)
                {
                    try
                    {
                        _numArr[i] = Integer.parseInt(_v[i].trim());
                    }
                    catch (NumberFormatException e)
                    {
                        _numArr[i] = defaultValue;
                    }
                }
                return _numArr;
            }
        }
        catch (Exception localException)
        {
        }
        return new int[0];
    }

    public long getLong(String key)
    {
        return getLong(key, 0L);
    }

    public long getLong(String key, long defaultValue)
    {
        try
        {
            String str = this.request.getParameter(key);
            return (str == null) || (str.trim().equals("")) ? defaultValue : Long.parseLong(str.trim());
        }
        catch (Exception ex) {
        }
        return defaultValue;
    }

    public long[] getLongArray(String key, long defaultValue)
    {
        try
        {
            String[] _v = this.request.getParameterValues(key);
            if ((_v != null) && (_v.length > 0))
            {
                long[] _numArr = new long[_v.length];
                for (int i = 0; i < _v.length; i++)
                {
                    try
                    {
                        _numArr[i] = Long.parseLong(_v[i].trim());
                    }
                    catch (NumberFormatException e)
                    {
                        _numArr[i] = defaultValue;
                    }
                }
                return _numArr;
            }
        }
        catch (Exception localException)
        {
        }
        return new long[0];
    }


    public String getRefererURL()
    {
        return this.request.getHeader("referer");
    }

    public String getRequestURI()
    {
        return this.request.getRequestURI().substring(this.request.getContextPath().length());
    }

    public String getSecureString(String key)
    {
        return getSecureString(key, "");
    }

    public String getSecureString(String key, String defaultValue)
    {
        String value = this.request.getParameter(key);
        return value == null ? defaultValue : filterInject(value);
    }

    public String getString(String key)
    {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue)
    {
        String value = this.request.getParameter(key);
        return value == null ? defaultValue : value;
    }

    public String[] getStringArray(String key)
    {
        try
        {
            String[] _v = this.request.getParameterValues(key);
            if ((_v != null) && (_v.length > 0))
            {
                return _v;
            }
        }
        catch (Exception localException)
        {
        }
        return new String[0];
    }

    public String[] getStringArray(String key, boolean delEmpty)
    {
        return getStringArray(key, delEmpty, false);
    }

    public String[] getStringArray(String key, boolean delEmpty, boolean isSecure)
    {
        if (!delEmpty)
        {
            return getStringArray(key);
        }
        try
        {
            String[] _v = this.request.getParameterValues(key);
            if ((_v != null) && (_v.length > 0))
            {
                List list = new ArrayList();
                for (int i = 0; i < _v.length; i++)
                {
                    if ((_v[i] != null) && (_v[i].trim().length() != 0))
                    {
                        list.add(isSecure ? filterInject(_v[i].trim()) : _v[i].trim());
                    }
                }
                _v = new String[list.size()];
                for (int i = 0; i < list.size(); i++)
                {
                    _v[i] = ((String)list.get(i));
                }
                return _v;
            }
        }
        catch (Exception localException)
        {
        }
        return new String[0];
    }

    public String getStringValues(String key)
    {
        return getStringValues(key, ",", false);
    }

    public String getStringValues(String key, String separator)
    {
        return getStringValues(key, separator, false);
    }

    public String getStringValues(String key, String separator, boolean isSecure)
    {
        StringBuilder value = new StringBuilder();
        String[] _v = this.request.getParameterValues(key);
        if ((_v != null) && (_v.length > 0))
        {
            if (isSecure)
            {
                value.append(filterInject(String.valueOf(_v[0]).replaceAll(separator, "")));
                for (int i = 1; i < _v.length; i++)
                {
                    value.append(separator);
                    value.append(filterInject(String.valueOf(_v[i]).replaceAll(separator, "")));
                }
            }
            else
            {
                value.append(String.valueOf(_v[0]).replaceAll(separator, ""));
                for (int i = 1; i < _v.length; i++)
                {
                    value.append(separator);
                    value.append(String.valueOf(_v[i]).replaceAll(separator, ""));
                }
            }
        }
        return value.toString();
    }

    private static String filterInject(String str)
    {
        String injectstr = "\\||\\&|\\*|\\?|exec\\s|drop\\s|insert\\s|select\\s|delete\\s|update\\s|truncate\\s";
        Pattern regex = Pattern.compile(injectstr, 226);
        Matcher matcher = regex.matcher(str);
        str = matcher.replaceAll("");
        str = str.replace("'", "''");
        str = str.replace(";", "；");
        str = str.replace("<", "＜");
        str = str.replace(">", "＞");
        str = str.replace("(", "（");
        str = str.replace(")", "）");
        str = str.replace("%", "％");
        return str;
    }

    private static Date toDate(String value) throws Exception
    {
        Date obj = null;
        if (value != null)
        {
            value = value.trim();
            int ii = value.length();
            String format = "yyyy-MM-dd";
            switch (ii)
            {
                case 19:
                    format = "yyyy-MM-dd HH:mm:ss";
                    break;
                case 10:
                    format = "yyyy-MM-dd";
                    break;
                case 7:
                    format = "yyyy-MM";
                    break;
                case 4:
                    format = "yyyy";
                    break;
                case 16:
                    format = "yyyy-MM-dd HH:mm";
                    break;
                case 13:
                    format = "yyyy-MM-dd HH";
                    break;
                case 23:
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            obj = sdf.parse(value);
        }
        return obj;
    }

    public JSONArray getJson(String key) {
        String value = this.request.getParameter(key);
        JSONArray ja = new JSONArray();
        return ja.fromObject(value);
    }
}
