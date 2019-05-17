package com.sundyn.util;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class M7Info
{
    private static M7Info m7Info;
    private static Map m7ls;
    
    public static synchronized M7Info getInstance() {
        if (M7Info.m7Info == null) {
            M7Info.m7Info = new M7Info();
            M7Info.m7ls = listToMap(read());
        }
        return M7Info.m7Info;
    }
    
    public boolean add(final Map m) {
        if (M7Info.m7ls == null) {
            M7Info.m7ls = new HashMap();
        }
        M7Info.m7ls.put(m.get("mac"), m);
        return true;
    }
    
    public static Map<String, Object> listToMap(final List ls) {
        final Map<String, Object> m = new HashMap<String, Object>();
        for (int i = 0; i < ls.size(); ++i) {
            final Map temp = (Map) ls.get(i);
            m.put(temp.get("mac").toString(), temp);
        }
        return m;
    }
    
    public static List getList() {
        final List ls = new ArrayList();
        if (M7Info.m7ls != null) {
            final Collection col = M7Info.m7ls.values();
            final Iterator iter = col.iterator();
            while (iter.hasNext()) {
                ls.add(iter.next());
            }
        }
        Collections.sort((List<Object>)ls, new ListComparator());
        return ls;
    }
    
    public static void Save() {
        final List ls = getList();
        final Element root = new Element("ls");
        for (int i = 0; i < ls.size(); ++i) {
            final Map m = (Map) ls.get(i);
            final Element m7Info = new Element("m7Info");
            final Element mac = new Element("mac");
            mac.setText(m.get("mac").toString());
            m7Info.addContent((Content)mac);
            final Element ip = new Element("ip");
            ip.setText(m.get("ip").toString());
            m7Info.addContent((Content)ip);
            final Element version = new Element("version");
            version.setText(m.get("version").toString());
            m7Info.addContent((Content)version);
            final Element windowName = new Element("windowName");
            windowName.setText(m.get("windowName").toString());
            m7Info.addContent((Content)windowName);
            final Element playListName = new Element("playListName");
            playListName.setText(m.get("playListName").toString());
            m7Info.addContent((Content)playListName);
            final Element date = new Element("date");
            date.setText(m.get("date").toString());
            m7Info.addContent((Content)date);
            root.addContent((Content)m7Info);
        }
        try {
            ActionContext ac = ActionContext.getContext();
            ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
            String path = sc.getRealPath("/");
            final Format format = Format.getPrettyFormat();
            format.setIndent("    ");
            format.setEncoding("gb2312");
            XMLOutputter XMLOut = new XMLOutputter(format);
            final Document doc = new Document(root);
            OutputStream os = new FileOutputStream(path + "update" + File.separator + "m7Info.xml");
            XMLOut.output(doc, os);
            os.close();
            os = null;
            XMLOut = null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List read() {
        final List ls = new ArrayList();
        SAXBuilder sb = new SAXBuilder();
        try {
            ActionContext ac = ActionContext.getContext();
            ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
            String path = sc.getRealPath("/");
            Document doc = sb.build(path + "update" + File.separator + "m7Info.xml");
            Element root = doc.getRootElement();
            final List e_ls = root.getChildren();
            for (int i = 0; i < e_ls.size(); ++i) {
                final Map m = new HashMap();
                final Element e_temp = (Element) e_ls.get(i);
                m.put("mac", e_temp.getChildText("mac"));
                m.put("ip", e_temp.getChildText("ip"));
                m.put("version", e_temp.getChildText("version"));
                m.put("windowName", e_temp.getChildText("windowName"));
                m.put("playListName", e_temp.getChildText("playListName"));
                m.put("date", e_temp.getChildText("date"));
                ls.add(m);
            }
            root = null;
            doc = null;
            sb = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }
    
    public static List getList(final boolean flag) {
        ActionContext ac = ActionContext.getContext();
        ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
        String path = sc.getRealPath("/");
        path = path + "update" + File.separator + "m7Info.xml";
        if (flag) {
            return read();
        }
        final List ls = getList();
        if (ls.size() > 0) {
            return ls;
        }
        return read();
    }
}
