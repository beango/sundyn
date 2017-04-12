package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import com.sundyn.vo.*;
import java.util.*;
import javax.servlet.http.*;
import java.text.*;

public class QuicklyAction extends ActionSupport
{
    private Integer id;
    private String msg;
    private List quicklyList;
    private QuicklyService quicklyService;
    
    public Integer getId() {
        return this.id;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public List getQuicklyList() {
        return this.quicklyList;
    }
    
    public QuicklyService getQuicklyService() {
        return this.quicklyService;
    }
    
    public String quicklyAdd() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer id = Integer.valueOf(manager.get("id").toString());
        final String name = request.getParameter("quicklyQueryName");
        String url = request.getParameter("url");
        final int aa = url.indexOf("/query");
        url = url.substring(url.indexOf("/query"));
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final QuicklyVo quicklyVo = new QuicklyVo();
        quicklyVo.setName(name);
        quicklyVo.setExcuteSql(url);
        quicklyVo.setManagerId(new StringBuilder().append(id).toString());
        quicklyVo.setStartDate(df.format(new Date()));
        this.quicklyService.addSearch(quicklyVo);
        return "success";
    }
    
    public String quicklyAddDialog() throws Exception {
        return "success";
    }
    
    public String quicklyDel() throws Exception {
        this.quicklyService.delSearchById(this.id);
        return "success";
    }
    
    public String quicklyQuery() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        Integer id = Integer.valueOf(manager.get("id").toString());
        id = new Integer(1);
        this.quicklyList = this.quicklyService.findByManagerId(id);
        return "success";
    }
    
    public String quicklyQueryAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer id = Integer.valueOf(manager.get("id").toString());
        this.quicklyList = this.quicklyService.findByManagerId(id);
        return "success";
    }
    
    public String quicklyQueryDeal() throws Exception {
        final Map quickly = this.quicklyService.findById(this.id);
        final String sql = quickly.get("excuteSql").toString();
        this.msg = "\ufffd\ufffd\u02be\ufffd\ufffd\u046f\ufffd\ufffd\ufffd";
        return "success";
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public void setQuicklyList(final List quicklyList) {
        this.quicklyList = quicklyList;
    }
    
    public void setQuicklyService(final QuicklyService quicklyService) {
        this.quicklyService = quicklyService;
    }
}
