package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import net.sf.json.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import com.sundyn.util.*;
import java.sql.*;

public class AnalyseAction extends ActionSupport
{
    private AnalyseService analyseService;
    private List bmls;
    private List chartList;
    private DeptService deptService;
    private String endDate;
    private boolean k7;
    private KeyTypeService keyTypeService;
    private List mls;
    private String msg;
    private PowerService powerService;
    private String startDate;
    private String type;
    
    public String analyseContent() {
        return "success";
    }
    
    public String analyseContentAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.endDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String contentId = this.getContentId();
        this.chartList = this.analyseService.AnalyseContent(this.startDate, this.endDate, contentId, type, this.getDeptIds());
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else if (type.equals("JiDu")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u5b63\u5ea6");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseContentAjaxDay() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String num = request.getParameter("num");
        final Integer numI = Integer.valueOf(num);
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.endDate = df.format(new Date());
        final Calendar cal = Calendar.getInstance();
        cal.set(6, cal.get(6) - numI);
        this.startDate = df.format(cal.getTime());
        final String contentId = this.getContentId();
        this.chartList = this.analyseService.AnalyseContent(this.startDate, this.endDate, contentId, "day", this.getDeptIds());
        final List tempList = new ArrayList();
        for (int i = 0; i < this.chartList.size(); ++i) {
            final Map chartM = (Map) this.chartList.get(i);
            String day = chartM.get("serviceDate").toString();
            day = day.substring(8);
            chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
            tempList.add(chartM);
        }
        this.msg = this.getText("sundyn.analyse.info", new String[] { num });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseContentRate() {
        return "success";
    }
    
    public String analyseContentRateAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.endDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        final List totalList = this.analyseService.AnalyseTotal(this.startDate, this.endDate, allId, type, this.getDeptIds());
        final String contentId = this.getContentId();
        final List contentList = this.analyseService.AnalyseContent(this.startDate, this.endDate, contentId, type, this.getDeptIds());
        this.chartList = this.rate(contentList, totalList);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseContentRateAjaxDay() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String num = request.getParameter("num");
        final Integer numI = Integer.valueOf(num);
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.endDate = df.format(new Date());
        final Calendar cal = Calendar.getInstance();
        cal.set(6, cal.get(6) - numI);
        this.startDate = df.format(cal.getTime());
        final String allId = this.getAllId();
        final List totalList = this.analyseService.AnalyseTotal(this.startDate, this.endDate, allId, "day", this.getDeptIds());
        final String contentId = this.getContentId();
        final List contentList = this.analyseService.AnalyseContent(this.startDate, this.endDate, contentId, "day", this.getDeptIds());
        this.chartList = this.rate(contentList, totalList);
        final List tempList = new ArrayList();
        for (int i = 0; i < this.chartList.size(); ++i) {
            final Map chartM = (Map) this.chartList.get(i);
            String day = chartM.get("serviceDate").toString();
            day = day.substring(8);
            chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
            tempList.add(chartM);
        }
        this.msg = this.getText("sundyn.analyse.info", new String[] { num });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseDept() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final List deptList = new ArrayList();
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        deptList.add(this.deptService.findDeptById(Integer.valueOf(deptIdGroup)));
        request.setAttribute("deptList", (Object)deptList);
        return "success";
    }
    
    public String analyseDeptAjax123() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        final String deptIds = this.deptService.findChildALLStr123(deptId);
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        this.chartList = this.analyseService.AnalyseDeptTotal(deptIds, this.startDate, this.endDate, allId, type);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseDeptContentAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        final String deptIds = this.deptService.findChildALLStr123(deptId);
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String contentId = this.getContentId();
        this.chartList = this.analyseService.AnalyseDeptContent(deptIds, this.startDate, this.endDate, contentId, type);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseDeptContentRateAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        final String deptIds = this.deptService.findChildALLStr123(deptId);
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        final List totalList = this.analyseService.AnalyseDeptTotal(deptIds, this.startDate, this.endDate, allId, type);
        final String contentId = this.getContentId();
        final List contentList = this.analyseService.AnalyseDeptContent(deptIds, this.startDate, this.endDate, contentId, type);
        this.chartList = this.rate(contentList, totalList);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseEmployee() throws Exception {
        return "success";
    }
    
    public String analyseEmployeeAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String employeeId = request.getParameter("employeeId");
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        this.chartList = this.analyseService.AnalyseEmployeeTotal(employeeId, this.startDate, this.endDate, allId, type);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseEmployeeContentAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String employeeId = request.getParameter("employeeId");
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String contentId = this.getContentId();
        this.chartList = this.analyseService.AnalyseEmployeeContent(employeeId, this.startDate, this.endDate, contentId, type);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseEmployeeContentRateAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String employeeId = request.getParameter("employeeId");
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        final List totalList = this.analyseService.AnalyseEmployeeTotal(employeeId, this.startDate, this.endDate, allId, type);
        final String contentId = this.getContentId();
        final List contentList = this.analyseService.AnalyseEmployeeContent(employeeId, this.startDate, this.endDate, contentId, type);
        this.chartList = this.rate(contentList, totalList);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseTotal() {
        return "success";
    }
    
    public String analyseTotalAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.endDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        this.chartList = this.analyseService.AnalyseTotal(this.startDate, this.endDate, allId, type, this.getDeptIds());
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseTotalAjaxDay() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String num = request.getParameter("num");
        final Integer numI = Integer.valueOf(num);
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.endDate = df.format(new Date());
        final Calendar cal = Calendar.getInstance();
        cal.set(6, cal.get(6) - numI);
        this.startDate = df.format(cal.getTime());
        final String allId = this.getAllId();
        this.chartList = this.analyseService.AnalyseTotal(this.startDate, this.endDate, allId, "day", this.getDeptIds());
        final List tempList = new ArrayList();
        for (int i = 0; i < this.chartList.size(); ++i) {
            final Map chartM = (Map) this.chartList.get(i);
            String day = chartM.get("serviceDate").toString();
            day = day.substring(8);
            chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
            tempList.add(chartM);
        }
        this.msg = this.getText("sundyn.analyse.info", new String[] { num });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    private String getAllId() throws Exception {
        this.k7 = this.isK7();
        this.mls = this.keyTypeService.findByApprieserId(1, 1, "on");
        this.bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        String result = "";
        if (this.k7) {
            for (int j = 0; j < this.mls.size(); ++j) {
                final Map k = (Map) this.mls.get(j);
                if (!k.get("keyNo").toString().equals("6")) {
                    result = String.valueOf(result) + k.get("keyNo").toString() + ",";
                }
            }
            for (int j = 0; j < this.bmls.size(); ++j) {
                final Map k = (Map) this.bmls.get(j);
                if (!k.get("keyNo").toString().equals("6")) {
                    result = String.valueOf(result) + k.get("keyNo").toString() + ",";
                }
            }
        }
        else {
            for (int j = 0; j < this.mls.size(); ++j) {
                final Map k = (Map) this.mls.get(j);
                result = String.valueOf(result) + k.get("keyNo").toString() + ",";
            }
            for (int j = 0; j < this.bmls.size(); ++j) {
                final Map k = (Map) this.bmls.get(j);
                result = String.valueOf(result) + k.get("keyNo").toString() + ",";
            }
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        if (result.startsWith(",")) {
            result = result.substring(1, result.length() - 1);
        }
        return result;
    }
    
    public AnalyseService getAnalyseService() {
        return this.analyseService;
    }
    
    public List getBmls() {
        return this.bmls;
    }
    
    public List getChartList() {
        return this.chartList;
    }
    
    private String getContentId() throws Exception {
        this.k7 = this.isK7();
        this.mls = this.keyTypeService.findByApprieserId(1, 1, "on");
        String result = "";
        if (this.k7) {
            for (int j = 0; j < this.mls.size(); ++j) {
                final Map k = (Map) this.mls.get(j);
                if (!k.get("keyNo").toString().equals("6")) {
                    result = String.valueOf(result) + k.get("keyNo").toString() + ",";
                }
            }
        }
        else {
            for (int j = 0; j < this.mls.size(); ++j) {
                final Map k = (Map) this.mls.get(j);
                result = String.valueOf(result) + k.get("keyNo").toString() + ",";
            }
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public String getEndDate() {
        return this.endDate;
    }
    
    public KeyTypeService getKeyTypeService() {
        return this.keyTypeService;
    }
    
    public List getMls() {
        return this.mls;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    private String getNoContentId() throws Exception {
        this.k7 = this.isK7();
        this.bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        String result = "";
        if (this.k7) {
            for (int j = 0; j < this.bmls.size(); ++j) {
                final Map k = (Map) this.bmls.get(j);
                if (!k.get("keyNo").toString().equals("6")) {
                    result = String.valueOf(result) + k.get("keyNo").toString() + ",";
                }
            }
        }
        else {
            for (int j = 0; j < this.bmls.size(); ++j) {
                final Map k = (Map) this.bmls.get(j);
                result = String.valueOf(result) + k.get("keyNo").toString() + ",";
            }
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
    
    public PowerService getPowerService() {
        return this.powerService;
    }
    
    public String getStartDate() {
        return this.startDate;
    }
    
    public String getType() {
        return this.type;
    }
    
    public boolean isK7() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            this.k7 = Boolean.valueOf(sundynSet.getM_system().get("k7").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.k7;
    }
    
    public List rate(final List contentList, final List totalList) {
        final List rsList = new ArrayList();
        boolean flag = true;
        for (int i = 0; i < totalList.size(); ++i) {
            flag = true;
            final Map chart = new HashMap();
            final Map total = (Map) totalList.get(i);
            final Integer totalnum = Integer.valueOf(total.get("num").toString());
            final String serviceDate = total.get("serviceDate").toString();
            for (int j = 0; j < contentList.size(); ++j) {
                final Map content = (Map) contentList.get(j);
                if (serviceDate.equals(content.get("serviceDate").toString())) {
                    final Integer contentNum = Integer.valueOf(content.get("num").toString());
                    double num = contentNum * 1.0 / (totalnum * 1.0);
                    num = Math.rint(num * 10000.0) / 100.0;
                    chart.put("num", num);
                    chart.put("serviceDate", serviceDate);
                    rsList.add(chart);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                chart.put("num", 0);
                chart.put("serviceDate", serviceDate);
                rsList.add(chart);
            }
        }
        return rsList;
    }
    
    public void setAnalyseService(final AnalyseService analyseService) {
        this.analyseService = analyseService;
    }
    
    public void setBmls(final List bmls) {
        this.bmls = bmls;
    }
    
    public void setChartList(final List chartList) {
        this.chartList = chartList;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    
    public void setK7(final boolean k7) {
        this.k7 = k7;
    }
    
    public void setKeyTypeService(final KeyTypeService keyTypeService) {
        this.keyTypeService = keyTypeService;
    }
    
    public void setMls(final List mls) {
        this.mls = mls;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
    
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public String getDeptIds() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String ids = this.deptService.findChildALLStr123(deptIdGroup);
        return ids;
    }
    
    public String analyseContentD() {
        return "success";
    }
    
    public String analyseContentDAjaxDay() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String num = request.getParameter("num");
        final Integer numI = Integer.valueOf(num);
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.endDate = df.format(new Date());
        final Calendar cal = Calendar.getInstance();
        cal.set(6, cal.get(6) - numI);
        this.startDate = df.format(cal.getTime());
        final String ids = this.deptService.findChildALLStr123("-1");
        this.chartList = this.analyseService.AnalyseContentD(this.startDate, this.endDate, "0,1,2,3,4", "day", ids);
        final List tempList = new ArrayList();
        for (int i = 0; i < this.chartList.size(); ++i) {
            final Map chartM = (Map) this.chartList.get(i);
            String day = chartM.get("serviceDate").toString();
            final String n = chartM.get("num").toString();
            day = day.substring(8);
            chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
            chartM.put("num", Double.valueOf(n) * 20.0);
            tempList.add(chartM);
        }
        this.msg = this.getText("sundyn.analyse.info", new String[] { num });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseContentDAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String type = request.getParameter("type");
        final String ids = this.deptService.findChildALLStr123("-1");
        this.chartList = this.analyseService.AnalyseContentD(this.startDate, this.endDate, "0,1,2,3,4", type, ids);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                final String n = chartM.get("num").toString();
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                chartM.put("num", Double.valueOf(n) * 20.0);
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                final String n = chartM.get("num").toString();
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                chartM.put("num", Double.valueOf(n) * 20.0);
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                final String n = chartM.get("num").toString();
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                chartM.put("num", Double.valueOf(n) * 20.0);
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String aa() {
        return "success";
    }
    
    public String analyseSection() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        List deptList = new ArrayList();
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        deptList = this.deptService.getAllSection(this.deptService.findChildALLStr123(deptIdGroup));
        request.setAttribute("deptList", (Object)deptList);
        return "success";
    }
    
    public String analyseSectionAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String sectionName = request.getParameter("sectionName");
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        this.chartList = this.analyseService.AnalyseSectionTotal(sectionName, this.startDate, this.endDate, allId, type);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseSectionContentAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String sectionName = request.getParameter("sectionName");
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String contentId = this.getContentId();
        this.chartList = this.analyseService.AnalyseSectionContent(sectionName, this.startDate, this.endDate, contentId, type);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String analyseSectionContentRateAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String sectionName = request.getParameter("sectionName");
        String type = request.getParameter("type");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        final List totalList = this.analyseService.AnalyseSectionTotal(sectionName, this.startDate, this.endDate, allId, type);
        final String contentId = this.getContentId();
        final List contentList = this.analyseService.AnalyseSectionContent(sectionName, this.startDate, this.endDate, contentId, type);
        this.chartList = this.rate(contentList, totalList);
        final List tempList = new ArrayList();
        if (type.equals("day")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(8);
                chartM.put("serviceDate", String.valueOf(day) + this.getText("sundyn.analyse.day2"));
                tempList.add(chartM);
            }
        }
        else if (type.equals("month")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                chartM.put("serviceDate", String.valueOf(day) + "\u6708");
                tempList.add(chartM);
            }
        }
        else {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(0, 4);
                chartM.put("serviceDate", String.valueOf(day) + "\u5e74");
                tempList.add(chartM);
            }
        }
        this.msg = this.getText("sundyn.analyse.info2", new String[] { this.startDate, this.endDate });
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("list", tempList);
        m.put("msg", this.msg);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }
}
