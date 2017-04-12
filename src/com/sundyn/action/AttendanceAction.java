package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import com.sundyn.util.*;
import org.apache.struts2.*;
import java.text.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;

public class AttendanceAction extends ActionSupport
{
    private AttendanceService attendanceService;
    private String endDate;
    private Pager pager;
    private String startDate;
    
    public String attendanceNO() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String attendDate = request.getParameter("attendDate");
        if (attendDate == null || attendDate.equals("")) {
            attendDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        final List ls = this.attendanceService.queryNO(this.startDate, this.endDate);
        request.setAttribute("ls", (Object)ls);
        return "success";
    }
    
    public String attendanceQuery() {
        return "success";
    }
    
    public String attendanceQueryAjax() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        if (this.startDate == null || this.startDate.equals("")) {
            this.startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            this.startDate = String.valueOf(this.startDate) + " 00:00:00";
        }
        if (this.endDate == null || this.endDate.equals("")) {
            this.endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
        final int rowsCount = this.attendanceService.count(this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 8, rowsCount, request, "attendancePage");
        final List pageList = this.attendanceService.query(this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(pageList);
        String filepath = ServletActionContext.getServletContext().getRealPath("/update");
        filepath = String.valueOf(filepath) + File.separator + "time.ini";
        try {
            final InputStream in = new FileInputStream(filepath);
            final Properties p = new Properties();
            p.load(in);
            final String sam = p.getProperty("sam", "08:30");
            final String eam = p.getProperty("eam", "12:00");
            final String spm = p.getProperty("spm", "14:30");
            final String epm = p.getProperty("epm", "17:00");
            request.setAttribute("sam", (Object)sam);
            request.setAttribute("eam", (Object)eam);
            request.setAttribute("spm", (Object)spm);
            request.setAttribute("epm", (Object)epm);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public AttendanceService getAttendanceService() {
        return this.attendanceService;
    }
    
    public String getEndDate() {
        return this.endDate;
    }
    
    public Pager getPager() {
        return this.pager;
    }
    
    public String getStartDate() {
        return this.startDate;
    }
    
    public void setAttendanceService(final AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
    
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
}
