package com.sundyn.util;

import org.jdom.input.*;
import java.util.*;
import org.jdom.*;
import org.jdom.output.*;
import java.io.*;

public class PieXml
{
    Document doc;
    SAXBuilder sb;
    
    public PieXml(final String path) {
        this.doc = null;
        this.sb = null;
        this.sb = new SAXBuilder();
        try {
            this.doc = this.sb.build(String.valueOf(path) + "update" + File.separator + "piedata.xml");
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public void setData(final List<?> totalList) {
        String item = "";
        String data = "";
        Integer sum = 0;
        if (totalList != null) {
            final Integer[] data_array = new Integer[totalList.size()];
            final String[] item_array = new String[totalList.size()];
            for (int i = 0; i < totalList.size(); ++i) {
                final Map m = (Map)totalList.get(i);
                item_array[i] = m.get("name").toString();
                data_array[i] = Integer.valueOf(m.get("num").toString());
            }
            for (int i = 0; i < data_array.length; ++i) {
                sum += data_array[i];
            }
            for (int i = 0; i < item_array.length; ++i) {
                item_array[i] = String.valueOf(item_array[i]) + Math.round(data_array[i] * 1.0 * 10000.0 / sum) * 1.0 / 100.0 + "%";
            }
            for (int i = 0; i < item_array.length; ++i) {
                item = String.valueOf(item) + item_array[i] + ",";
            }
            for (int i = 0; i < data_array.length; ++i) {
                data = String.valueOf(data) + data_array[i] + ",";
            }
        }
        if (item.endsWith(",")) {
            item = item.substring(0, item.length() - 1);
        }
        if (data.endsWith(",")) {
            data = data.substring(0, data.length() - 1);
        }
        final Element root = this.doc.getRootElement();
        final Element pie = root.getChild("pie");
        pie.getChild("pie_name").setText(item);
        pie.getChild("pie_num").setText(data);
    }
    
    public void setX(final String x) {
    }
    
    public void setY(final String y) {
    }
    
    public void createXml(final String path) {
        final Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter XMLOut = new XMLOutputter(format);
        final File f = new File(String.valueOf(path) + "piedata.xml");
        try {
            FileOutputStream os = new FileOutputStream(f);
            XMLOut.output(this.doc, (OutputStream)os);
            os.close();
            os = null;
            XMLOut = null;
            this.doc = null;
            this.sb = null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
