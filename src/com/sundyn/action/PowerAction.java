package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.entity.AppriesFunc;
import com.sundyn.entity.AppriesPowerfunc;
import com.sundyn.service.*;
import com.sundyn.util.Mysql;
import com.sundyn.util.Pager;
import com.sundyn.vo.PowerVo;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PowerAction extends MainAction
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

    @Resource
    private IAppriesPowerfuncService powerFuncService;
    @Resource
    private IAppriesFuncService appriesFuncService;

    public String powerQuery() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int rowsCount = this.powerService.countByName("");
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "lowerPowerPage", this);
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
        name = name.trim();
        //final Map manager = (Map)request.getSession().getAttribute("manager");
        //final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        //final Map power = this.powerService.getUserGroup(groupid);
        //final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptGroups = this.deptService.findChildALLStr123(super.getUserDept().toString());
        final int rowsCount = this.powerService.countLowerPowerByName(name, deptGroups);
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "lowerPowerPage", this);
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
        name = name.trim();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptGroups = this.deptService.findChildALLStr123(deptIdGroup);
        final int rowsCount = this.powerService.countLowerPowerByName(name, deptGroups);
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "lowerPowerPage", this);
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
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "lowerPowerPage", this);
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
            this.powerService.delPowerAndFunc(id);
        }
        return "success";
    }
    
    public String powerAddDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final List list = this.deptService.findChildALL(super.getUserDept().toString());
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
        if (this.powerService.powerExist(null, name)) {
            this.msg = "该角色名存在";
        }
        else {
            this.msg = "";
        }

        final Integer baseSet = Integer.valueOf(request.getParameter("baseSet"));
        final Integer dataManage = Integer.valueOf(request.getParameter("dataManage"));
        final Integer deptId = Integer.valueOf(request.getParameter("deptId"));
        String powertype = req.getString("powertype");
        int status = req.getInt("status",0);

        final String funcid = req.getString("funcid");
        if (funcid!=null){
            List<AppriesFunc> allFuncs = appriesFuncService.selectList(null);
            String[] funcidArr = funcid.split(",");
            List<AppriesPowerfunc> pfList = new ArrayList<>();
            for (String f : funcidArr) {
                if (f.equals(""))
                    continue;
                AppriesFunc func = getAppeiesFuncById(allFuncs, Integer.valueOf(f));
                if (func == null)
                    continue;
                AppriesPowerfunc pf = new AppriesPowerfunc();
                pf.setFuncCode(func.getFuncCode());
                pf.setPowerName(name);
                pfList.add(pf);
                boolean isInsert = powerFuncService.insert(pf);
            }
        }

        final PowerVo powerVo = new PowerVo();
        powerVo.setName(name);
        powerVo.setBaseSet(baseSet);
        powerVo.setDataManage(dataManage);
        powerVo.setDeptIdGroup(deptId);
        powerVo.setPowertype(powertype);
        powerVo.setStatus(status);
        this.powerService.addUserGroup(powerVo);
        return "success";
    }
    
    public String powerEditDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        final Map m = this.powerService.getUserGroup(id);

        final List list = this.deptService.findChildALL(super.getUserDept().toString());
        if (list != null && list.size() > 0) {
            final Map dept = (Map) list.get(0);
            dept.put("fatherId", -1);
        }
        if (id>0){
            Map oldpower = powerService.getUserGroup(id);
            EntityWrapper<AppriesPowerfunc> ew = new EntityWrapper<AppriesPowerfunc>();
            ew.where("powerName={0}", oldpower.get("name"));
            List<AppriesPowerfunc> powerFuncList = powerFuncService.selectListEx(ew);

            request.setAttribute("powerFunc", powerFuncList);
        }
        request.setAttribute("m", (Object)m);
        request.setAttribute("list", (Object)list);
        return "success";
    }
    
    public String powerEdit() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("name");
        final Integer id = req.getInt("id", 0);
        if (id==0){
            return powerAdd();
        }
        if (this.powerService.powerExist(String.valueOf(id), name)) {
            this.msg = "该角色名存在";
        }
        else {
            this.msg = "";
        }
        final Integer baseSet = Integer.valueOf(request.getParameter("baseSet"));
        final Integer dataManage = Integer.valueOf(request.getParameter("dataManage"));
        final Integer deptId = Integer.valueOf(request.getParameter("deptId"));
        final String funcid = req.getString("funcid");
        String powertype = req.getString("powertype");
        int status = req.getInt("status");
        Map oldpower = powerService.getUserGroup(id);
        if (funcid!=null && oldpower!=null){
            List<AppriesFunc> allFuncs = appriesFuncService.selectList(null);

            EntityWrapper ew=new EntityWrapper();
            ew.setEntity(new AppriesPowerfunc());
            ew.where("powerName = {0}", oldpower.get("name"));
            boolean isdel = powerFuncService.delete(ew);

            String[] funcidArr = funcid.split(",");
            List<AppriesPowerfunc> pfList = new ArrayList<>();
            for (String f : funcidArr) {
                if (f.equals(""))
                    continue;
                AppriesFunc func = getAppeiesFuncById(allFuncs, Integer.valueOf(f));
                if (func == null)
                    continue;
                AppriesPowerfunc pf = new AppriesPowerfunc();
                pf.setFuncCode(func.getFuncCode());
                pf.setPowerName(name);
                pfList.add(pf);
                boolean isInsert = powerFuncService.insert(pf);
            }
        }
        final PowerVo powerVo = new PowerVo();
        powerVo.setId(id);
        powerVo.setName(name);
        powerVo.setBaseSet(baseSet);
        powerVo.setDataManage(dataManage);
        powerVo.setDeptIdGroup(deptId);
        powerVo.setPowertype(powertype);
        powerVo.setStatus(status);
        this.powerService.update(powerVo);
        return "success";
    }

    private AppriesFunc getAppeiesFuncById(List<AppriesFunc> allFuncs, Integer integer) {
        for (AppriesFunc f : allFuncs){
            if (f.getId().equals(integer))
                return f;
        }
        return null;
    }

    public String powerExist() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("name");
        final String id = request.getParameter("id");
        if (this.powerService.powerExist(id, name)) {
            this.msg = "该角色名存在";
        }
        else {
            this.msg = "";
        }
        return "success";
    }

    public String powerCopy(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        boolean succ = this.powerService.copyService(id);
        this.msg = "复制成功";
        return "success";
    }
}
