package com.sundyn.action;

import com.sundyn.service.ErrorInfoService;
import com.sundyn.util.Pager;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ErrorInfoAction extends MainAction
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
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, this);
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
