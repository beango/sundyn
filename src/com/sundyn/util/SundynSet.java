package com.sundyn.util;

import org.apache.struts2.ServletActionContext;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SundynSet {
	private static SundynSet sundynSet;
    private Map<String, String> m_system;
    private Map<String, String> m_content;
    private Map<String, Object> m_worktime;
    private Map<String, String> m_work2;
    private Map<String, String> m_work3;
    private Map<String, String> m_work4;
    private Map<String, String> m_employee;
    private List l_m7Temp;
    private List l_star;
    private static String setPath;

    public Map<String, String> getM_work4() {
        return this.m_work4;
    }

    public void setM_work4(final Map<String, String> m_work4) {
        this.m_work4 = m_work4;
    }

    public static synchronized SundynSet getInstance(final String path) throws JDOMException, IOException {
        SundynSet.setPath = path + "update" + File.separator + "set.xml";
        if (SundynSet.sundynSet == null) {
            SundynSet.sundynSet = new SundynSet(SundynSet.setPath);
        }
        return SundynSet.sundynSet;
    }

    private SundynSet(final String path) throws JDOMException, IOException {
        final SAXBuilder sb = new SAXBuilder();
        final Document doc = sb.build(path);
        String result = null;
        try {
            try {
                final File file = new File(this.getClass().getResource("/").toURI());
                result = file.getAbsolutePath();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception ex) {}
        final Element root = doc.getRootElement();
        final Element system = root.getChild("system");
        (this.m_system = new HashMap<String, String>()).put("camera", system.getChild("camera").getText());
        this.m_system.put("k7", system.getChild("k7").getText());
        this.m_system.put("star", system.getChild("star").getText());
        this.m_system.put("bind", system.getChild("bind").getText());
        this.m_system.put("guide", system.getChild("guide").getText());
        this.m_system.put("tipLanguage", system.getChild("tipLanguage").getText());
        final Element content = root.getChild("content");
        (this.m_content = new HashMap<String, String>()).put("title", content.getChild("title").getText());
        this.m_content.put("logo", content.getChild("logo").getText());
        this.m_content.put("buttom", content.getChild("buttom").getText());
        this.m_content.put("requestAddress", content.getChild("requestAddress").getText());
        this.m_content.put("standard", content.getChild("standard").getText());
        final Element worktime = root.getChild("worktime");
        final Element work2 = worktime.getChild("work2");
        final Element work3 = worktime.getChild("work3");
        final Element work4 = worktime.getChild("work4");
        this.m_worktime = new HashMap<String, Object>();
        this.m_work2 = new HashMap<String, String>();
        this.m_work3 = new HashMap<String, String>();
        this.m_work4 = new HashMap<String, String>();
        this.m_work2.put("isuse", worktime.getChild("work2").getAttributeValue("isuse"));
        this.m_work2.put("sam", work2.getChildText("sam"));
        this.m_work2.put("eam", work2.getChildText("eam"));
        this.m_work2.put("spm", work2.getChildText("spm"));
        this.m_work2.put("epm", work2.getChildText("epm"));
        this.m_work3.put("isuse", work3.getAttributeValue("isuse"));
        this.m_work3.put("work31s", work3.getChildText("work31s"));
        this.m_work3.put("work31e", work3.getChildText("work31e"));
        this.m_work3.put("work32s", work3.getChildText("work32s"));
        this.m_work3.put("work32e", work3.getChildText("work32e"));
        this.m_work3.put("work33s", work3.getChildText("work33s"));
        this.m_work3.put("work33e", work3.getChildText("work33e"));
        this.m_work4.put("start", work4.getChildText("start"));
        this.m_work4.put("end", work4.getChildText("end"));
        this.m_worktime.put("work2", this.m_work2);
        this.m_worktime.put("work3", this.m_work3);
        this.m_worktime.put("work4", this.m_work4);
        final Element employeeInfo = root.getChild("employeeInfoSet");
        if (employeeInfo != null) {
            (this.m_employee = new HashMap<String, String>()).put("employeeName", employeeInfo.getChildText("employeeName"));
            this.m_employee.put("job_desc", employeeInfo.getChildText("job_desc"));
            this.m_employee.put("employeeJobNum", employeeInfo.getChildText("employeeJobNum"));
            this.m_employee.put("star", employeeInfo.getChildText("star"));
            this.m_employee.put("employeeCardNum", employeeInfo.getChildText("employeeCardNum"));
            this.m_employee.put("contact", employeeInfo.getChildText("contact"));
            this.m_employee.put("windowName", employeeInfo.getChildText("windowName"));
            this.m_employee.put("deptname", employeeInfo.getChildText("deptname"));
            this.m_employee.put("unitName", employeeInfo.getChildText("unitName"));
        }
        final Element m7Temp = root.getChild("m7Temp");
        this.l_m7Temp = new ArrayList();
        final List l_m7Temps = m7Temp.getChildren();
        for (int i = 0; i < l_m7Temps.size(); ++i) {
            final Element temp = (Element) l_m7Temps.get(i);
            final Map m_temp = new HashMap();
            m_temp.put("isuse", temp.getAttributeValue("isuse"));
            m_temp.put("tempName", temp.getChildText("tempName"));
            m_temp.put("tempUrl", temp.getChildText("tempUrl"));
            m_temp.put("k3", temp.getChildText("k3"));
            m_temp.put("k4", temp.getChildText("k4"));
            m_temp.put("k5", temp.getChildText("k5"));
            m_temp.put("k6", temp.getChildText("k6"));
            this.l_m7Temp.add(m_temp);
        }
        final Element starLevel = root.getChild("starLevel");
        this.l_star = new ArrayList();
        final List l_stars = starLevel.getChildren();
        for (int j = 0; j < l_stars.size(); ++j) {
            final Element star = (Element) l_stars.get(j);
            final Map m_star = new HashMap();
            m_star.put("star10", star.getChildText("star10"));
            m_star.put("star100", star.getChildText("star100"));
            m_star.put("star", star.getChildText("star"));
            this.l_star.add(m_star);
        }
    }

    public Map<String, String> getM_system() {
        return this.m_system;
    }

    public void setM_system(final Map<String, String> m_system) {
        this.m_system = m_system;
    }

    public Map<String, String> getM_content() {
        return this.m_content;
    }

    public void setM_content(final Map<String, String> m_content) {
        this.m_content = m_content;
    }

    public Map<String, Object> getM_worktime() {
        return this.m_worktime;
    }

    public void setM_worktime(final Map<String, Object> m_worktime) {
        this.m_worktime = m_worktime;
    }

    public Map<String, String> getM_work2() {
        return this.m_work2;
    }

    public void setM_work2(final Map<String, String> m_work2) {
        this.m_work2 = m_work2;
    }

    public Map<String, String> getM_work3() {
        return this.m_work3;
    }

    public void setM_work3(final Map<String, String> m_work3) {
        this.m_work3 = m_work3;
    }

    public List getL_m7Temp() {
        return this.l_m7Temp;
    }

    public void setL_m7Temp(final List temp) {
        this.l_m7Temp = temp;
    }

    public List getL_star() {
        return this.l_star;
    }

    public void setL_star(final List l_star) {
        this.l_star = l_star;
    }

    public Map<String, String> getM_employee() {
        return this.m_employee;
    }

    public void setM_employee(final Map<String, String> mEmployee) {
        this.m_employee = mEmployee;
    }

    public static void setSundynSet(final SundynSet sundynSet) {
        SundynSet.sundynSet = sundynSet;
    }

    public void update(final Map m_system, final Map m_content, final Map m_work2, final List l_star) throws JDOMException, IOException {
        final String basepath = ServletActionContext.getServletContext().getRealPath("/");
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(String.valueOf(basepath) + "update" + File.separator + "set.xml");
        Element root = doc.getRootElement();
        if (m_system != null && !m_system.equals(this.m_system)) {
            this.m_system = (Map<String, String>)m_system;
            root.removeChild("system");
            final Element system = new Element("system");
            final Element camera = new Element("camera").setText(m_system.get("camera").toString());
            final Element k7 = new Element("k7").setText(m_system.get("k7").toString());
            final Element star = new Element("star").setText(m_system.get("star").toString());
            final Element bind = new Element("bind").setText(m_system.get("bind").toString());
            final Element guide = new Element("guide").setText(m_system.get("guide").toString());
            final Element tipLanguage = new Element("tipLanguage").setText(m_system.get("tipLanguage").toString());
            system.addContent((Content)camera);
            system.addContent((Content)k7);
            system.addContent((Content)star);
            system.addContent((Content)bind);
            system.addContent((Content)guide);
            system.addContent((Content)tipLanguage);
            root.addContent((Content)system);
        }
        if (m_content != null && !m_content.equals(this.m_content)) {
            this.m_content = (Map<String, String>)m_content;
            root.removeChild("content");
            final Element content = new Element("content");
            final Element title = new Element("title").setText(m_content.get("title").toString());
            final Element logo = new Element("logo").setText(m_content.get("logo").toString());
            final Element buttom = new Element("buttom").setText(m_content.get("buttom").toString());
            final Element requestAddress = new Element("requestAddress").setText(m_content.get("requestAddress").toString());
            final Element standard = new Element("standard").setText(m_content.get("standard").toString());
            content.addContent((Content)title);
            content.addContent((Content)logo);
            content.addContent((Content)buttom);
            content.addContent((Content)requestAddress);
            content.addContent((Content)standard);
            root.addContent((Content)content);
        }
        if (m_work2 != null && !m_work2.equals(this.m_work2)) {
            this.m_work2 = (Map<String, String>)m_work2;
            final Element worktime = root.getChild("worktime");
            worktime.removeChild("work2");
            final Element work2 = new Element("work2").setAttribute("isuse", "true");
            final Element sam = new Element("sam").setText(m_work2.get("sam").toString());
            final Element eam = new Element("eam").setText(m_work2.get("eam").toString());
            final Element spm = new Element("spm").setText(m_work2.get("spm").toString());
            final Element epm = new Element("epm").setText(m_work2.get("epm").toString());
            work2.addContent((Content)sam);
            work2.addContent((Content)eam);
            work2.addContent((Content)spm);
            work2.addContent((Content)epm);
            worktime.addContent((Content)work2);
        }
        if (l_star != null && !l_star.equals(this.l_star)) {
            this.l_star = l_star;
            final Element starLevel = root.getChild("starLevel");
            starLevel.removeContent();
            for (int i = 0; i < l_star.size(); ++i) {
                final Map star2 = (Map) l_star.get(i);
                final Element e_star = new Element("star");
                final Element star3 = new Element("star10").setText(star2.get("star10").toString());
                final Element star4 = new Element("star100").setText(star2.get("star100").toString());
                final Element starx = new Element("star").setText(star2.get("star").toString());
                e_star.addContent((Content)star3);
                e_star.addContent((Content)star4);
                e_star.addContent((Content)starx);
                starLevel.addContent((Content)e_star);
            }
        }
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        FileWriter fw = new FileWriter(String.valueOf(basepath) + "update" + File.separator + "set.xml");
        XMLOut.output(doc, (Writer)fw);
        fw.close();
        fw = null;
        root = null;
        XMLOut = null;
        doc = null;
        sb = null;
    }

    public void update(final Map m_system, final Map m_content, final Map m_work2, final Map m_work4, final List l_star, final Map employeeInfo) throws JDOMException, IOException {
        final String basepath = ServletActionContext.getServletContext().getRealPath("/");
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(String.valueOf(basepath) + "update" + File.separator + "set.xml");
        Element root = doc.getRootElement();
        if (m_system != null && !m_system.equals(this.m_system)) {
            this.m_system = (Map<String, String>)m_system;
            root.removeChild("system");
            final Element system = new Element("system");
            final Element camera = new Element("camera").setText(m_system.get("camera").toString());
            final Element k7 = new Element("k7").setText(m_system.get("k7").toString());
            final Element star = new Element("star").setText(m_system.get("star").toString());
            final Element bind = new Element("bind").setText(m_system.get("bind").toString());
            final Element guide = new Element("guide").setText(m_system.get("guide").toString());
            final Element tipLanguage = new Element("tipLanguage").setText(m_system.get("tipLanguage").toString());
            system.addContent((Content)camera);
            system.addContent((Content)k7);
            system.addContent((Content)star);
            system.addContent((Content)bind);
            system.addContent((Content)guide);
            system.addContent((Content)tipLanguage);
            root.addContent((Content)system);
        }
        if (m_content != null && !m_content.equals(this.m_content)) {
            this.m_content = (Map<String, String>)m_content;
            root.removeChild("content");
            final Element content = new Element("content");
            final Element title = new Element("title").setText(m_content.get("title").toString());
            final Element logo = new Element("logo").setText(m_content.get("logo").toString());
            final Element buttom = new Element("buttom").setText(m_content.get("buttom").toString());
            final Element requestAddress = new Element("requestAddress").setText(m_content.get("requestAddress").toString());
            final Element standard = new Element("standard").setText(m_content.get("standard").toString());
            content.addContent((Content)title);
            content.addContent((Content)logo);
            content.addContent((Content)buttom);
            content.addContent((Content)requestAddress);
            content.addContent((Content)standard);
            root.addContent((Content)content);
        }
        if (m_work2 != null && !m_work2.equals(this.m_work2)) {
            this.m_work2 = (Map<String, String>)m_work2;
            final Element worktime = root.getChild("worktime");
            worktime.removeChild("work2");
            final Element work2 = new Element("work2").setAttribute("isuse", "true");
            final Element sam = new Element("sam").setText(m_work2.get("sam").toString());
            final Element eam = new Element("eam").setText(m_work2.get("eam").toString());
            final Element spm = new Element("spm").setText(m_work2.get("spm").toString());
            final Element epm = new Element("epm").setText(m_work2.get("epm").toString());
            work2.addContent((Content)sam);
            work2.addContent((Content)eam);
            work2.addContent((Content)spm);
            work2.addContent((Content)epm);
            worktime.addContent((Content)work2);
        }
        if (m_work4 != null && !m_work4.equals(this.m_work4)) {
            this.m_work4 = (Map<String, String>)m_work4;
            final Element worktime = root.getChild("worktime");
            worktime.removeChild("work4");
            final Element work3 = new Element("work4");
            final Element start = new Element("start").setText(String.valueOf(m_work4.get("start")));
            final Element end = new Element("end").setText(String.valueOf(m_work4.get("end")));
            work3.addContent((Content)start);
            work3.addContent((Content)end);
            worktime.addContent((Content)work3);
        }
        if (l_star != null && !l_star.equals(this.l_star)) {
            this.l_star = l_star;
            final Element starLevel = root.getChild("starLevel");
            starLevel.removeContent();
            for (int i = 0; i < l_star.size(); ++i) {
                final Map star2 = (Map) l_star.get(i);
                final Element e_star = new Element("star");
                final Element star3 = new Element("star10").setText(star2.get("star10").toString());
                final Element star4 = new Element("star100").setText(star2.get("star100").toString());
                final Element starx = new Element("star").setText(star2.get("star").toString());
                e_star.addContent((Content)star3);
                e_star.addContent((Content)star4);
                e_star.addContent((Content)starx);
                starLevel.addContent((Content)e_star);
            }
        }
        if (employeeInfo != null) {
            this.m_employee = (Map<String, String>)employeeInfo;
            Element employeeInfoSet = root.getChild("employeeInfoSet");
            if (employeeInfoSet == null) {
                employeeInfoSet = new Element("employeeInfoSet");
                root.addContent((Content)employeeInfoSet);
            }
            employeeInfoSet.removeContent();
            final Element employeeName = new Element("employeeName").setText(employeeInfo.get("employeeName").toString());
            final Element job_desc = new Element("job_desc").setText(employeeInfo.get("job_desc").toString());
            final Element sex = new Element("employeeJobNum").setText(employeeInfo.get("employeeJobNum").toString());
            final Element star5 = new Element("star").setText(employeeInfo.get("star").toString());
            final Element employeeCardNum = new Element("employeeCardNum").setText(employeeInfo.get("employeeCardNum").toString());
            final Element contact = new Element("phone").setText(employeeInfo.get("phone").toString());
            final Element windowName = new Element("windowName").setText(employeeInfo.get("windowName").toString());
            final Element deptname = new Element("deptname").setText(employeeInfo.get("deptname").toString());
            final Element unitName = new Element("unitName").setText(employeeInfo.get("unitName").toString());
            employeeInfoSet.addContent((Content)employeeName);
            employeeInfoSet.addContent((Content)job_desc);
            employeeInfoSet.addContent((Content)sex);
            employeeInfoSet.addContent((Content)star5);
            employeeInfoSet.addContent((Content)employeeCardNum);
            employeeInfoSet.addContent((Content)contact);
            employeeInfoSet.addContent((Content)windowName);
            employeeInfoSet.addContent((Content)deptname);
            employeeInfoSet.addContent((Content)unitName);
        }
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        FileWriter fw = new FileWriter(String.valueOf(basepath) + "update" + File.separator + "set.xml");
        XMLOut.output(doc, (Writer)fw);
        fw.close();
        fw = null;
        root = null;
        XMLOut = null;
        doc = null;
        sb = null;
    }

    public void delete(final Integer index) throws JDOMException, IOException {
        final String basepath = ServletActionContext.getServletContext().getRealPath("/");
        final SAXBuilder sb = new SAXBuilder();
        final Document doc = sb.build(String.valueOf(basepath) + "update" + File.separator + "set.xml");
        final Element root = doc.getRootElement();
        final Element m7Temp = root.getChild("m7Temp");
        final Element temp = (Element) m7Temp.getChildren().get(index);
        final String path = String.valueOf(basepath) + "m7temp" + File.separator + temp.getChildText("tempUrl");
        final File f_m7path = new File(path);
        if (f_m7path.exists()) {
            f_m7path.delete();
        }
        doc.getRootElement().getChild("m7Temp").removeContent((Content)temp);
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(basepath) + "update" + File.separator + "set.xml"));
        XMLOut = null;
        SundynSet.sundynSet = new SundynSet(SundynSet.setPath);
    }

    public Map add(final File f) throws JDOMException, IOException {
        final String basepath = ServletActionContext.getServletContext().getRealPath("/");
        final SAXBuilder sb = new SAXBuilder();
        final Document doc = sb.build(String.valueOf(basepath) + "update" + File.separator + "set.xml");
        final Element root = doc.getRootElement();
        final Element m7Temp = root.getChild("m7Temp");
        final List ls = m7Temp.getChildren();
        Element temp = null;
        String tempUrl = "";
        if (ls.size() > 0) {
            temp = (Element) ls.get(ls.size() - 1);
            tempUrl = temp.getChildText("tempUrl");
            tempUrl = "temp" + (Integer.valueOf(tempUrl.substring(4)) + 1);
            final File f_temp = new File(String.valueOf(basepath) + "m7temp" + File.separator + tempUrl);
            if (f_temp.exists()) {
                f_temp.delete();
                f_temp.mkdirs();
            }
            else {
                f_temp.mkdirs();
            }
        }
        else {
            tempUrl = "temp1";
            final File f_temp = new File(String.valueOf(basepath) + "m7temp" + File.separator + "temp1");
            if (f_temp.exists()) {
                f_temp.delete();
                f_temp.mkdirs();
            }
            else {
                f_temp.mkdirs();
            }
        }
        MyFile.extZipFileList(f, String.valueOf(basepath) + "m7temp" + File.separator + tempUrl);
        final String aa = String.valueOf(basepath) + "m7temp" + File.separator + tempUrl + File.separator + "info.xml";
        Document docInfo = sb.build(String.valueOf(basepath) + "m7temp" + File.separator + tempUrl + File.separator + "info.xml");
        final String tempName = docInfo.getRootElement().getChildText("tempName");
        docInfo = null;
        temp = new Element("temp");
        temp.addContent((Content)new Element("tempName").setText(tempName));
        temp.addContent((Content)new Element("tempUrl").setText(tempUrl));
        temp.addContent((Content)new Element("k3").setText("true"));
        temp.addContent((Content)new Element("k4").setText("false"));
        temp.addContent((Content)new Element("k5").setText("false"));
        temp.addContent((Content)new Element("k6").setText("false"));
        temp.setAttribute("isuse", "false");
        m7Temp.addContent((Content)temp);
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(basepath) + "update" + File.separator + "set.xml"));
        XMLOut = null;
        final Map res = new HashMap();
        res.put("tempName", tempName);
        res.put("tempUrl", tempUrl);
        SundynSet.sundynSet = new SundynSet(SundynSet.setPath);
        return res;
    }

    public void select(final Integer i, final String k) throws JDOMException, IOException {
        final String basepath = ServletActionContext.getServletContext().getRealPath("/");
        final SAXBuilder sb = new SAXBuilder();
        final Document doc = sb.build(String.valueOf(basepath) + "update" + File.separator + "set.xml");
        final Element root = doc.getRootElement();
        final Element m7Temp = root.getChild("m7Temp");
        final List l = m7Temp.getChildren();
        String tempUrl = "";
        for (int j = 0; j < l.size(); ++j) {
            final Element temp = (Element) l.get(j);
            if (i == j) {
                temp.setAttribute("isuse", "true");
                temp.getChild("k3").setText("false");
                temp.getChild("k4").setText("false");
                temp.getChild("k5").setText("false");
                temp.getChild("k6").setText("false");
                temp.getChild(k).setText("true");
                tempUrl = temp.getChildText("tempUrl");
            }
            else {
                temp.setAttribute("isuse", "false");
            }
        }
        final Document cfDoc = sb.build(String.valueOf(basepath) + "UpdateFile" + File.separator + "CONFIG.XML");
        final Element cfroot = cfDoc.getRootElement();
        String version = cfroot.getChild("version").getText();
        version = new StringBuilder().append(Integer.parseInt(version) + 1).toString();
        cfroot.getChild("version").setText(version);
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        XMLOut.output(cfroot, (OutputStream)new FileOutputStream(String.valueOf(basepath) + "UpdateFile" + File.separator + "CONFIG.XML"));
        XMLOut.output(cfroot, (OutputStream)new FileOutputStream(String.valueOf(basepath) + "update" + File.separator + "CONFIG.XML"));
        XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(basepath) + "update" + File.separator + "set.xml"));
        tempUrl = String.valueOf(basepath) + "m7temp" + File.separator + tempUrl + File.separator + k;
        Update up = new Update(tempUrl);
        up.add(String.valueOf(basepath) + "update" + File.separator + "CONFIG.XML");
        up.createUpdateFile(String.valueOf(basepath) + "update" + File.separator + "M7Update" + version + ".bin");
        final File f = new File(tempUrl);
        final File[] fs = f.listFiles();
        for (int m = 0; m < fs.length; ++m) {
            MyFile.copy(fs[m], new File(String.valueOf(basepath) + "UpdateFile" + File.separator + fs[m].getName()));
        }
        up = new Update(String.valueOf(basepath) + "UpdateFile");
        up.createUpdateFile(String.valueOf(basepath) + "update" + File.separator + "M7Update.bin");
        XMLOut = null;
        SundynSet.sundynSet = new SundynSet(SundynSet.setPath);
    }
}
