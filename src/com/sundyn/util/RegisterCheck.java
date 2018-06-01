package com.sundyn.util;

import com.opensymphony.xwork2.interceptor.*;
import com.sundyn.service.*;
import com.opensymphony.xwork2.*;
import java.util.*;

public class RegisterCheck extends AbstractInterceptor
{
    private static final long serialVersionUID = 1L;
    private boolean use;
    private DeptService deptService;
    private Reg reg;
    
    public boolean isUse() {
        return this.use;
    }
    
    public void setUse(final boolean use) {
        this.use = use;
    }
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public Reg getReg() {
        return this.reg;
    }
    
    public void setReg(final Reg reg) {
        this.reg = reg;
    }
    
    public String intercept(final ActionInvocation arg0) throws Exception {
        System.out.println("RegisterCheck>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        this.reg = Reg.getInstance();
        arg0.getInvocationContext().put("ser", (Object)this.reg.getSerial());
        final boolean regcheck = this.reg.isRegCheck();
        final int windowNum = this.reg.getWindowNum();
        final int datingNum = this.reg.getDatingNum();
        List ls = this.deptService.getAllleafes();
        int wn = 0;
        int dn = 0;
        if (ls != null) {
            wn = ls.size();
        }
        ls = this.deptService.getAlllDating();
        if (ls != null) {
            dn = ls.size();
        }
        if (regcheck) {
            if (wn > windowNum) {
                arg0.getInvocationContext().put("msg", (Object)("\ufffd\ufffd\u05bb\ufffd\ufffd\u02b9\ufffd\ufffd" + windowNum + "\u0328\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd,\ufffd\ufffd\ufffd\ufffd\ufffd\u02b9\ufffd\u00f6\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05e2\ufffd\ufffd"));
                return "register";
            }
            if (dn > datingNum) {
                arg0.getInvocationContext().put("msg", (Object)("\ufffd\ufffd\u05bb\ufffd\ufffd\u02b9\ufffd\ufffd" + datingNum + "\ufffd\ufffd\ufffd\ufffd,\ufffd\ufffd\ufffd\ufffd\ufffd\u02b9\ufffd\u00f6\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05e2\ufffd\ufffd"));
                return "register";
            }
            return arg0.invoke();
        }
        else {
            final int d = this.reg.getDay();
            if (d > 0) {
                return arg0.invoke();
            }
            arg0.getInvocationContext().put("msg", (Object)"\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u047e\ufffd\ufffd\ufffd\ufffd\u06a3\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\u05a4\ufffd\ub8ec\ufffd\ufffd\ufffd\ufffd\u02b9\ufffd\ufffd");
            return "register";
        }
    }
}
