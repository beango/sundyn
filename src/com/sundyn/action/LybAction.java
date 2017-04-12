package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import com.sundyn.vo.*;
import java.util.*;
import javax.servlet.http.*;
import java.text.*;

public class LybAction extends ActionSupport
{
    private Integer id;
    private List list;
    private Map lyb;
    private LybService lybService;
    private String msg;
    
    public Integer getId() {
        return this.id;
    }
    
    public List getList() {
        return this.list;
    }
    
    public Map getLyb() {
        return this.lyb;
    }
    
    public LybService getLybService() {
        return this.lybService;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public String lybAdd() {
        return "success";
    }
    
    public String lybAddDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final String lybTitle = request.getParameter("lybTitle");
        final String lybAsk = request.getParameter("lybAsk");
        final LybVo lybVo = new LybVo();
        lybVo.setLybTitle(lybTitle);
        lybVo.setLybAsk(lybAsk);
        lybVo.setLybAskId(manager.get("id").toString());
        lybVo.setLybState("0");
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        lybVo.setLybAskDate(df.format(new Date()));
        this.lybService.add(lybVo);
        return "success";
    }
    
    public String lybDel() {
        this.lybService.del(this.id);
        return "success";
    }
    
    public String lybEdit() {
        this.lyb = this.lybService.findById(this.id);
        return "success";
    }
    
    public String lybEditDeal() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final String lybId = request.getParameter("lybId");
        final String lybAnswer = request.getParameter("lybAnswer");
        final LybVo lybVo = new LybVo();
        lybVo.setLybId(Integer.parseInt(lybId));
        lybVo.setLybAnswerId(manager.get("id").toString());
        lybVo.setLybState("1");
        lybVo.setLybAnswer(lybAnswer);
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        lybVo.setLybAnswerDate(df.format(new Date()));
        this.lybService.update(lybVo);
        return "success";
    }
    
    public String lybManage() {
        this.list = this.lybService.findAll();
        return "success";
    }
    
    public String lybQuery() {
        this.list = this.lybService.findAll();
        return "success";
    }
    
    public String lybQueryAjax() {
        this.list = this.lybService.findAll();
        return "success";
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public void setList(final List list) {
        this.list = list;
    }
    
    public void setLyb(final Map lyb) {
        this.lyb = lyb;
    }
    
    public void setLybService(final LybService lybService) {
        this.lybService = lybService;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
}
