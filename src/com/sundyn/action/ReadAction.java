package com.sundyn.action;

import com.opensymphony.xwork2.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.*;
import net.sf.json.*;

public class ReadAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    String serverURL;
    String tempContent;
    String doc_id;
    String itemId;
    Integer delay;
    
    public String getServerURL() {
        return this.serverURL;
    }
    
    public void setServerURL(final String serverURL) {
        this.serverURL = serverURL;
    }
    
    public String getTempContent() {
        return this.tempContent;
    }
    
    public void setTempContent(final String tempContent) {
        this.tempContent = tempContent;
    }
    
    public String getDoc_id() {
        return this.doc_id;
    }
    
    public void setDoc_id(final String doc_id) {
        this.doc_id = doc_id;
    }
    
    public String getItemId() {
        return this.itemId;
    }
    
    public void setItemId(final String itemId) {
        this.itemId = itemId;
    }
    
    public Integer getDelay() {
        return this.delay;
    }
    
    public void setDelay(final Integer delay) {
        this.delay = delay;
    }
    
    public String readHtmlAjax() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        BufferedReader reader = null;
        try {
            final String url = String.valueOf(this.serverURL) + "/service/hn_viewDoc.jsp?doc_id=" + request.getParameter("doc_id") + "&class_sid=IBoDm";
            System.out.println(url);
            this.tempContent = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
            this.tempContent = String.valueOf(this.tempContent) + "<HTML>";
            this.tempContent = String.valueOf(this.tempContent) + " <BODY><div id=myDIV>";
            final String p = "exeHTML.exe " + url + " " + 5 + " tempHTML.html";
            final Process proc = Runtime.getRuntime().exec(p);
            if (proc.waitFor() == 0) {
                final File file = new File(String.valueOf(System.getProperty("user.dir")) + "\\tempHTML.html");
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                int start = 0;
                int end = 0;
                final String[] tempContent = new String[350];
                while ((tempString = reader.readLine()) != null) {
                    tempContent[line] = tempString;
                    if ("<DIV class=\"box box_3\" id=viewDoc>".equals(tempString)) {
                        start = line;
                    }
                    if ("<DIV id=optionButton style=\"DISPLAY: none\"></DIV></DIV></DIV></DIV></DIV>".equals(tempString)) {
                        end = line;
                    }
                    ++line;
                }
                this.tempContent = "";
                boolean flage = true;
                for (int c = start + 1; c < end; ++c) {
                    flage = !flage;
                    this.tempContent = String.valueOf(this.tempContent) + tempContent[c];
                }
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException ex) {}
            }
        }
        if (reader != null) {
            try {
                reader.close();
            }
            catch (IOException ex2) {}
        }
        this.tempContent = String.valueOf(this.tempContent) + "</div></BODY>";
        this.tempContent = String.valueOf(this.tempContent) + "</HTML>";
        System.out.println(this.tempContent);
        return "success";
    }
    
    public String readListAjax() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        BufferedReader reader = null;
        final String item = request.getParameter("itemId");
        final String url = String.valueOf(this.serverURL) + "/4447_viewList.html?class_sid=IBoDm&item_id=" + item + "&comp=0&xsl_id=47973";
        System.out.println("\u8bbf\u95eeurl\uff1a" + url);
        this.tempContent = "";
        final String p = "exeHTML.exe " + url + " " + 5 + " TempList.html";
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(p);
        }
        catch (IOException e) {
            System.out.println("\u5728\u6267\u884c\u8bfb\u53d6\u7f51\u7ad9\u9875\u9762\u65f6\u53d1\u751f\u5f02\u5e38");
            e.printStackTrace();
        }
        System.out.println("\u751f\u6210\u6587\u4ef6\u5730\u5740\uff1a" + System.getProperty("user.dir") + "\\TempList.html");
        try {
            if (proc.waitFor() == 0) {
                final File file = new File(String.valueOf(System.getProperty("user.dir")) + "\\TempList.html");
                try {
                    reader = new BufferedReader(new FileReader(file));
                }
                catch (FileNotFoundException e2) {
                    System.out.println("\u8bfb\u53d6\u751f\u6210\u7684\u4e34\u65f6\u5217\u8868\u6587\u4ef6\u51fa\u9519");
                    e2.printStackTrace();
                }
                String tempString = null;
                final String[] tempContent = new String[300];
                int line = 0;
                int start = 0;
                int end = 0;
                try {
                    while ((tempString = reader.readLine()) != null) {
                        tempContent[line] = tempString;
                        if ("<DIV id=view_list>".equals(tempString)) {
                            start = line;
                        }
                        if ("<DIV id=table_display style=\"PADDING-RIGHT: 10px; DISPLAY: block; BACKGROUND: #f4f4f4; TEXT-ALIGN: right\">".equals(tempString)) {
                            end = line;
                        }
                        ++line;
                    }
                }
                catch (IOException e3) {
                    System.out.println("\u5728\u8bfb\u53d6\u4e34\u65f6\u6587\u4ef6\u65f6\u53d1\u751f\u5f02\u5e38");
                    e3.printStackTrace();
                }
                boolean flage = true;
                for (int c = start + 2; c < end; ++c) {
                    if (flage) {
                        tempContent[c] = "<ul>";
                    }
                    else {
                        tempContent[c] = tempContent[c].replace("<LI>¡¤", "<LI>").replace("<FONT color=#cc6600>[\u6709\u6548]</FONT>", "").replace("http://www.12366.ha.cn/websites/service/viewDoc.jsp", "readDetails.action").replace("target=_blank", "");
                    }
                    flage = !flage;
                    this.tempContent = String.valueOf(this.tempContent) + tempContent[c];
                }
                try {
                    reader.close();
                }
                catch (IOException e4) {
                    System.out.println("\u5173\u95ed\u8f93\u5165\u6d41\u65f6\u51fa\u73b0\u5f02\u5e38");
                    e4.printStackTrace();
                }
            }
        }
        catch (InterruptedException e5) {
            System.out.println("\u5728\u6267\u884cexe\u6587\u4ef6\u65f6\u51fa\u73b0\u5f02\u5e38");
            e5.printStackTrace();
        }
        System.out.println(this.tempContent);
        return "success";
    }
    
    public String readListAjaxJson() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        BufferedReader reader = null;
        final String item = request.getParameter("itemId");
        final String url = String.valueOf(this.serverURL) + "/4447_viewList.html?class_sid=IBoDm&item_id=" + item + "&comp=0&xsl_id=47973";
        System.out.println("\u8bbf\u95eeurl\uff1a" + url);
        this.tempContent = "";
        final String p = String.valueOf(path) + "exeHTML.exe " + url + " " + this.delay + "    " + path + "TempList.html";
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(p);
        }
        catch (IOException e) {
            System.out.println("\u5728\u6267\u884c\u8bfb\u53d6\u7f51\u7ad9\u9875\u9762\u65f6\u53d1\u751f\u5f02\u5e38");
            e.printStackTrace();
        }
        System.out.println("\u751f\u6210\u6587\u4ef6\u5730\u5740\uff1a" + path + "TempList.html");
        try {
            if (proc.waitFor() == 0) {
                final File file = new File(String.valueOf(path) + "TempList.html");
                try {
                    reader = new BufferedReader(new FileReader(file));
                }
                catch (FileNotFoundException e2) {
                    System.out.println("\u8bfb\u53d6\u751f\u6210\u7684\u4e34\u65f6\u5217\u8868\u6587\u4ef6\u51fa\u9519");
                    e2.printStackTrace();
                }
                String tempString = null;
                final String[] tempContent = new String[300];
                int line = 0;
                int start = 0;
                int end = 0;
                try {
                    while ((tempString = reader.readLine()) != null) {
                        tempContent[line] = tempString;
                        if ("<DIV id=view_list>".equals(tempString)) {
                            start = line;
                        }
                        if ("<DIV id=table_display style=\"PADDING-RIGHT: 10px; DISPLAY: block; BACKGROUND: #f4f4f4; TEXT-ALIGN: right\">".equals(tempString)) {
                            end = line;
                        }
                        ++line;
                    }
                }
                catch (IOException e3) {
                    System.out.println("\u5728\u8bfb\u53d6\u4e34\u65f6\u6587\u4ef6\u65f6\u53d1\u751f\u5f02\u5e38");
                    e3.printStackTrace();
                }
                boolean flage = true;
                for (int c = start + 2; c < end; ++c) {
                    if (flage) {
                        tempContent[c] = "<ul>";
                    }
                    else {
                        tempContent[c] = tempContent[c].replace("<LI>¡¤", "<LI>").replace("<FONT color=#cc6600>[\u6709\u6548]</FONT>", "").replace("http://www.12366.ha.cn/websites/service/viewDoc.jsp", "readHtmlAjaxJson.action").replace("target=_blank", "");
                    }
                    flage = !flage;
                    this.tempContent = String.valueOf(this.tempContent) + tempContent[c];
                }
                try {
                    reader.close();
                }
                catch (IOException e4) {
                    System.out.println("\u5173\u95ed\u8f93\u5165\u6d41\u65f6\u51fa\u73b0\u5f02\u5e38");
                    e4.printStackTrace();
                }
            }
        }
        catch (InterruptedException e5) {
            System.out.println("\u5728\u6267\u884cexe\u6587\u4ef6\u65f6\u51fa\u73b0\u5f02\u5e38");
            e5.printStackTrace();
        }
        System.out.println(this.tempContent);
        final Document doc = Jsoup.parse(this.tempContent);
        final Elements links = doc.getElementsByTag("a");
        final List ls = new ArrayList();
        for (final Element link : links) {
            final String linkHref = link.attr("href");
            final String linkText = link.text();
            linkText.replace(" ", "");
            linkText.replace(String.valueOf('\r'), "");
            linkText.replace(String.valueOf('\n'), "");
            final Map m = new HashMap();
            m.put("text", linkText);
            m.put("url", linkHref);
            ls.add(m);
        }
        final JSONArray json = JSONArray.fromObject((Object)ls);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String readHtmlAjaxJson() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        BufferedReader reader = null;
        try {
            final String url = String.valueOf(this.serverURL) + "/service/hn_viewDoc.jsp?doc_id=" + request.getParameter("doc_id") + "&class_sid=IBoDm";
            System.out.println(url);
            this.tempContent = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
            this.tempContent = String.valueOf(this.tempContent) + "<HTML>";
            this.tempContent = String.valueOf(this.tempContent) + " <BODY><div id=myDIV>";
            final String p = String.valueOf(path) + "exeHTML.exe " + url + " " + this.delay + " " + path + "tempHTML.html";
            final Process proc = Runtime.getRuntime().exec(p);
            if (proc.waitFor() == 0) {
                final File file = new File(String.valueOf(path) + "tempHTML.html");
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                int start = 0;
                int end = 0;
                final String[] tempContent = new String[350];
                while ((tempString = reader.readLine()) != null) {
                    tempContent[line] = tempString;
                    if ("<DIV class=\"box box_3\" id=viewDoc>".equals(tempString)) {
                        start = line;
                    }
                    if ("<DIV id=optionButton style=\"DISPLAY: none\"></DIV></DIV></DIV></DIV></DIV>".equals(tempString)) {
                        end = line;
                    }
                    ++line;
                }
                this.tempContent = "";
                boolean flage = true;
                for (int c = start + 1; c < end; ++c) {
                    flage = !flage;
                    this.tempContent = String.valueOf(this.tempContent) + tempContent[c];
                }
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException ex) {}
            }
        }
        if (reader != null) {
            try {
                reader.close();
            }
            catch (IOException ex2) {}
        }
        System.out.println(this.tempContent);
        final Document doc = Jsoup.parse(this.tempContent);
        final Map m = new HashMap();
        try {
            final Element td = doc.select("td[htjs_title=\u6807\u9898]").first();
            m.put("title", td.text());
        }
        catch (Exception e2) {
            m.put("title", "");
        }
        try {
            final Element td = doc.select("td[htjs_title=\u6587\u53f7]").first();
            m.put("fileNum", td.text());
        }
        catch (Exception e2) {
            m.put("fileNum", "");
        }
        try {
            final Element td = doc.select("td[htjs_title=\u5355\u4f4d]").first();
            m.put("unit", td.text());
        }
        catch (Exception e2) {
            m.put("unit", "");
        }
        try {
            final Element td = doc.select("td[htjs_title=\u65f6\u95f4]").first();
            m.put("time", td.text());
        }
        catch (Exception e2) {
            m.put("time", "");
        }
        try {
            final Element td = doc.select("td[htjs_title=\u6b63\u6587\u5185\u5bb9]").first();
            String content = td.child(0).html();
            if (content.equals("")) {
                throw new Exception("\u5f97\u5230\u4e0d\u7f51\u9875\u5185\u5bb9");
            }
            content = content.replace("&nbsp;", " ");
            content = content.replace("<br />", "\r");
            content = content.split("\u9644\u4ef6")[0];
            m.put("content", content);
        }
        catch (Exception e2) {
            final Element td = doc.select("td[htjs_title=\u6b63\u6587\u5185\u5bb9]").first();
            String content2 = td.html();
            content2 = content2.replace("&nbsp;", " ");
            content2 = content2.replace("<br />", "\r");
            content2 = content2.split("\u9644\u4ef6")[0];
            m.put("content", content2);
        }
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String readList() {
        return "success";
    }
    
    public String readDetails() {
        return "success";
    }
}
