package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.util.*;
import com.sundyn.util.*;
import com.sundyn.vo.*;

public class PowerAction extends ActionSupport
{
    private Pager pager;
    private PowerService powerService;
    private DeptService deptService;
    private ManagerService managerService;
    private String msg;
    
    public Pager getPager() {
        return this.pager;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public PowerService getPowerService() {
        return this.powerService;
    }
    
    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public ManagerService getManagerService() {
        return this.managerService;
    }
    
    public void setManagerService(final ManagerService managerService) {
        this.managerService = managerService;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public String powerQuery() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int rowsCount = this.powerService.countByName("");
        this.pager = new Pager("currentPage", 10, rowsCount, request, "lowerPowerPage");
        this.pager = new Pager("currentPage", 10, rowsCount, request, "lowerPowerPage");
        final List list = this.powerService.findByName("", (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(list);
        return "success";
    }
    
    public String lowerPowerQuery() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }
        System.out.println("name-lowerPowerQuery=" + name);
        name = name.trim();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptGroups = this.deptService.findChildALLStr123(deptIdGroup);
        final int rowsCount = this.powerService.countLowerPowerByName(name, deptGroups);
        this.pager = new Pager("currentPage", 10, rowsCount, request, "lowerPowerPage");
        final List list = this.powerService.findLowerPowerByName(name, deptGroups, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(list);
        return "success";
    }
    
    public String lowerPowerQueryAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }
        System.out.println("name-lowerPowerQueryAjax=" + name);
        name = name.trim();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptGroups = this.deptService.findChildALLStr123(deptIdGroup);
        final int rowsCount = this.powerService.countLowerPowerByName(name, deptGroups);
        System.out.println("rowsCount2=" + rowsCount);
        this.pager = new Pager("currentPage", 10, rowsCount, request, "lowerPowerPage");
        final List list = this.powerService.findLowerPowerByName(name, deptGroups, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(list);
        request.setAttribute("keyword", (Object)name);
        return "success";
    }
    
    public String powerQueryAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        keyword = keyword.trim();
        keyword = Mysql.mysql(keyword);
        final int rowsCount = this.powerService.countByName(keyword);
        this.pager = new Pager("currentPage", 10, rowsCount, request, "lowerPowerPage");
        this.pager = new Pager("currentPage", 10, rowsCount, request, "lowerPsowerPage");
        final List list = this.powerService.findByName(keyword, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(list);
        request.setAttribute("keyword", (Object)keyword);
        return "success";
    }
    
    public String powerDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        if (this.managerService.isUseUserGroupId(id)) {
            this.msg = this.getText("sundyn.useNotDel");
        }
        else {
            this.msg = this.getText("sundyn.deleteSuccess");
            this.powerService.delUserGroup(id);
        }
        return "success";
    }
    
    public String powerAddDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final List list = this.deptService.findChildALL(deptIdGroup);
        if (list != null && list.size() > 0) {
            final Map dept = (Map) list.get(0);
            dept.put("fatherId", -1);
        }
        request.setAttribute("list", (Object)list);
        return "success";
    }
    
    public String powerAdd() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        request.setCharacterEncoding("GBK");
        final String name = request.getParameter("name");
        final Integer baseSet = Integer.valueOf(request.getParameter("baseSet"));
        final Integer dataManage = Integer.valueOf(request.getParameter("dataManage"));
        final Integer deptId = Integer.valueOf(request.getParameter("deptId"));
        final PowerVo powerVo = new PowerVo();
        powerVo.setName(name);
        powerVo.setBaseSet(baseSet);
        powerVo.setDataManage(dataManage);
        powerVo.setDeptIdGroup(deptId);
        this.powerService.addUserGroup(powerVo);
        return "success";
    }
    
    public String powerEditDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        final Map m = this.powerService.getUserGroup(id);
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        System.out.println("deptIdGroup=" + deptIdGroup);
        final List list = this.deptService.findChildALL(deptIdGroup);
        if (list != null && list.size() > 0) {
            final Map dept = (Map) list.get(0);
            dept.put("fatherId", -1);
        }
        request.setAttribute("m", (Object)m);
        request.setAttribute("list", (Object)list);
        return "success";
    }
    
    public String powerEdit() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("name");
        final Integer id = Integer.valueOf(request.getParameter("id"));
        final Integer baseSet = Integer.valueOf(request.getParameter("baseSet"));
        final Integer dataManage = Integer.valueOf(request.getParameter("dataManage"));
        final Integer deptId = Integer.valueOf(request.getParameter("deptId"));
        final PowerVo powerVo = new PowerVo();
        powerVo.setId(id);
        powerVo.setName(name);
        powerVo.setBaseSet(baseSet);
        powerVo.setDataManage(dataManage);
        powerVo.setDeptIdGroup(deptId);
        this.powerService.update(powerVo);
        return "success";
    }
    
    public String powerExist() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("name");
        if (this.powerService.powerExist(name)) {
            this.msg = "\ufffd\u00fd\ufffd\u026b\ufffd\ufffd\ufffd\ufffd\ufffd";
        }
        else {
            this.msg = "";
        }
        return "success";
    }
}
