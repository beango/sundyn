package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.util.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import javax.servlet.http.*;

public class RegisterAction extends ActionSupport
{
    private static final long serialVersionUID = 7440588664847739180L;
    private Reg reg;
    private DeptService deptService;
    
    public Reg getReg() {
        return this.reg;
    }
    
    public void setReg(final Reg reg) {
        this.reg = reg;
    }
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public String registerView() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String sip = request.getHeader("x-forwarded-for");
        sip = request.getHeader("proxy-Client-IP");
        sip = request.getHeader("WL-Proxy-Client-IP");
        this.reg = Reg.getInstance();
        final int d = this.reg.getDay();
        final String ser = this.reg.getSerial();
        request.setAttribute("ser", (Object)ser);
        final boolean check = this.reg.isRegCheck();
        final int windwoNum = this.reg.getWindowNum();
        final int datingNum = this.reg.getDatingNum();
        if (check) {
            request.setAttribute("msg", (Object)("\u60a8\u7684\u8f6f\u4ef6\u5df2\u7ecf\u6ce8\u518c<br/>\u60a8\u7684\u6ce8\u518c\u53f7\u6700\u591a\u53ea\u80fd\u4f7f\u7528" + windwoNum + "\u8bc4\u4ef7\u5668\uff0c" + datingNum + "\u5927\u5385\uff0c" + "\u5982\u679c\u8981\u4f7f\u7528\u66f4\u591a\u8d44\u6e90\uff0c\u8bf7\u518d\u6b21\u7533\u8bf7\u6ce8\u518c"));
            return "success";
        }
        if (d > 0) {
            request.setAttribute("msg", (Object)("\u4f60\u7684\u8f6f\u4ef6\u8fd8\u6709" + d + "\u5929\u5230\u671f\uff0c\u8bf7\u53ca\u65f6\u6ce8\u518c"));
            return "success";
        }
        request.setAttribute("msg", (Object)"\u4f60\u7684\u8f6f\u4ef6\u5df2\u7ecf\u8fc7\u671f\uff0c\u8bf7\u53ca\u65f6\u6ce8\u518c");
        return "success";
    }
    
    public String registerReg() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String serTxt = request.getParameter("serTxt");
        (this.reg = Reg.getInstance()).register(serTxt);
        Reg.reset();
        return "success";
    }
}
