package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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

public class PlayAction extends MainAction
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    private static final String playSource = "playSource";
    private File img;
    private Pager pager;
    private PlayService playService;
    private CompressPicDemo cPic;
    private String msg;

    public String getMsg() { return this.msg; }
    public void setMsg(final String msg) {
        this.msg = msg;
    }
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
        playSource = playSource.replace("playSource/","");
        if (playType.equals("text")) {
            final String playTitle = request.getParameter("playTitle");
            String playContent = request.getParameter("playContent");
            if (playTitle.equals("")){
                this.msg = "资源标题不能为空!";
                return "msg";
            }
            if (playContent.equals("")){
                this.msg = "资源内容不能为空!";
                return "msg";
            }

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
            logger.info("add生成资源路径：" + String.valueOf(basepath) + "playSource" + File.separator + playSource);
            XMLOut = null;
        }
        if (playTimes == null || playTimes.equals("")) {
            playTimes = "1000";
        }
        final int i_times = Integer.parseInt(playTimes);
        final PlayVo playVo = new PlayVo();
        String playName = request.getParameter("playName");
        if (playName.equals("")){
            this.msg = "播放名不能为空!";
            return "msg";
        }
        if (playService.existsByName(null, playName)){
            this.msg = "添加失败:已经存在相同的名称";
            return "msg";
        }

        playVo.setPlayName(playName);
        playVo.setPlayType(playType);
        playVo.setPlaySource(playSource);
        playVo.setPlayIndex(request.getParameter("playIndex"));
        playVo.setPlayTimes(new StringBuilder(String.valueOf(i_times)).toString());
        playVo.setOrgname(request.getParameter("orgname"));
        this.playService.playAdd(playVo);
        return "msg";
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
        String playSource = request.getParameter("playSource");
        playSource = playSource.replace("playSource/","");
        if (playType.equals("text")) {
            final String playTitle = request.getParameter("playTitle");
            String playContent = request.getParameter("playContent");
            if (playTitle.equals("")){
                this.msg = "资源标题不能为空!";
                return "msg";
            }
            if (playContent.equals("")){
                this.msg = "资源内容不能为空!";
                return "msg";
            }
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
            File f = new File(String.valueOf(basepath) + "playSource" + File.separator + playSource);
            if (!f.exists() || f.isDirectory()){
                playSource = String.valueOf(System.currentTimeMillis()) + ".xml";
            }
            XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(basepath) + "playSource" + File.separator + playSource));

            XMLOut = null;
        }
        if (playTimes == null || playTimes.equals("")) {
            playTimes = "1000";
        }
        final int i_times = Integer.parseInt(playTimes);
        final PlayVo playVo = new PlayVo();
        String playId = request.getParameter("playId");
        String playName = request.getParameter("playName");
        if (playName.equals("")){
            this.msg = "播放名不能为空!";
            return "msg";
        }
        if (playService.existsByName(playId, playName)){
            this.msg = "添加失败:已经存在相同的名称";
            return "msg";
        }
        playVo.setPlayId(Integer.parseInt(playId));
        playVo.setPlayName(playName);
        playVo.setPlayType(playType);
        playVo.setPlaySource(playSource);
        playVo.setPlayIndex(request.getParameter("playIndex"));
        playVo.setPlayTimes(new StringBuilder(String.valueOf(i_times)).toString());
        playVo.setOrgname(request.getParameter("orgname"));
        this.playService.playEdit(playVo);
        return "msg";
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
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "playPage");
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
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "playPage");
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
            final String path = String.valueOf(dstPath) + "/playSource" + File.separator + curTime + File.separator;
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
            JSONObject jo = uploadFile(null,"playSource");
            if (jo.get("rst").equals("success")){
                String dstPath = ServletActionContext.getServletContext().getRealPath("/");
                dstPath = dstPath.replaceAll("%20", " ");
                final long curTime = System.currentTimeMillis();
                final String path = String.valueOf(dstPath) + "/playSource" + File.separator + curTime + File.separator;
                String _docfile = ((JSONArray)(jo.get("path"))).get(0).toString();
                if (_docfile.toLowerCase().endsWith("doc")) {
                    if (!new File(path).exists()) {
                        new File(path).mkdirs();
                    }
                    WordToHtmlUtil.doc(new FileInputStream(dstPath + _docfile), path, "index.html");
                    impPath = new StringBuilder().append(curTime).toString();
                    jo.put("path", new String[]{impPath});
                }
                else if (_docfile.toLowerCase().endsWith("docx")) {
                    if (!new File(path).exists()) {
                        new File(path).mkdirs();
                    }
                    logger.info("保存路径: " + path);
                    WordToHtmlUtil.docx(new FileInputStream(dstPath + _docfile), path, "index.html");
                    impPath = new StringBuilder().append(curTime).toString();
                    jo.put("path", new String[]{impPath});
                }
            }

            request.setAttribute("msg", jo.toString());
            return "uploadsucc";
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
