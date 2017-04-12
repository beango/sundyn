package com.sundyn.utils;

import org.jdom.output.*;
import org.jdom.*;
import java.io.*;
import java.util.*;
import com.sundyn.vo.*;

public class JavaXML
{
    public void BuildXMLDoc() throws IOException, JDOMException {
        final Element root = new Element("quary");
        final Document Doc = new Document(root);
        for (int i = 0; i < 5; ++i) {
            final Element elements = new Element("item");
            elements.setAttribute("id", new StringBuilder().append(i).toString());
            elements.addContent((Content)new Element("text").setText("xuehui"));
            elements.addContent((Content)new Element("url").setText("www.baidu.com"));
            root.addContent((Content)elements);
        }
        final XMLOutputter XMLOut = new XMLOutputter();
        String file = this.getClass().getClassLoader().getResource("").getPath();
        file = file.replaceAll("%20", " ");
        file = String.valueOf(file.substring(1, file.indexOf("classes"))) + "source/";
        XMLOut.output(Doc, (OutputStream)new FileOutputStream(String.valueOf(file) + "notice.xml"));
    }
    
    public static void downloadWeburl(final List<WeburlVo> weburls, final String mac) throws FileNotFoundException, IOException {
        final Element root = new Element("quary");
        final Document Doc = new Document(root);
        for (final WeburlVo vo : weburls) {
            final Element elements = new Element("item");
            elements.addContent((Content)new Element("text").setText(vo.getName()));
            elements.addContent((Content)new Element("url").setText(vo.getUrl()));
            root.addContent((Content)elements);
        }
        final XMLOutputter XMLOut = new XMLOutputter();
        String file = JavaXML.class.getClassLoader().getResource("").getPath();
        file = file.replaceAll("%20", " ");
        file = String.valueOf(file.substring(1, file.indexOf("classes"))) + "source/";
        XMLOut.output(Doc, (OutputStream)new FileOutputStream(String.valueOf(file) + mac + ".xml"));
    }
    
    public static void downloadEmployeeInfoSet(final Map<String, String> employInfoSet) throws FileNotFoundException, IOException {
        final Element root = new Element("employInfoSet");
        final Document Doc = new Document(root);
        if (employInfoSet != null) {
            final Element employeeName = new Element("employeeName").setText(employInfoSet.get("employeeName").toString());
            final Element job_desc = new Element("job_desc").setText(employInfoSet.get("job_desc").toString());
            final Element sex = new Element("employeeJobNum").setText(employInfoSet.get("employeeJobNum").toString());
            final Element star = new Element("star").setText(employInfoSet.get("star").toString());
            final Element employeeCardNum = new Element("employeeCardNum").setText(employInfoSet.get("employeeCardNum").toString());
            final Element contact = new Element("phone").setText(employInfoSet.get("phone").toString());
            final Element windowName = new Element("windowName").setText(employInfoSet.get("windowName").toString());
            final Element deptname = new Element("deptname").setText(employInfoSet.get("deptname").toString());
            final Element unitName = new Element("unitName").setText(employInfoSet.get("unitName").toString());
            root.addContent((Content)employeeName);
            root.addContent((Content)job_desc);
            root.addContent((Content)sex);
            root.addContent((Content)star);
            root.addContent((Content)employeeCardNum);
            root.addContent((Content)contact);
            root.addContent((Content)windowName);
            root.addContent((Content)deptname);
            root.addContent((Content)unitName);
        }
        final XMLOutputter XMLOut = new XMLOutputter();
        String file = JavaXML.class.getClassLoader().getResource("").getPath();
        file = file.replaceAll("%20", " ");
        file = String.valueOf(file.substring(1, file.indexOf("classes"))) + "source/";
        XMLOut.output(Doc, (OutputStream)new FileOutputStream(String.valueOf(file) + "employeeInfoSet.xml"));
    }
    
    public static void downloadNotice(final List<NoticeVo> notices) throws FileNotFoundException, IOException {
        final Element root = new Element("notice");
        final Document Doc = new Document(root);
        for (final NoticeVo vo : notices) {
            final Element elements = new Element("item");
            elements.addContent((Content)new Element("id").setText(new StringBuilder(String.valueOf(vo.getId())).toString()));
            elements.addContent((Content)new Element("title").setText(vo.getTitle()));
            elements.addContent((Content)new Element("content").setText(vo.getContent()));
            System.out.println(vo.getDate());
            elements.addContent((Content)new Element("date").setText(vo.getDate()));
            root.addContent((Content)elements);
        }
        final XMLOutputter XMLOut = new XMLOutputter();
        String file = JavaXML.class.getClassLoader().getResource("").getPath();
        file = file.replaceAll("%20", " ");
        file = String.valueOf(file.substring(1, file.indexOf("classes"))) + "source/";
        XMLOut.output(Doc, (OutputStream)new FileOutputStream(String.valueOf(file) + "notice.xml"));
    }
    
    public static void main(final String[] args) {
    }
}
