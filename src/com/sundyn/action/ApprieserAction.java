package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import java.util.*;

public class ApprieserAction extends ActionSupport
{
    private ApprieserService apprieserService;
    private List list;
    
    public String apprieserQuery() throws Exception {
        this.list = this.apprieserService.findAll();
        return "success";
    }
    
    public ApprieserService getApprieserService() {
        return this.apprieserService;
    }
    
    public List getList() {
        return this.list;
    }
    
    public void setApprieserService(final ApprieserService apprieserService) {
        this.apprieserService = apprieserService;
    }
    
    public void setList(final List list) {
        this.list = list;
    }
}
