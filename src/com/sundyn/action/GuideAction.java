package com.sundyn.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.DeptService;
import com.sundyn.service.EmployeeService;
import com.sundyn.util.Mysql;
import com.sundyn.util.SundynSet;
import com.sundyn.vo.DeptVo;
import com.sundyn.vo.EmployeeVo;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.jdom.JDOMException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuideAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    private DeptService deptService;
    private EmployeeService employeeService;
    private Integer deptType;
    private boolean guide;
    private String multi;
    private Map m;
    private List ls;
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public Integer getDeptType() {
        return this.deptType;
    }
    
    public void setDeptType(final Integer deptType) {
        this.deptType = deptType;
    }
    
    public boolean isGuide() {
        return this.guide;
    }
    
    public void setGuide(final boolean guide) {
        this.guide = guide;
    }
    
    public String getMulti() {
        return this.multi;
    }
    
    public void setMulti(final String multi) {
        this.multi = multi;
    }
    
    public Map getM() {
        return this.m;
    }
    
    public void setM(final Map m) {
        this.m = m;
    }
    
    public List getLs() {
        return this.ls;
    }
    
    public void setLs(final List ls) {
        this.ls = ls;
    }
    
    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }
    
    public void setEmployeeService(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    public String guideStart() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final Map system = sundynSet.getM_system();
        this.guide = Boolean.valueOf(system.get("guide").toString());
        final List<?> ls = (List<?>)this.employeeService.findAllEmployee();
        if (!this.guide) {
            System.out.println("guide=false\uff0c\u8fd4\u56deindex");
            return "index";
        }
        System.out.println("ls.size()=" + ls.size());
        if (ls != null && ls.size() > 1) {
            System.out.println("ls!=null,\u8fd4\u56deindex");
            return "index";
        }
        System.out.println("ls==null,\u8fd4\u56deguide");
        return "guide";
    }
    
    public String guideSimpleOne() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final boolean multi = Boolean.valueOf(request.getParameter("multi"));
        this.m = this.deptService.findDeptById(1);
        request.getSession().setAttribute("multi", (Object)multi);
        return "success";
    }

    public String guideSimpleOnePost() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        this.deptService.update(id, name);
        return "success";
    }

    public String guideSimpleTwo() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        boolean multi = false;
        try {
            multi = Boolean.valueOf(request.getSession().getAttribute("multi").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final Integer id = Integer.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        //this.deptService.update(id, name);
        final List ls = this.deptService.findchild(id);
        if (ls != null && ls.size() > 0) {
            this.m = (Map) ls.get(0);
        }
        if (multi) {
            return "multi";
        }
        return "success";
    }

    public String guideSimpleTwoPost() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String ids = request.getParameter("id");
        final String name = request.getParameter("name");
        final String remark = request.getParameter("remark");
        if (ids == null || ids == "") {
            final DeptVo dept = new DeptVo();
            dept.setName(name);
            dept.setFatherId(1);
            dept.setLenvel(2);
            dept.setDeptType(2);
            dept.setRemark(remark);
            this.deptService.addDept(dept, 1);
            this.m = this.deptService.findDeptByName(name);
            request.setAttribute("fatherId", (Object)Integer.valueOf(this.m.get("id").toString()));
        }
        else {
            final Integer id = Integer.valueOf(ids);
            this.deptService.update(id, name, remark);
            this.m = this.deptService.findDeptById(id);
            this.ls = this.deptService.findchild(id);
            request.setAttribute("fatherId", (Object)id);
        }
        return "success";
    }

    public String guideSimpleThree() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String ids = request.getParameter("id");
        final String name = request.getParameter("name");
        final String remark = request.getParameter("remark");
        if (ids == null || ids == "") {
            final DeptVo dept = new DeptVo();
            dept.setName(name);
            dept.setFatherId(1);
            dept.setLenvel(2);
            dept.setDeptType(2);
            dept.setRemark(remark);
            //this.deptService.addDept(dept, 1);
            this.m = this.deptService.findDeptByName(name);
            request.setAttribute("fatherId", (Object)Integer.valueOf(this.m.get("id").toString()));
        }
        else {
            final Integer id = Integer.valueOf(ids);
            this.m = this.deptService.findDeptById(id);
            this.ls = this.deptService.findchild(id);
            request.setAttribute("fatherId", (Object)id);
        }
        return "success";
    }
    
    public String guideEdit() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        this.m = this.deptService.findDeptById(id);
        final JSONObject json = JSONObject.fromObject((Object)this.m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String guideDel() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        this.deptService.deleteDept(id);
        return "success";
    }
    
    public String quideSaveOrUpdate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        final String fatherId = request.getParameter("fatherId");
        final String name = request.getParameter("name");
        final String remark = request.getParameter("remark");
        final String client_type = request.getParameter("client_type");
        final DeptVo deptVo = new DeptVo();
        if (!id.equals("")) {
            this.deptService.update(Integer.valueOf(id), name, remark, client_type);
        }
        else {
            deptVo.setName(Mysql.mysql(name));
            deptVo.setRemark(remark);
            deptVo.setClient_type(Integer.valueOf(client_type));
            deptVo.setDeptType(0);
            deptVo.setDept_businessId(-1);
            deptVo.setFatherId(Integer.valueOf(fatherId));
            deptVo.setDept_playListId(-1);
            this.m = this.deptService.findDeptById(Integer.valueOf(fatherId));
            deptVo.setLenvel(Integer.valueOf(this.m.get("lenvel").toString()) + 1);
            this.deptService.addDept(deptVo, Integer.valueOf(fatherId));
        }
        return "success";
    }
    
    public String guideAjax() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer fatherId = Integer.parseInt(request.getParameter("fatherId"));
        this.ls = this.deptService.findchild(fatherId);
        return "success";
    }
    
    public String guideSimpleFour() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer fatherId = Integer.valueOf(request.getParameter("fatherId"));
        this.ls = this.employeeService.findEmployeeByDeptid(fatherId, 0, 40);
        request.setAttribute("deptId", (Object)fatherId);
        return "success";
    }
    
    public String guideSimpleEmployeeEdit() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        this.m = this.employeeService.findEmployeeById(id);
        final JSONObject object = JSONObject.fromObject((Object)this.m);
        request.setAttribute("json", (Object)object);
        return "success";
    }
    
    public String guideSimpleEmployeeDel() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        this.employeeService.delEmployee(id);
        return "success";
    }
    
    public String guideSimpleEmployeeSaveOrUpdate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer deptId = Integer.valueOf(request.getParameter("deptId"));
        final String id = request.getParameter("id");
        final String name = request.getParameter("name");
        final String cardNum = request.getParameter("cardNum");
        final String sex = request.getParameter("sex");
        final String imgName = request.getParameter("imgName");
        final String ext2 = request.getParameter("ext2");
        final String job_desc = request.getParameter("job_desc");
        if (id == null || id.equals("")) {
            final EmployeeVo employeeVo = new EmployeeVo();
            employeeVo.setName(name);
            employeeVo.setCardnum(cardNum);
            employeeVo.setSex(sex);
            employeeVo.setDeptid(deptId);
            employeeVo.setPicture(imgName);
            employeeVo.setExt2(ext2);
            employeeVo.setJob_desc(job_desc);
            employeeVo.setPassWord("49BA59ABBE56E057");
            this.employeeService.addEmployee(employeeVo);
        }
        else {
            this.employeeService.UpdateEmployee(Integer.valueOf(id), name, sex, cardNum, imgName, ext2, job_desc);
        }
        return "success";
    }
    
    public String guideSimpleEmployeeAjax() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer deptId = Integer.valueOf(request.getParameter("deptId"));
        this.ls = this.employeeService.findEmployeeByDeptid(deptId, 0, 40);
        return "success";
    }
    
    public String guideComplete() throws JDOMException, IOException {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final Map system = sundynSet.getM_system();
        final Map m = (Map)((HashMap)system).clone();
        m.put("guide", "false");
        sundynSet.update(m, null, null, null);
        return "success";
    }
    
    public String guideLeft() throws JDOMException, IOException {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final Map system = sundynSet.getM_system();
        final Map m = (Map)((HashMap)system).clone();
        m.put("guide", "true");
        sundynSet.update(m, null, null, null);
        return "success";
    }
    
    public String guideMultiSelect() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        final Map m = this.deptService.findDeptById(id);
        final Integer fatherId = Integer.valueOf(m.get("fatherId").toString());
        this.deptType = Integer.valueOf(m.get("deptType").toString());
        this.ls = this.deptService.findchild(fatherId);
        request.setAttribute("id", (Object)id);
        return "success";
    }
    
    public String guideMultiSelect1() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer fatherId = Integer.valueOf(request.getParameter("fatherId"));
        final Integer deptType = Integer.valueOf(request.getParameter("deptType"));
        if (deptType.equals(0)) {
            final Map m = this.deptService.findDeptById(fatherId);
            if (m != null) {
                this.ls = this.deptService.findchild(Integer.valueOf(m.get("fatherId").toString()));
            }
            request.setAttribute("fatherId", (Object)fatherId);
            request.setAttribute("deptType", (Object)deptType);
        }
        else {
            this.ls = this.deptService.findchild(fatherId);
            request.setAttribute("fatherId", (Object)fatherId);
            request.setAttribute("deptType", (Object)deptType);
        }
        return "success";
    }
    
    public String guideMultiDept() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        this.ls = this.deptService.findchild(id, this.deptType);
        this.m = this.deptService.findDeptById(id);
        request.setAttribute("id", (Object)id);
        return "success";
    }
    
    public String guideMultiDeptSaveOrUpdate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        final String fatherId = request.getParameter("fatherId");
        final String deptType = request.getParameter("deptType");
        final String name = request.getParameter("name");
        final String remark = request.getParameter("remark");
        String client_type = request.getParameter("client_type");
        if (client_type == null || client_type.equals("")) {
            client_type = "2";
        }
        Integer lenvel = 0;
        try {
            final Map m = this.deptService.findDeptById(Integer.valueOf(fatherId));
            lenvel = Integer.valueOf(m.get("lenvel").toString()) + 1;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final DeptVo deptVo = new DeptVo();
        deptVo.setFatherId(Integer.valueOf(fatherId));
        deptVo.setDeptType(Integer.valueOf(deptType));
        deptVo.setName(name);
        deptVo.setRemark(remark);
        deptVo.setLenvel(lenvel);
        try {
            deptVo.setClient_type(Integer.valueOf(client_type));
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        if (id == null || id.equals("")) {
            this.deptService.addDept(deptVo, Integer.valueOf(fatherId));
        }
        else {
            this.deptService.update(Integer.valueOf(id), name, remark);
        }
        return "success";
    }
    
    public String guideMultiAjax() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer fatherId = Integer.parseInt(request.getParameter("fatherId"));
        this.ls = this.deptService.findchild(fatherId, this.deptType);
        return "success";
    }
    
    public String guideDeptTypeAjax() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.parseInt(request.getParameter("id"));
        final Map m = this.deptService.findDeptById(id);
        if (m != null) {
            this.deptType = Integer.valueOf(m.get("deptType").toString());
        }
        return "success";
    }
}
