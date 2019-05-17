package com.sundyn.action;

import com.sundyn.service.WeburlService;
import com.sundyn.util.Pager;
import com.sundyn.utils.JavaXML;
import com.sundyn.vo.WeburlVo;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeburlAction extends MainAction
{
    private WeburlVo weburl;
    private WeburlService weburlService;
    private String isDeal;
    private List weburls;
    private String url;
    private InputStream xml;
    private String msg;
    private Pager pager;

    public String weburlToAdd() {
        return "weburlToAdd";
    }

    public String weburlAdd() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("name");
        final String id = request.getParameter("id");
        if (name.equals("")){
            this.msg = "标题不能为空!";
            return "msg";
        }
        if (weburlService.existsByName(id, name)){
            this.msg = "添加失败:已经存在相同标题的记录";
            return "msg";
        }
        final String url = request.getParameter("url");
        if (this.weburl == null) {
            this.weburl = new WeburlVo();
        }
        this.weburl.setName(name);
        this.weburl.setUrl(url);
        if (this.weburlService.addWeburl(this.weburl)) {
            return "msg";
        }
        this.msg = "添加失败";
        return "msg";
    }

    public String weburlList() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String uri = request.getRequestURL().toString();
        final String key_title = request.getParameter("key_title");
        final int rowsCount = this.weburlService.getCount(key_title);
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "weburlPage", this);
        this.weburls = this.weburlService.findWeburl(key_title,(this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(this.weburls);
        return "weburlListOk";
    }

    public String weburlToUpdate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        final Map map = this.weburlService.findWeburlById(Integer.parseInt(id));
        if (this.weburl == null) {
            this.weburl = new WeburlVo();
        }
        this.weburl.setName((String) map.get("name"));
        this.weburl.setUrl((String) map.get("url"));
        this.weburl.setId(Integer.parseInt(id));
        return "weburlToUpdate";
    }

    public String weburlUpdate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("name");
        final String url = request.getParameter("url");
        final String id = request.getParameter("id");

        if (name.equals("")){
            this.msg = "标题不能为空!";
            return "msg";
        }
        if (weburlService.existsByName(id, name)){
            this.msg = "添加失败:已经存在相同标题的记录";
            return "msg";
        }
        if (this.weburl == null) {
            this.weburl = new WeburlVo();
        }
        this.weburl.setName(name);
        this.weburl.setUrl(url);
        this.weburl.setId(Integer.parseInt(id));
        this.weburlService.weburlUpdate(this.weburl);
        return "msg";
    }

    public String weburlDelete() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        this.weburlService.weburlDelete(Integer.parseInt(id));
        return "weburlDeleteOk";
    }

    public String weburlDownload() throws FileNotFoundException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        File f = JavaXML.XMLOutFile(mac+".xml");
        if (!f.exists()) {
            f.createNewFile();
        }
        this.xml = new FileInputStream(f);
        if (mac == null || mac.equals("")) {
            final HttpServletResponse response = ServletActionContext.getResponse();
            final PrintWriter out = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            out.println("mac is null");
            out.flush();
            out.close();
            return null;
        }
        final List list = this.weburlService.bindedWeburl(mac);
        if (list != null) {
            final List<WeburlVo> weburls = new ArrayList<WeburlVo>();
            for (final Object map1 : list) {
            	Map map = (Map)map1;
                final WeburlVo web = new WeburlVo();
                web.setName((String) map.get("name"));
                web.setUrl((String) map.get("url"));
                weburls.add(web);
            }
            JavaXML.downloadWeburl(weburls, mac);
            return "weburlDownloadOk";
        }
        final HttpServletResponse response2 = ServletActionContext.getResponse();
        final PrintWriter out2 = response2.getWriter();
        response2.setCharacterEncoding("UTF-8");
        response2.setHeader("Pragma", "No-cache");
        response2.setHeader("Cache-Control", "no-cache");
        response2.setDateHeader("Expires", 0L);
        out2.println("no records");
        out2.flush();
        out2.close();
        return null;
    }

    public WeburlVo getWeburl() {
        return this.weburl;
    }

    public void setWeburl(final WeburlVo weburl) {
        this.weburl = weburl;
    }

    public WeburlService getWeburlService() {
        return this.weburlService;
    }

    public void setWeburlService(final WeburlService weburlService) {
        this.weburlService = weburlService;
    }

    public String getIsDeal() {
        return this.isDeal;
    }

    public void setIsDeal(final String isDeal) {
        this.isDeal = isDeal;
    }

    public List<WeburlVo> getWeburls() {
        return (List<WeburlVo>)this.weburls;
    }

    public void setWeburls(final List<WeburlVo> weburls) {
        this.weburls = weburls;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String testAction() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("name");
        final String password = request.getParameter("password");
        final HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (this.weburlService.testAction(name)) {
                out.print("success");
            }
            else {
                out.print("error");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            out.print("error");
        }
        return null;
    }

    public InputStream getXml() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        String file = JavaXML.class.getClassLoader().getResource("").getPath();
        file = file.replaceAll("%20", " ");
        file = String.valueOf(file.substring(1, file.indexOf("classes"))) + "source/";
        final String filename = String.valueOf(mac) + ".xml";
        final String url = String.valueOf(file) + filename;
        return ServletActionContext.getServletContext().getResourceAsStream("/WEB-INF/source/" + filename);
    }

    public void setXml(final InputStream xml) {
        this.xml = xml;
    }

    public String getMsg() { return this.msg; }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public Pager getPager() {
        return this.pager;
    }

    public void setPager(final Pager pager) {
        this.pager = pager;
    }
}
