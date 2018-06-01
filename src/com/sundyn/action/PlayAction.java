package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import java.net.*;
import org.jdom.*;
import org.jdom.output.*;
import com.sundyn.vo.*;
import javax.servlet.http.*;
import org.jdom.input.*;
import java.util.*;
import java.io.*;
import com.sundyn.util.*;

public class PlayAction extends ActionSupport
{
    private static final String playSource = "playSource";
    private File img;
    private Pager pager;
    private PlayService playService;
    private CompressPicDemo cPic;
    
    public CompressPicDemo getcPic() {
        return this.cPic;
    }
    
    public void setcPic(final CompressPicDemo cPic) {
        this.cPic = cPic;
    }
    
    private String getExtFileName(final String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    public File getImg() {
        return this.img;
    }
    
    public Pager getPager() {
        return this.pager;
    }
    
    public PlayService getPlayService() {
        return this.playService;
    }
    
    public String playAdd() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String playTimes = request.getParameter("playTimes");
        final String playType = request.getParameter("playType");
        String playSource = request.getParameter("playSource");
        if (playType.equals("text")) {
            final String playTitle = request.getParameter("playTitle");
            String playContent = request.getParameter("playContent");
            final String ip = InetAddress.getLocalHost().getHostAddress();
            int i_index2;
            for (int i_index = 0; (i_index = playContent.indexOf("src", i_index)) > -1; playContent = String.valueOf(playContent.substring(0, i_index2 + 1)) + "http://" + ip + playContent.substring(i_index2 + 1), i_index = i_index2) {
                i_index2 = playContent.indexOf("\"", i_index);
            }
            final String basepath = ServletActionContext.getServletContext().getRealPath("/");
            playSource = String.valueOf(System.currentTimeMillis()) + ".xml";
            final Element root = new Element("list");
            final Document doc = new Document(root);
            final Element title = new Element("title");
            final Element text = new Element("text");
            title.setText(playTitle);
            text.setText(playContent);
            root.addContent((Content)title);
            root.addContent((Content)text);
            final Format format = Format.getPrettyFormat();
            format.setIndent("    ");
            format.setEncoding("gb2312");
            XMLOutputter XMLOut = new XMLOutputter(format);
            XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(basepath) + "playSource" + File.separator + playSource));
            XMLOut = null;
        }
        if (playTimes == null || playTimes.equals("")) {
            playTimes = "1000";
        }
        final int i_times = Integer.parseInt(playTimes);
        final PlayVo playVo = new PlayVo();
        playVo.setPlayName(request.getParameter("playName"));
        playVo.setPlayType(playType);
        playVo.setPlaySource(playSource);
        playVo.setPlayIndex(request.getParameter("playIndex"));
        playVo.setPlayTimes(new StringBuilder(String.valueOf(i_times)).toString());
        this.playService.playAdd(playVo);
        return "success";
    }
    
    public String playAddDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        return "success";
    }
    
    public String playDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("playId"));
        this.playService.playDel(id);
        return "success";
    }
    
    public String playEdit() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String playTimes = request.getParameter("playTimes");
        final String playType = request.getParameter("playType");
        final String playSource = request.getParameter("playSource");
        if (playType.equals("text")) {
            final String playTitle = request.getParameter("playTitle");
            String playContent = request.getParameter("playContent");
            final String ip = InetAddress.getLocalHost().getHostAddress();
            int i_index = 0;
            while ((i_index = playContent.indexOf("src", i_index)) > -1) {
                final int i_index2 = playContent.indexOf("\"", i_index);
                final String temp = playContent.substring(i_index2 + 1).trim().substring(0, 4).toLowerCase();
                if (!playContent.substring(i_index2 + 1).trim().substring(0, 4).toLowerCase().equals("http")) {
                    playContent = String.valueOf(playContent.substring(0, i_index2 + 1)) + "http://" + ip + playContent.substring(i_index2 + 1);
                    i_index = i_index2;
                }
                else {
                    i_index += 4;
                }
            }
            final String basepath = ServletActionContext.getServletContext().getRealPath("/");
            final Element root = new Element("list");
            final Document doc = new Document(root);
            final Element title = new Element("title");
            final Element text = new Element("text");
            title.setText(playTitle);
            text.setText(playContent);
            root.addContent((Content)title);
            root.addContent((Content)text);
            final Format format = Format.getPrettyFormat();
            format.setIndent("    ");
            format.setEncoding("gb2312");
            XMLOutputter XMLOut = new XMLOutputter(format);
            XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(basepath) + "playSource" + File.separator + playSource));
            XMLOut = null;
        }
        if (playTimes == null || playTimes.equals("")) {
            playTimes = "1000";
        }
        final int i_times = Integer.parseInt(playTimes);
        final PlayVo playVo = new PlayVo();
        playVo.setPlayId(Integer.parseInt(request.getParameter("playId")));
        playVo.setPlayName(request.getParameter("playName"));
        playVo.setPlayType(playType);
        playVo.setPlaySource(playSource);
        playVo.setPlayIndex(request.getParameter("playIndex"));
        playVo.setPlayTimes(new StringBuilder(String.valueOf(i_times)).toString());
        this.playService.playEdit(playVo);
        return "success";
    }
    
    public String playEditDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer playId = Integer.valueOf(request.getParameter("playId"));
        final Map p = this.playService.findById(playId);
        if (p != null && p.get("playType").toString().equals("text")) {
            final String basepath = ServletActionContext.getServletContext().getRealPath("/");
            final SAXBuilder sb = new SAXBuilder();
            final Document doc = sb.build(String.valueOf(basepath) + "playSource" + File.separator + p.get("playSource").toString());
            final Element root = doc.getRootElement();
            p.put("playTitle", root.getChild("title").getText());
            p.put("playContent", root.getChild("text").getText());
        }
        request.setAttribute("p", (Object)p);
        return "success";
    }
    
    public String playQuery() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int rowsCount = this.playService.countPlayQuery("");
        this.pager = new Pager("currentPage", 6, rowsCount, request, "playPage");
        final List list = this.playService.playQuery(request.getParameter("keyword"), (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(list);
        return "success";
    }
    
    public String playQueryAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        keyword = keyword.trim();
        keyword = Mysql.mysql(keyword);
        final int rowsCount = this.playService.countPlayQuery(keyword);
        this.pager = new Pager("currentPage", 6, rowsCount, request, "playPage");
        final List list = this.playService.playQuery(keyword, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(list);
        request.setAttribute("keyword", (Object)keyword);
        return "success";
    }
    
    public String playUpload() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String imgName = request.getParameter("imgName");
        String impPath = "";
        if (this.img != null) {
            String dstPath = ServletActionContext.getServletContext().getRealPath("/");
            dstPath = dstPath.replaceAll("%20", " ");
            final long curTime = System.currentTimeMillis();
            final String path = String.valueOf(dstPath) + "/" + "playSource" + File.separator + curTime + File.separator;
            if (imgName.toLowerCase().endsWith("doc")) {
                if (!new File(path).exists()) {
                    new File(path).mkdirs();
                }
                WordToHtmlUtil.doc(new FileInputStream(this.img), path, "index.html");
                impPath = new StringBuilder().append(curTime).toString();
            }
            else if (imgName.toLowerCase().endsWith("docx")) {
                if (!new File(path).exists()) {
                    new File(path).mkdirs();
                }
                WordToHtmlUtil.docx(new FileInputStream(this.img), path, "index.html");
                impPath = new StringBuilder().append(curTime).toString();
            }
            else {
                final String filename = String.valueOf(curTime) + Math.round(Math.random() * 100.0) + this.getExtFileName(imgName);
                final File dst = new File(String.valueOf(dstPath) + "playSource" + File.separator + filename);
                MyFile.copy(this.img, dst);
                impPath = filename;
            }
        }
        else {
            impPath = "";
        }
        request.setAttribute("imgPath", (Object)impPath);
        return "success";
    }
    
    public void setImg(final File img) {
        this.img = img;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public void setPlayService(final PlayService playService) {
        this.playService = playService;
    }
}
