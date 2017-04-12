package com.sundyn.util;

import org.apache.struts2.*;
import javax.servlet.http.*;
import java.io.*;
import org.jdom.input.*;
import org.jdom.*;
import java.util.*;

public class WeatherStand
{
    String path;
    
    public WeatherStand(final String path) {
        this.path = path;
    }
    
    public WeatherStand() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        this.path = ServletActionContext.getServletContext().getRealPath("/");
    }
    
    public List getWeatherStand() {
        final String xmlPath = String.valueOf(this.path) + "data" + File.separator + "weatherStand.xml";
        try {
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(xmlPath);
            Element root = doc.getRootElement();
            final List<?> ls = (List<?>)root.getChildren();
            final List rs = new ArrayList();
            for (int i = 0; i < ls.size(); ++i) {
                final Element temp = (Element)ls.get(i);
                final Map m = new HashMap();
                m.put("txt", temp.getChildText("txt").trim());
                m.put("img", temp.getChildText("img").trim());
                rs.add(m);
            }
            root = null;
            doc = null;
            sb = null;
            return rs;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
