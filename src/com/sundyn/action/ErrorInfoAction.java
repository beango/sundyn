package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import com.sundyn.util.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.text.*;
import java.util.*;

public class ErrorInfoAction extends ActionSupport
{
    private String endDate;
    private ErrorInfoService errorInfoService;
    private Pager pager;
    private String startDate;
    
    public String errorInfoQuery() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String curdate = df.format(new Date());
        if (this.startDate == null || this.startDate.equals("")) {
            this.startDate = curdate;
        }
        if (this.endDate == null || this.endDate.equals("")) {
            this.endDate = curdate;
        }
        final int rowsCount = this.errorInfoService.query(this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 20, rowsCount, request);
        final List templist = this.errorInfoService.query(this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(templist);
        return "success";
    }
    
    public String getEndDate() {
        return this.endDate;
    }
    
    public ErrorInfoService getErrorInfoService() {
        return this.errorInfoService;
    }
    
    public Pager getPager() {
        return this.pager;
    }
    
    public String getStartDate() {
        return this.startDate;
    }
    
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    
    public void setErrorInfoService(final ErrorInfoService errorInfoService) {
        this.errorInfoService = errorInfoService;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
}
