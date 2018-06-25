package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.sql.*;
import com.sundyn.vo.*;
import org.jdom.input.*;
import org.jdom.output.*;
import java.io.*;
import org.jdom.*;
import com.sundyn.util.*;
import java.util.*;
import com.sundyn.util.impl.*;

public class PlayListAction extends MainAction
{
    private PlayListService playListService;
    private PlayService playService;
    private DeptService deptService;
    private PowerService powerService;
    private Pager pager;
    private File img;
    private int playListId;
    private String msg;
    
    public PlayListService getPlayListService() {
        return this.playListService;
    }
    
    public void setPlayListService(final PlayListService playListService) {
        this.playListService = playListService;
    }
    
    public PowerService getPowerService() {
        return this.powerService;
    }
    
    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
    
    public Pager getPager() {
        return this.pager;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public PlayService getPlayService() {
        return this.playService;
    }
    
    public void setPlayService(final PlayService playService) {
        this.playService = playService;
    }
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public File getImg() {
        return this.img;
    }
    
    public void setImg(final File img) {
        this.img = img;
    }
    
    public int getPlayListId() {
        return this.playListId;
    }
    
    public void setPlayListId(final int playListId) {
        this.playListId = playListId;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public String playListQuery() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        if (manager.get("id").toString().equals("1")) {
            final int rowsCount = this.playListService.countPlayListQuery("", null);
            this.pager = new Pager("currentPage", pageSize, rowsCount, request, "playListPage");
            final List list = this.playListService.playListQuery(request.getParameter("keyword"), null, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
            this.pager.setPageList(list);
        }
        else {
            final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
            final Map power = this.powerService.getUserGroup(groupid);
            final String deptIdGroup = power.get("deptIdGroup").toString();
            final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);
            final int rowsCount2 = this.playListService.countPlayListQuery("", deptIds);
            this.pager = new Pager("currentPage", pageSize, rowsCount2, request, "playListPage");
            final List list2 = this.playListService.playListQuery(request.getParameter("keyword"), deptIds, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
            this.pager.setPageList(list2);
        }
        return "success";
    }
    
    public String playListQueryAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        keyword = keyword.trim();
        keyword = Mysql.mysql(keyword);
        if (manager.get("id").toString().equals("1")) {
            final int rowsCount = this.playListService.countPlayListQuery(keyword, null);
            this.pager = new Pager("currentPage", pageSize, rowsCount, request, "playListPage");
            final List list = this.playListService.playListQuery(keyword, null, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
            this.pager.setPageList(list);
        }
        else {
            final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
            final Map power = this.powerService.getUserGroup(groupid);
            final String deptIdGroup = power.get("deptIdGroup").toString();
            final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);
            final int rowsCount2 = this.playListService.countPlayListQuery(keyword, deptIds);
            this.pager = new Pager("currentPage", pageSize, rowsCount2, request, "playListPage");
            final List list2 = this.playListService.playListQuery(keyword, deptIds, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
            this.pager.setPageList(list2);
        }
        request.setAttribute("keyword", (Object)keyword);
        return "success";
    }
    
    public String playListQueryAjaxAndroid() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        keyword = keyword.trim();
        keyword = Mysql.mysql(keyword);
        if (manager.get("id").toString().equals("1")) {
            final int rowsCount = this.playListService.countPlayListQuery(keyword, null);
            this.pager = new Pager("currentPage", pageSize, rowsCount, request, "playListPage");
            final List list = this.playListService.playListQuery(keyword, null, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
            this.pager.setPageList(list);
        }
        else {
            final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
            final Map power = this.powerService.getUserGroup(groupid);
            final String deptIdGroup = power.get("deptIdGroup").toString();
            final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);
            final int rowsCount2 = this.playListService.countPlayListQuery(keyword, deptIds);
            this.pager = new Pager("currentPage", pageSize, rowsCount2, request, "playListPage");
            final List list2 = this.playListService.playListQuery(keyword, deptIds, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
            this.pager.setPageList(list2);
        }
        request.setAttribute("keyword", (Object)keyword);
        return "success";
    }
    
    public String playListDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String basepath = ServletActionContext.getServletContext().getRealPath("/");
        final Integer id = Integer.valueOf(request.getParameter("playListId"));
        final int num = this.deptService.countByPlayListId(new StringBuilder().append(id).toString());
        if (num > 0) {
            this.msg = this.getText("sundyn.useNotDel");
        }
        else {
            this.playListService.playListDel(id);
            MyFile.delete(String.valueOf(basepath) + "m7app" + File.separator + id);
            MyFile.delete(String.valueOf(basepath) + "update" + File.separator + id);
            this.msg = this.getText("sundyn.deleteSuccess");
        }
        return "success";
    }
    
    public String playListAddDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final List ls = this.playService.playQuery("", null, null);
        request.setAttribute("ls", (Object)ls);
        return "success";
    }
    
    public String playListAddDialogAndroid() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final List ls = this.playService.playQuery("", null, null);
        request.setAttribute("ls", (Object)ls);
        return "success";
    }
    
    public String playListAdd() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final PlayListVo playListVo = new PlayListVo();
        playListVo.setPlayListName(request.getParameter("playListName"));
        playListVo.setPlayListDescription(request.getParameter("playListDescription"));
        playListVo.setPlayIds(request.getParameter("playIds"));
        this.playListService.playListAdd(playListVo);
        try {
            final int id = this.playListService.getLastPlayListId();
            final String basePath = ServletActionContext.getServletContext().getRealPath("/");
            final String m7path = String.valueOf(basePath) + "m7app" + File.separator + id;
            final String m7binpath = String.valueOf(basePath) + "update" + File.separator + id;
            System.out.println("playListAdd-m7path=" + m7path);
            System.out.println("playListAdd-m7binpath=" + m7binpath);
            final String oldPath = String.valueOf(basePath) + "UpdateFile";
            MyFile.copyDirectory(oldPath, m7path);
            final File m7bin = new File(m7binpath);
            if (!m7bin.exists()) {
                m7bin.mkdirs();
            }
            MyFile.copy(String.valueOf(m7path) + File.separator + "NEWCONFIG.XML", String.valueOf(m7binpath) + File.separator + "NEWCONFIG.XML");
            MyFile.copy(String.valueOf(m7path) + File.separator + "CONFIG.XML", String.valueOf(m7binpath) + File.separator + "CONFIG.XML");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public String playListEditDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer playListId = Integer.valueOf(request.getParameter("playListId"));
        final Map p = this.playListService.findById(playListId);
        final Object o = p.get("playIds");
        String ids = " ";
        if (o != null) {
            ids = o.toString();
        }
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        final List pls = this.playService.findByIds(ids);
        final List als = this.playService.playQuery("", null, null);
        if (pls != null) {
            for (int i = 0; i < pls.size(); ++i) {
                als.remove(pls.get(i));
            }
        }
        request.setAttribute("pls", (Object)pls);
        request.setAttribute("als", (Object)als);
        request.setAttribute("p", (Object)p);
        request.setAttribute("ids", (Object)ids);
        return "success";
    }
    
    public String playListEditDialogAndroid() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer playListId = Integer.valueOf(request.getParameter("playListId"));
        final String basePath = ServletActionContext.getServletContext().getRealPath(File.separator);
        final String filePath = String.valueOf(basePath) + "m7app" + File.separator + playListId.toString() + File.separator + "NEWCONFIG.XML";
        SAXBuilder sb = new SAXBuilder(false);
        File docFile = new File(filePath);
        if (!docFile.exists()){
            this.msg = "文件丢失,请删除后重新添加!";
            return "input";
        }
        Document doc = sb.build(docFile);
        final Element root = doc.getRootElement();
        final String[] Shutdown = root.getChild("Software").getChildText("Shutdown").toString().split(":");
        final String[] Boot = root.getChild("Software").getChildText("Boot").toString().split(":");
        final int Shutdownhh = Integer.valueOf(Shutdown[0]);
        final int Shutdownmm = Integer.valueOf(Shutdown[1]);
        final int Boothh = Integer.valueOf(Boot[0]);
        final int Bootmm = Integer.valueOf(Boot[1]);
        final Map<String, String> config = new HashMap<String, String>();
        config.put("Shutdownhh", new StringBuilder().append(Shutdownhh).toString());
        config.put("Shutdownmm", new StringBuilder().append(Shutdownmm).toString());
        config.put("Boothh", new StringBuilder().append(Boothh).toString());
        config.put("Bootmm", new StringBuilder().append(Bootmm).toString());
        config.put("Version", root.getChild("Software").getChildText("Version"));
        config.put("Welcometime", root.getChild("Software").getChildText("Welcometime"));
        config.put("Approvertime", root.getChild("Software").getChildText("Approvertime"));
        config.put("Shutdown", root.getChild("Software").getChildText("Shutdown"));
        config.put("Boot", root.getChild("Software").getChildText("Boot"));
        config.put("ShowEmployeePage", root.getChild("Software").getChildText("ShowEmployeePage"));
        config.put("IP", root.getChild("Server").getChildText("IP"));
        config.put("Port", root.getChild("Server").getChildText("Port"));
        config.put("Type", root.getChild("Network").getChildText("Type"));
        config.put("playListId", playListId.toString());
        doc = null;
        sb = null;
        request.setAttribute("config", (Object)config);
        final Map p = this.playListService.findById(playListId);
        final Object o = p.get("playIds");
        String ids = " ";
        if (o != null) {
            ids = o.toString();
        }
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        final List pls = this.playService.findByIds(ids); //播放列表勾选资源
        final List als = this.playService.playQuery("", null, null);
        if (pls != null) {
            for (int i = 0; i < pls.size(); ++i) {
                als.remove(pls.get(i));
            }
        }
        request.setAttribute("pls", (Object)pls);
        request.setAttribute("als", (Object)als);
        request.setAttribute("p", (Object)p);
        request.setAttribute("ids", (Object)ids);
        return "success";
    }
    
    public String playListEdit() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String basePath = ServletActionContext.getServletContext().getRealPath(File.separator);
        final PlayListVo playListVo = new PlayListVo();
        playListVo.setPlayListId(Integer.valueOf(request.getParameter("playListId")));
        playListVo.setPlayListName(request.getParameter("playListName"));
        playListVo.setPlayListDescription(request.getParameter("playListDescription"));
        playListVo.setPlayIds(request.getParameter("playIds"));
        this.playListService.playListEdit(playListVo);
        final String Shutdownhh = request.getParameter("Shutdownhh");
        final String Shutdownmm = request.getParameter("Shutdownmm");
        final String Boothh = request.getParameter("Boothh");
        final String Bootmm = request.getParameter("Bootmm");
        final String m7apppath = String.valueOf(basePath) + "m7app" + File.separator + this.playListId + File.separator + "NEWCONFIG.XML";
        final String m7binpath = String.valueOf(basePath) + "update" + File.separator + this.playListId + File.separator + "NEWCONFIG.XML";
        final String updateFilepath = String.valueOf(basePath) + "UpdateFile" + File.separator + "NEWCONFIG.XML";
        final SAXBuilder sb = new SAXBuilder();
        final Document doc = sb.build(m7apppath);
        final Element root = doc.getRootElement();
        root.getChild("Software").getChild("Version").setText(request.getParameter("Version"));
        root.getChild("Software").getChild("Welcometime").setText(request.getParameter("Welcometime"));
        root.getChild("Software").getChild("Approvertime").setText(request.getParameter("Approvertime"));
        root.getChild("Software").getChild("Shutdown").setText(String.valueOf(Shutdownhh) + ":" + Shutdownmm);
        root.getChild("Software").getChild("Boot").setText(String.valueOf(Boothh) + ":" + Bootmm);
        root.getChild("Software").getChild("ShowEmployeePage").setText(request.getParameter("ShowEmployeePage"));
        root.getChild("Server").getChild("IP").setText(request.getParameter("IP"));
        root.getChild("Server").getChild("Port").setText(request.getParameter("Port"));
        root.getChild("Network").getChild("Type").setText(request.getParameter("Type"));
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        XMLOut.output(doc, (OutputStream)new FileOutputStream(m7apppath));
        XMLOut.output(doc, (OutputStream)new FileOutputStream(m7binpath));
        XMLOut.output(doc, (OutputStream)new FileOutputStream(updateFilepath));
        XMLOut = null;
        return "success";
    }
    
    public String playListUpdateDialog() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String playListId = request.getParameter("playListId");
        final String basePath = ServletActionContext.getServletContext().getRealPath(File.separator);
        final String filePath = String.valueOf(basePath) + "m7app" + File.separator + playListId + File.separator + "NEWCONFIG.XML";
        SAXBuilder sb = new SAXBuilder(false);
        Document doc = sb.build(new File(filePath));
        final Element root = doc.getRootElement();
        System.out.println("playListUpdateDialog-filePath=" + filePath);
        final String[] Shutdown = root.getChild("Software").getChildText("Shutdown").toString().split(":");
        final String[] Boot = root.getChild("Software").getChildText("Boot").toString().split(":");
        final int Shutdownhh = Integer.valueOf(Shutdown[0]);
        final int Shutdownmm = Integer.valueOf(Shutdown[1]);
        final int Boothh = Integer.valueOf(Boot[0]);
        final int Bootmm = Integer.valueOf(Boot[1]);
        final Map<String, String> config = new HashMap<String, String>();
        config.put("Shutdownhh", new StringBuilder().append(Shutdownhh).toString());
        config.put("Shutdownmm", new StringBuilder().append(Shutdownmm).toString());
        config.put("Boothh", new StringBuilder().append(Boothh).toString());
        config.put("Bootmm", new StringBuilder().append(Bootmm).toString());
        config.put("Version", root.getChild("Software").getChildText("Version"));
        config.put("Welcometime", root.getChild("Software").getChildText("Welcometime"));
        config.put("Approvertime", root.getChild("Software").getChildText("Approvertime"));
        config.put("Shutdown", root.getChild("Software").getChildText("Shutdown"));
        config.put("Boot", root.getChild("Software").getChildText("Boot"));
        config.put("ShowEmployeePage", root.getChild("Software").getChildText("ShowEmployeePage"));
        config.put("IP", root.getChild("Server").getChildText("IP"));
        config.put("Port", root.getChild("Server").getChildText("Port"));
        config.put("Type", root.getChild("Network").getChildText("Type"));
        config.put("playListId", playListId.toString());
        doc = null;
        sb = null;
        request.setAttribute("config", (Object)config);
        return "success";
    }
    
    public String playListCreateUpdateFile() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String playIds = request.getParameter("playIds");
        final Integer playListId = Integer.valueOf(request.getParameter("playListId"));
        final String basepath = ServletActionContext.getServletContext().getRealPath("/");
        final String m7apppath = String.valueOf(basepath) + "m7app" + File.separator + playListId;
        final String m7binpath = String.valueOf(basepath) + "update" + File.separator + playListId;
        final Map p = this.playListService.findById(playListId);
        final Object o = p.get("playIds");
        System.out.println("playListCreateUpdateFile-m7apppath=" + m7apppath);
        System.out.println("playListCreateUpdateFile-binpath=" + m7binpath);
        System.out.println("playListCreateUpdateFile-p=" + p);
        String ids = "";
        if (playIds.endsWith(",")) {
            playIds = playIds.substring(0, playIds.length() - 1);
        }
        if (o != null) {
            ids = o.toString();
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
        }
        if (!playIds.equals(ids)) {
            ids = playIds;
        }
        final List pls = this.playService.findByIds(ids);
        for (final Object o2 : pls) {
            System.out.println("得到播放列表包含的文件详细信息=(1)" + o2);
        }
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        final List playlist = new ArrayList();
        if (pls != null) {
            OutputStream os = new FileOutputStream(String.valueOf(m7apppath) + File.separator + "ads.xml");
            final Element root = new Element("playlist");
            final Document doc = new Document(root);
            for (int i = 0; i < pls.size(); ++i) {
                final Element play = new Element("play");
                final Map m_play = (Map) pls.get(i);
                final Element type = new Element("type");
                type.setText(m_play.get("playType").toString());
                play.addContent((Content)type);
                final Element url = new Element("url");
                url.setText(m_play.get("playSource").toString());
                playlist.add(m_play.get("playSource").toString());
                play.addContent((Content)url);
                final Element time = new Element("time");
                time.setText(m_play.get("playTimes").toString());
                play.addContent((Content)time);
                root.addContent((Content)play);
            }
            XMLOut.output(doc, os);
            os.close();
            os = null;
        }
        else {
            OutputStream os = new FileOutputStream(String.valueOf(m7apppath) + File.separator + "ads.xml");
            final Element root = new Element("playlist");
            final Document doc = new Document(root);
            XMLOut.output(doc, os);
            os.close();
            os = null;
        }
        for (final Object o3 : playlist) {
            System.out.println("得到播放列表文件名=(2)" + o3);
        }
        final SAXBuilder sb = new SAXBuilder();
        final Document doc2 = sb.build(String.valueOf(m7apppath) + File.separator + "CONFIG.XML");
        final Element root2 = doc2.getRootElement();
        String version = root2.getChild("version").getText();
        version = new StringBuilder().append(Integer.parseInt(version) + 1).toString();
        root2.getChild("version").setText(version);
        XMLOut.output(doc2, (OutputStream)new FileOutputStream(String.valueOf(m7apppath) + File.separator + "CONFIG.XML"));
        XMLOut.output(doc2, (OutputStream)new FileOutputStream(String.valueOf(m7binpath) + File.separator + "CONFIG.XML"));
        XMLOut = null;
        final String[] file = new String[playlist.size() + 1];
        for (int j = 0; j < playlist.size(); ++j) {
            file[j] = String.valueOf(basepath) + "playSource" + File.separator + playlist.get(j).toString();
        }
        file[playlist.size()] = String.valueOf(m7apppath) + File.separator + "ads.xml";
        String[] array;
        for (int length = (array = file).length, k = 0; k < length; ++k) {
            final Object o4 = array[k];
            System.out.println("最终确定的待打bin包文件file=(3)" + o4);
        }
        final Update upadd = new Update(file);
        upadd.createUpdateFile(String.valueOf(m7binpath) + File.separator + "M7Update" + version + ".bin");
        final String tar = String.valueOf(m7binpath) + File.separator + "M7Update.bin";
        final Update up = new Update(String.valueOf(m7apppath) + File.separator);
        up.add(file);
        up.createUpdateFile(tar);
        System.out.println("bin\u5305\u751f\u6210\u5b8c\u6210(4)");
        return "success";
    }
    
    public String playListCreateUpdateZip() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String playIds = request.getParameter("playIds");
        final Integer playListId = Integer.valueOf(request.getParameter("playListId"));
        final String basepath = ServletActionContext.getServletContext().getRealPath("/");
        final String m7apppath = String.valueOf(basepath) + "m7app" + File.separator + playListId;
        final String m7binpath = String.valueOf(basepath) + "update" + File.separator + playListId;
        final String m7rarpath = String.valueOf(basepath) + "update" + File.separator + playListId;
        final String m7apptemppath = String.valueOf(basepath) + "m7app" + File.separator + "temp" + File.separator + playListId;
        final Map p = this.playListService.findById(playListId);
        final Object o = p.get("playIds");
        String ids = "";
        if (playIds.endsWith(",")) {
            playIds = playIds.substring(0, playIds.length() - 1);
        }
        if (o != null) {
            ids = o.toString();
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
        }
        if (!playIds.equals(ids)) {
            ids = playIds;
        }
        final List pls = this.playService.findByIds(ids);
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        final List playlist = new ArrayList();
        if (pls != null) {
            OutputStream os = new FileOutputStream(String.valueOf(m7apppath) + File.separator + "ads.xml");
            final Element root = new Element("playlist");
            final Document doc = new Document(root);
            for (int i = 0; i < pls.size(); ++i) {
                final Element play = new Element("play");
                final Map m_play = (Map) pls.get(i);
                final Element type = new Element("type");
                type.setText(m_play.get("playType").toString());
                play.addContent((Content)type);
                final Element url = new Element("url");
                url.setText(m_play.get("playSource").toString());
                playlist.add(m_play.get("playSource").toString());
                play.addContent((Content)url);
                final Element time = new Element("time");
                time.setText(m_play.get("playTimes").toString());
                play.addContent((Content)time);
                root.addContent((Content)play);
            }
            XMLOut.output(doc, os);
            os.close();
            os = null;
        }
        else {
            OutputStream os = new FileOutputStream(String.valueOf(m7apppath) + File.separator + "ads.xml");
            final Element root = new Element("playlist");
            final Document doc = new Document(root);
            XMLOut.output(doc, os);
            os.close();
            os = null;
        }
        final SAXBuilder sb = new SAXBuilder();
        final Document doc2 = sb.build(String.valueOf(m7apppath) + File.separator + "NEWCONFIG.XML");
        final Element root2 = doc2.getRootElement();
        String version = root2.getChild("Software").getChild("Version").getText();
        version = new StringBuilder().append(Integer.parseInt(version) + 1).toString();
        System.out.println("生成新资源版本号=" + version);
        root2.getChild("Software").getChild("Version").setText(version);
        XMLOut.output(doc2, (OutputStream)new FileOutputStream(String.valueOf(m7apppath) + File.separator + "NEWCONFIG.XML"));
        XMLOut.output(doc2, (OutputStream)new FileOutputStream(String.valueOf(m7binpath) + File.separator + "NEWCONFIG.XML"));
        XMLOut = null;
        final String[] file = new String[playlist.size() + 2];
        final String[] file2 = new String[playlist.size() + 2];
        for (int j = 0; j < playlist.size(); ++j) {
            file[j] = String.valueOf(basepath) + "playSource" + File.separator + playlist.get(j).toString();
            file2[j] = "ads/" + playlist.get(j).toString();
        }
        file[playlist.size()] = String.valueOf(m7apppath) + File.separator + "ads.xml";
        file2[playlist.size()] = "ads/ads.xml";
        String[] array;
        for (int length = (array = file).length, k = 0; k < length; ++k) {
            final String s = array[k];
            System.out.println("升级包1文件=" + s);
        }
        String[] array2;
        for (int length2 = (array2 = file2).length, l = 0; l < length2; ++l) {
            final String s = array2[l];
            System.out.println("升级包2文件=" + s);
        }
        final Update upadd = new Update();
        final String versionTar = String.valueOf(m7binpath) + File.separator + "M7Update" + version + ".zip";
        System.out.println("m7apppath=" + m7apppath);
        file[playlist.size() + 1] = String.valueOf(m7apppath) + File.separator + "NEWCONFIG.XML";
        file2[playlist.size() + 1] = "NEWCONFIG.XML";
        //final String tar = String.valueOf(m7binpath) + File.separator + "M7Update.zip";
        //System.out.println("tar=" + tar);
        upadd.makeZip(versionTar, file, file2);
        final ZipManager zipManager = new ZipManager();
        zipManager.releaseZipToFile(versionTar, String.valueOf(m7apptemppath) + File.separator);
        //zipManager.createZip(String.valueOf(m7apptemppath) + File.separator, tar);
        return "success";
    }
    
    public String playListUpdateDeal() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        if (this.img != null) {
            try {
                final String basepath = ServletActionContext.getServletContext().getRealPath("\\");
                final String m7apppath = String.valueOf(basepath) + "m7app" + File.separator + this.playListId;
                final String m7binpath = String.valueOf(basepath) + "update" + File.separator + this.playListId;
                final String m7apptemppath = String.valueOf(basepath) + "m7app" + File.separator + "temp" + File.separator + this.playListId;
                final String m7apptemppath2 = String.valueOf(basepath) + "m7app" + File.separator + "temp" + File.separator + "temp";
                MyFile.extZipFileList(this.img, m7apppath);
                final File f = new File(m7apptemppath);
                if (!f.exists()) {
                    f.mkdirs();
                }
                final File f2 = new File(m7apptemppath2);
                if (!f2.exists()) {
                    f2.mkdirs();
                }
                MyFile.extZipFileList(this.img, m7apptemppath);
                MyFile.extZipFileList(this.img, m7apptemppath2);
                final Format format = Format.getPrettyFormat();
                format.setIndent("    ");
                format.setEncoding("gb2312");
                XMLOutputter XMLOut = new XMLOutputter(format);
                final SAXBuilder sb = new SAXBuilder();
                final Document doc = sb.build(String.valueOf(m7apppath) + File.separator + "CONFIG.XML");
                final Element root = doc.getRootElement();
                String version = root.getChild("version").getText();
                version = new StringBuilder().append(Integer.parseInt(version) + 1).toString();
                root.getChild("version").setText(version);
                XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(m7apppath) + File.separator + "CONFIG.XML"));
                XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(m7binpath) + File.separator + "CONFIG.XML"));
                XMLOut = null;
                final Update upadd = new Update(String.valueOf(m7apptemppath) + File.separator);
                upadd.createUpdateFile(String.valueOf(m7binpath) + File.separator + "M7Update" + version + ".bin");
                final Update up = new Update(String.valueOf(m7apppath) + File.separator);
                final Map p = this.playListService.findById(this.playListId);
                final Object o = p.get("playIds");
                String ids = "";
                if (o != null) {
                    ids = o.toString();
                    if (ids.endsWith(",")) {
                        ids = ids.substring(0, ids.length() - 1);
                    }
                }
                final List pls = this.playService.findByIds(ids);
                final List playlist = new ArrayList();
                if (pls != null) {
                    for (int i = 0; i < pls.size(); ++i) {
                        final Map m_play = (Map) pls.get(i);
                        playlist.add(m_play.get("playSource").toString());
                    }
                }
                final String[] file = new String[playlist.size()];
                for (int j = 0; j < playlist.size(); ++j) {
                    file[j] = String.valueOf(basepath) + "playSource" + File.separator + playlist.get(j).toString();
                }
                up.add(file);
                up.createUpdateFile(String.valueOf(m7binpath) + File.separator + "M7Update.bin");
                request.setAttribute("msg", (Object)"Bin\u66f4\u65b0\u6210\u529f");
            }
            catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("msg", (Object)"\u66f4\u65b0\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u6587\u4ef6\u7684\u683c\u5f0f,\u538b\u7f29\u7684\u6587\u4ef6\u4e0d\u80fd\u6709\u4e2d\u6587");
            }
        }
        return "success";
    }
    
    public String playListUpdateDeal2() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        if (this.img != null) {
            try {
                final String basepath = ServletActionContext.getServletContext().getRealPath("\\");
                final String m7apppath = String.valueOf(basepath) + "m7app" + File.separator + this.playListId;
                final String m7binpath = String.valueOf(basepath) + "update" + File.separator + this.playListId;
                final String m7apptemppath = String.valueOf(basepath) + "m7app" + File.separator + "temp" + File.separator + this.playListId;
                final String m7apptemppath2 = String.valueOf(basepath) + "m7app" + File.separator + "temp" + File.separator + "temp";
                MyFile.extZipFileList(this.img, m7apppath);
                final File f = new File(m7apptemppath);
                if (!f.exists()) {
                    f.mkdirs();
                }
                final File f2 = new File(m7apptemppath2);
                if (!f2.exists()) {
                    f2.mkdirs();
                }
                MyFile.extZipFileList(this.img, m7apptemppath);
                MyFile.extZipFileList(this.img, m7apptemppath2);
                final Format format = Format.getPrettyFormat();
                format.setIndent("    ");
                format.setEncoding("gb2312");
                XMLOutputter XMLOut = new XMLOutputter(format);
                final SAXBuilder sb = new SAXBuilder();
                final Document doc = sb.build(String.valueOf(m7apppath) + File.separator + "NEWCONFIG.XML");
                final Element root = doc.getRootElement();
                String version = root.getChild("Software").getChild("Version").getText();
                version = new StringBuilder().append(Integer.parseInt(version) + 1).toString();
                root.getChild("Software").getChild("Version").setText(version);
                XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(m7apppath) + File.separator + "NEWCONFIG.XML"));
                XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(m7binpath) + File.separator + "NEWCONFIG.XML"));
                XMLOut = null;
                final ZipManager zipManager = new ZipManager();
                System.out.println("\u5efa\u7acbzipManager\u5bf9\u8c61");
                zipManager.createZip(String.valueOf(m7apptemppath) + File.separator, String.valueOf(m7binpath) + File.separator + "M7Update.zip");
                System.out.println("\u751f\u6210zip\u5305");
                zipManager.createZip(String.valueOf(m7apptemppath2) + File.separator, String.valueOf(m7binpath) + File.separator + "M7Update" + version + ".zip");
                MyFile.delete(m7apptemppath2);
                System.out.println("\u5220\u9664\u7f13\u5b58");
                System.out.println("\u5efa\u7acb\u5b8c\u6574\u5347\u7ea7\u5305");
                request.setAttribute("msg", (Object)"Zip\u66f4\u65b0\u6210\u529f");
            }
            catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("msg", (Object)"\u66f4\u65b0\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u6587\u4ef6\u7684\u683c\u5f0f,\u538b\u7f29\u7684\u6587\u4ef6\u4e0d\u80fd\u6709\u4e2d\u6587");
            }
        }
        return "success";
    }
    
    public String playListConfigDialog() throws Exception {
        final String basePath = ServletActionContext.getServletContext().getRealPath("\\");
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String playListId = request.getParameter("playListId");
        final String filePath = String.valueOf(basePath) + "m7app" + File.separator + playListId + File.separator + "CONFIG.XML";
        SAXBuilder sb = new SAXBuilder(false);
        Document doc = sb.build(new File(filePath));
        final Element root = doc.getRootElement();
        final int autoShutDown = Integer.parseInt(root.getChildText("autoShutDown"));
        final int hh = autoShutDown / 3600;
        final int mm = autoShutDown % 3600 / 60;
        final Map<String, String> config = new HashMap<String, String>();
        config.put("rotate", root.getChildText("rotate"));
        config.put("update", root.getChildText("update"));
        config.put("CheckDelay", root.getChildText("CheckDelay"));
        config.put("autoShutDown", root.getChildText("autoShutDown"));
        config.put("hh", new StringBuilder().append(hh).toString());
        config.put("mm", new StringBuilder().append(mm).toString());
        config.put("NetDelay", root.getChildText("NetDelay"));
        config.put("version", root.getChildText("version"));
        config.put("ip", root.getChild("server").getChildText("ip"));
        config.put("port", root.getChild("server").getChildText("port"));
        config.put("approvertime", root.getChild("basic").getChildText("approvertime"));
        config.put("xmlHttpOvertime", root.getChild("basic").getChildText("xmlHttpOvertime"));
        config.put("showtimecontroltime", root.getChild("basic").getChildText("showtimecontroltime"));
        config.put("showwelcometime", root.getChild("basic").getChildText("showwelcometime"));
        config.put("readcardtime", root.getChild("basic").getChildText("readcardtime"));
        config.put("showEmployeePage", root.getChildText("ShowEmployeePage"));
        config.put("playListId", playListId);
        doc = null;
        sb = null;
        request.setAttribute("config", (Object)config);
        return "success";
    }
    
    public String playListConfigDialogAndroid() throws Exception {
        final String basePath = ServletActionContext.getServletContext().getRealPath(File.separator);
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String playListId = request.getParameter("playListId");
        final String filePath = String.valueOf(basePath) + "m7app" + File.separator + playListId + File.separator + "NEWCONFIG.XML";
        SAXBuilder sb = new SAXBuilder(false);
        Document doc = sb.build(new File(filePath));
        final Element root = doc.getRootElement();
        System.out.println("playListConfigDialog-filePath=" + filePath);
        final String[] Shutdown = root.getChild("Software").getChildText("Shutdown").toString().split(":");
        final String[] Boot = root.getChild("Software").getChildText("Boot").toString().split(":");
        final int Shutdownhh = Integer.valueOf(Shutdown[0]);
        final int Shutdownmm = Integer.valueOf(Shutdown[1]);
        final int Boothh = Integer.valueOf(Boot[0]);
        final int Bootmm = Integer.valueOf(Boot[1]);
        final Map<String, String> config = new HashMap<String, String>();
        config.put("Shutdownhh", new StringBuilder().append(Shutdownhh).toString());
        config.put("Shutdownmm", new StringBuilder().append(Shutdownmm).toString());
        config.put("Boothh", new StringBuilder().append(Boothh).toString());
        config.put("Bootmm", new StringBuilder().append(Bootmm).toString());
        config.put("Version", root.getChild("Software").getChildText("Version"));
        config.put("Welcometime", root.getChild("Software").getChildText("Welcometime"));
        config.put("Approvertime", root.getChild("Software").getChildText("Approvertime"));
        config.put("Shutdown", root.getChild("Software").getChildText("Shutdown"));
        config.put("Boot", root.getChild("Software").getChildText("Boot"));
        config.put("ShowEmployeePage", root.getChild("Software").getChildText("ShowEmployeePage"));
        config.put("IP", root.getChild("Server").getChildText("IP"));
        config.put("Port", root.getChild("Server").getChildText("Port"));
        config.put("Type", root.getChild("Network").getChildText("Type"));
        config.put("playListId", playListId);
        doc = null;
        sb = null;
        request.setAttribute("config", (Object)config);
        return "success";
    }
    
    public String playListConfigSave() throws Exception {
        final String basePath = ServletActionContext.getServletContext().getRealPath("\\");
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String playListId = request.getParameter("playListId");
        final String hh = request.getParameter("hh");
        final String mm = request.getParameter("mm");
        final int autoShutDown = Integer.parseInt(hh) * 3600 + Integer.parseInt(mm) * 60;
        final String m7apppath = String.valueOf(basePath) + "m7app" + File.separator + playListId + File.separator + "CONFIG.XML";
        final String m7binpath = String.valueOf(basePath) + "update" + File.separator + playListId + File.separator + "CONFIG.XML";
        final String updateFilepath = String.valueOf(basePath) + "UpdateFile" + File.separator + "CONFIG.XML";
        final SAXBuilder sb = new SAXBuilder();
        final Document doc = sb.build(m7apppath);
        final Element root = doc.getRootElement();
        root.getChild("rotate").setText(request.getParameter("rotate"));
        root.getChild("update").setText(request.getParameter("update"));
        root.getChild("CheckDelay").setText(request.getParameter("CheckDelay"));
        root.getChild("autoShutDown").setText(new StringBuilder().append(autoShutDown).toString());
        root.getChild("NetDelay").setText(request.getParameter("NetDelay"));
        root.getChild("version").setText(request.getParameter("version"));
        root.getChild("server").getChild("ip").setText(request.getParameter("ip"));
        root.getChild("server").getChild("port").setText(request.getParameter("port"));
        root.getChild("basic").getChild("approvertime").setText(request.getParameter("approvertime"));
        root.getChild("basic").getChild("xmlHttpOvertime").setText(request.getParameter("xmlHttpOvertime"));
        root.getChild("basic").getChild("showtimecontroltime").setText(request.getParameter("showtimecontroltime"));
        root.getChild("basic").getChild("showwelcometime").setText(request.getParameter("showwelcometime"));
        root.getChild("basic").getChild("readcardtime").setText(request.getParameter("readcardtime"));
        root.getChild("ShowEmployeePage").setText(request.getParameter("showEmployeePage"));
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        XMLOut.output(doc, (OutputStream)new FileOutputStream(m7apppath));
        XMLOut.output(doc, (OutputStream)new FileOutputStream(m7binpath));
        root.getChild("version").setText("1");
        XMLOut.output(doc, (OutputStream)new FileOutputStream(updateFilepath));
        XMLOut = null;
        return "success";
    }
    
    public String playListConfigSaveAndroid() throws Exception {
        final String basePath = ServletActionContext.getServletContext().getRealPath("\\");
        final HttpServletRequest request = ServletActionContext.getRequest();
        System.out.println("playListConfigSave-begin");
        final String playListId = request.getParameter("playListId");
        final String Shutdownhh = request.getParameter("Shutdownhh");
        final String Shutdownmm = request.getParameter("Shutdownmm");
        final String Boothh = request.getParameter("Boothh");
        final String Bootmm = request.getParameter("Bootmm");
        final String m7apppath = String.valueOf(basePath) + "m7app" + File.separator + playListId + File.separator + "NEWCONFIG.XML";
        final String m7binpath = String.valueOf(basePath) + "update" + File.separator + playListId + File.separator + "NEWCONFIG.XML";
        final String updateFilepath = String.valueOf(basePath) + "UpdateFile" + File.separator + "NEWCONFIG.XML";
        final SAXBuilder sb = new SAXBuilder();
        final Document doc = sb.build(m7apppath);
        final Element root = doc.getRootElement();
        root.getChild("Software").getChild("Version").setText(request.getParameter("Version"));
        root.getChild("Software").getChild("Welcometime").setText(request.getParameter("Welcometime"));
        root.getChild("Software").getChild("Approvertime").setText(request.getParameter("Approvertime"));
        root.getChild("Software").getChild("Shutdown").setText(String.valueOf(Shutdownhh) + ":" + Shutdownmm);
        root.getChild("Software").getChild("Boot").setText(String.valueOf(Boothh) + ":" + Bootmm);
        root.getChild("Software").getChild("ShowEmployeePage").setText(request.getParameter("ShowEmployeePage"));
        root.getChild("Server").getChild("IP").setText(request.getParameter("IP"));
        root.getChild("Server").getChild("Port").setText(request.getParameter("Port"));
        root.getChild("Network").getChild("Type").setText(request.getParameter("Type"));
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        XMLOut.output(doc, (OutputStream)new FileOutputStream(m7apppath));
        XMLOut.output(doc, (OutputStream)new FileOutputStream(m7binpath));
        XMLOut.output(doc, (OutputStream)new FileOutputStream(updateFilepath));
        XMLOut = null;
        return "success";
    }
}
