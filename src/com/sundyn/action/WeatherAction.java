package com.sundyn.action;

import com.opensymphony.xwork2.*;
import org.apache.struts2.*;
import org.jdom.input.*;
import javax.servlet.http.*;
import org.jdom.*;
import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import net.sf.json.*;
import net.sf.json.JSONArray;

import org.jdom.output.*;
import java.io.*;
import com.sundyn.util.*;

public class WeatherAction extends ActionSupport
{
    private static final long serialVersionUID = 4933155911640256660L;
    private String msg;
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public String weatherM7Get() throws JSONException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String xmlPath = String.valueOf(path) + "data" + File.separator + "weather.xml";
        String resultstring = "";
        String state = "success";
        JSONObject object = new JSONObject();
        try {
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(xmlPath);
            Element root = doc.getRootElement();
            final List<?> ls = (List<?>)root.getChildren();
            Element weather = null;
            for (int i = 0; i < ls.size(); ++i) {
                final Element temp = (Element)ls.get(i);
                if (temp.getChild("isUse").getText().equals("true")) {
                    weather = temp;
                    break;
                }
            }
            if (weather.getChildText("source").equals("weather")) {
                final String urlAddress = weather.getChildText("url");
                final Html html = new Html(urlAddress);
                resultstring = html.getHTML();
                if (resultstring.equals("")) {
                    state = "fail";
                }
                if (resultstring.equals("")) {
                    object = new JSONObject();
                }
                else {
                    try {
                        object = new JSONObject(resultstring);
                    }
                    catch (Exception e) {
                        state = "fail";
                        e.printStackTrace();
                    }
                }
            }
            else if (weather.getChildText("source").equals("google")) {
                final String urlAddress = weather.getChildText("url");
                sb = new SAXBuilder();
                doc = sb.build(urlAddress);
                root = doc.getRootElement();
                final List w = root.getChildren();
                final Map weatherinfo = new HashMap();
                final String t = ((Element) w.get(0)).getText();
                if (((Element) w.get(0)).getText().equals("\u9ad8\u901f\u8bbf\u95ee\u88ab\u9650\u5236\uff01")) {
                    state = "fail";
                }
                else {
                    String a = ((Element) w.get(8)).getText();
                    a = a.substring(0, a.length() - 4);
                    weatherinfo.put("img1", a);
                    weatherinfo.put("weather1", ((Element) w.get(6)).getText().split(" ")[1]);
                    weatherinfo.put("city", ((Element) w.get(1)).getText());
                    weatherinfo.put("wind1", ((Element) w.get(7)).getText());
                    weatherinfo.put("temp1", ((Element) w.get(5)).getText());
                    object.put("weatherinfo", (Object)new JSONObject(weatherinfo));
                    state = "success";
                }
            }
            else if (weather.getChildText("source").equals("hand")) {
                final Map weatherinfo2 = new HashMap();
                weatherinfo2.put("img1", weather.getChildText("weatherImg"));
                weatherinfo2.put("weather1", weather.getChildText("weather"));
                weatherinfo2.put("city", weather.getChildText("city"));
                weatherinfo2.put("wind1", weather.getChildText("f"));
                weatherinfo2.put("temp1", String.valueOf(weather.getChildText("temp")) + "\u2103");
                object.put("weatherinfo", (Object)new JSONObject(weatherinfo2));
                state = "success";
            }
            weather = null;
            root = null;
            doc = null;
            sb = null;
        }
        catch (JDOMException e2) {
            state = "fail";
            e2.printStackTrace();
        }
        catch (IOException e3) {
            state = "fail";
            e3.printStackTrace();
        }
        object.put("state", (Object)state);
        this.msg = object.toString();
        return "success";
    }
    
    public String weatherQuery() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String xmlPath = String.valueOf(path) + "data" + File.separator + "weather.xml";
        final List l = new ArrayList();
        try {
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(xmlPath);
            Element root = doc.getRootElement();
            final List<?> ls = (List<?>)root.getChildren();
            for (int i = 0; i < ls.size(); ++i) {
                final Element temp = (Element)ls.get(i);
                final Map m = new HashMap();
                m.put("source", temp.getChildText("source"));
                m.put("title", temp.getChildText("title"));
                m.put("isUse", temp.getChildText("isUse"));
                if (temp.getChildText("source").equals("hand")) {
                    m.put("temp", temp.getChildText("temp"));
                    m.put("weather", temp.getChildText("weather").trim());
                    m.put("weatherImg", temp.getChildText("weatherImg").trim());
                    m.put("f", temp.getChildText("f"));
                }
                else {
                    m.put("url", temp.getChildText("url"));
                }
                l.add(m);
            }
            root = null;
            doc = null;
            sb = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final JSONArray object = JSONArray.fromObject((Object)l);
        this.msg = object.toString();
        return "success";
    }
    
    public String weatherSave() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String ss = request.getParameter("ss");
        final String t = request.getParameter("temp");
        final String weather = request.getParameter("weather");
        final String weatherImg = request.getParameter("weatherImg");
        final String f = request.getParameter("f");
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String xmlPath = String.valueOf(path) + "data" + File.separator + "weather.xml";
        final List l = new ArrayList();
        try {
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(xmlPath);
            Element root = doc.getRootElement();
            final List<?> ls = (List<?>)root.getChildren();
            for (int i = 0; i < ls.size(); ++i) {
                final Element temp = (Element)ls.get(i);
                if (temp.getChildText("source").equals(ss)) {
                    temp.getChild("isUse").setText("true");
                }
                else {
                    temp.getChild("isUse").setText("false");
                }
                if (temp.getChildText("source").equals("hand")) {
                    temp.getChild("temp").setText(t);
                    temp.getChild("weather").setText(weather);
                    temp.getChild("weatherImg").setText(weatherImg);
                    temp.getChild("f").setText(f);
                }
            }
            final Format format = Format.getPrettyFormat();
            format.setIndent("    ");
            format.setEncoding("gb2312");
            final XMLOutputter XMLOut = new XMLOutputter(format);
            FileWriter fw = new FileWriter(xmlPath);
            XMLOut.output(doc, (Writer)fw);
            fw.close();
            fw = null;
            root = null;
            doc = null;
            sb = null;
            this.msg = "success";
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "error";
        }
        return "success";
    }
    
    public String weatherAjax() {
        final WeatherStand stand = new WeatherStand();
        final List ls = stand.getWeatherStand();
        final JSONArray o = JSONArray.fromObject((Object)ls);
        this.msg = o.toString();
        return "success";
    }
    
    public String weatherM7GetById() throws JSONException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");
        if (id == null || id.equals("")) {
            id = "101180101";
        }
        String resultstring = "";
        String state = "success";
        JSONObject object = new JSONObject();
        try {
            final String url = "http://m.weather.com.cn/data/" + id + ".html";
            final Html html = new Html(url);
            resultstring = html.getHTML();
            if (resultstring.equals("")) {
                state = "fail";
            }
            if (resultstring.equals("")) {
                object = new JSONObject();
            }
            else {
                try {
                    object = new JSONObject(resultstring);
                }
                catch (Exception e) {
                    state = "fail";
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e2) {
            state = "fail";
            e2.printStackTrace();
        }
        object.put("state", (Object)state);
        this.msg = object.toString();
        return "success";
    }
}
