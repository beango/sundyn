package com.sundyn.action;

import com.sundyn.service.*;
import com.sundyn.util.ColorHelper;
import com.sundyn.util.DateHelper;
import com.sundyn.util.SundynSet;
import com.xuan.xutils.utils.StringUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AnalyseAction extends MainAction
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

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private EmployeeService employeeService;

    public List getEmployeeTreeData() {
        return employeeTreeData;
    }

    public void setEmployeeTreeData(List employeeTreeData) {
        this.employeeTreeData = employeeTreeData;
    }

    private List employeeTreeData;

    public List getDeptJSON() {
        return deptJSON;
    }

    public void setDeptJSON(List deptJSON) {
        this.deptJSON = deptJSON;
    }

    private List deptJSON;
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public void getDeptTree(int deep) throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager2 = (Map)request.getSession().getAttribute("manager");
        final Integer groupid2 = Integer.valueOf(manager2.get("userGroupId").toString());
        final Map power2 = this.powerService.getUserGroup(groupid2);
        final String deptIdGroup2 = power2.get("deptIdGroup").toString();
        this.deptJSON = this.deptService.findChildALL(deptIdGroup2, deep);
    }

    public QueryService getQueryService() {
        return queryService;
    }

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    private QueryService queryService;
    private String startDate;
    private String type;

    public String analyseContent() {
        return "success";
    }

    public String analyseContentAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        String deptId = req.getString("deptId");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        final String contentId = this.getContentId();
        String filterDeptids = super.DEPTIDS;
        if (StringUtils.isNotBlank(deptId)){
            filterDeptids = this.deptService.findChildALLStr1234(deptId);//low
        }
        this.chartList = this.analyseService.AnalyseTotalExRpt(this.startDate, this.endDate, contentId, type, filterDeptids,"totalkeymy");
        final StringBuilder strXML1 = new StringBuilder("");
        //strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        //strXML1.append("<graph caption='满意量走势分析' xAxisName='日期' yAxisName='满意量' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'>");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='满意量走势分析' xAxisName='日期' yAxisName='满意量' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'>");

        if (type.equals("day")) {
            strXML1.append(mergeCategory(1));
            strXML1.append(mergeDataSet(1));
        }
        else if (type.equals("month")) {
            strXML1.append(mergeCategory(2));
            strXML1.append(mergeDataSet(2));
        }
        else {
            strXML1.append(mergeCategory(3));
            strXML1.append(mergeDataSet(3));
        }
        /*if (type.equals("day")) {
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            Date start = df.parse(startDate);
            Date end = df.parse(endDate);
            Calendar s = Calendar.getInstance();
            s.setTime(df.parse(startDate));
            Calendar e = Calendar.getInstance();
            e.setTime(df.parse(endDate));

            while(start.getTime()<=end.getTime()){
                boolean iscontain = false;
                for (int i = 0; i < this.chartList.size(); ++i) {
                    final Map chartM = (Map) this.chartList.get(i);
                    String day = chartM.get("serviceDate").toString();
                    if (df.format(start).equals(day)){
                        strXML1.append("<set name='" + day + "' value='" + chartM.get("num") + "' color='" + ColorHelper.getColor() + "' />");
                        iscontain = true;
                    }

                }
                if(!iscontain){
                    strXML1.append("<set name='" + df.format(start) + "' value='0' color='" + ColorHelper.getColor() + "' />");
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(start);
                cal.add(Calendar.DATE,1);

                start = cal.getTime();
            }
        }
        else if (type.equals("month")) {
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");
            Date start = df.parse(startDate);
            Date end = df.parse(endDate);
            Calendar s = Calendar.getInstance();
            s.setTime(df.parse(startDate));
            Calendar e = Calendar.getInstance();
            e.setTime(df.parse(endDate));

            while(start.getTime()<=end.getTime()){
                boolean iscontain = false;
                for (int i = 0; i < this.chartList.size(); ++i) {
                    final Map chartM = (Map) this.chartList.get(i);
                    String day = chartM.get("serviceDate").toString();
                    if (df.format(start).equals(day)){
                        strXML1.append("<set name='" + day + "' value='" + chartM.get("num") + "' color='" + ColorHelper.getColor() + "' />");
                        iscontain = true;
                    }

                }
                if(!iscontain){
                    strXML1.append("<set name='" + df.format(start) + "' value='0' color='" + ColorHelper.getColor() + "' />");
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(start);
                cal.add(Calendar.MONTH,1);

                start = cal.getTime();
            }
        }
        else if (type.equals("JiDu")) {
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                day = day.substring(5, 7);
                strXML1.append("<set name='" + String.valueOf(day) + "\u5b63\u5ea6" + "' value='" + chartM.get("num") + "' color='" + ColorHelper.getColor() + "' />");
            }
        }
        else {
            SimpleDateFormat df=new SimpleDateFormat("yyyy");
            Date start = df.parse(startDate);
            Date end = df.parse(endDate);
            Calendar s = Calendar.getInstance();
            s.setTime(df.parse(startDate));
            Calendar e = Calendar.getInstance();
            e.setTime(df.parse(endDate));

            while(start.getTime()<=end.getTime()){
                boolean iscontain = false;
                for (int i = 0; i < this.chartList.size(); ++i) {
                    final Map chartM = (Map) this.chartList.get(i);
                    String day = chartM.get("serviceDate").toString();
                    if (df.format(start).equals(day)){
                        strXML1.append("<set name='" + day + "' value='" + chartM.get("num") + "' color='" + ColorHelper.getColor() + "' />");
                        iscontain = true;
                    }

                }
                if(!iscontain){
                    strXML1.append("<set name='" + df.format(start) + "' value='0' color='" + ColorHelper.getColor() + "' />");
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(start);
                cal.add(Calendar.YEAR,1);

                start = cal.getTime();
            }
        }*/
        strXML1.append("</graph>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("w", request.getParameter("w"));
        request.setAttribute("strXMLType", "MSLine.swf");
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
        this.chartList = this.analyseService.AnalyseContent(this.startDate, this.endDate, contentId, "day", super.DEPTIDS);
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
        String deptId = req.getString("deptId");
        startDate = request.getParameter("startDate");
        endDate = request.getParameter("endDate");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        String filterDeptids = super.DEPTIDS;
        if (StringUtils.isNotBlank(deptId)){
            filterDeptids = this.deptService.findChildALLStr1234(deptId);//low
        }
        chartList = this.analyseService.AnalyseTotalExRpt(this.startDate, this.endDate, allId, type, filterDeptids, "totalmyd");
        for (Object item: chartList)
        {
            Map m = (Map)item;
            Object d = m.get("d");
            if(d!=null)
                m.put("num", Math.floor(Double.valueOf(d.toString())));
            else
                m.put("num",0);
        }
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='满意率走势分析' xAxisName='日期' yAxisName='满意率' baseFontSize='12' rotateYAxisName='1' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0' slantLabels='1' labelDisplay='ROTATE' rotateNames='1' decimals='2'>");

        if (type.equals("day")) {
            //strXML1.append(mergeData(1));
            strXML1.append(mergeCategory(1));
            strXML1.append(mergeDataSet(1));
        }
        else if (type.equals("month")) {
            //strXML1.append(mergeData(2));
            strXML1.append(mergeCategory(2));
            strXML1.append(mergeDataSet(2));
        }
        else {
            //strXML1.append(mergeData(3));
            strXML1.append(mergeCategory(3));
            strXML1.append(mergeDataSet(3));
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("strXMLType", "MSLine.swf");//占比
        return "success";
    }

    public String analyseContentRateAjaxAll() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        String deptId = req.getString("deptId");
        startDate = request.getParameter("startDate");
        endDate = request.getParameter("endDate");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        String filterDeptids = super.DEPTIDS;
        if (StringUtils.isNotBlank(deptId)){
            filterDeptids = this.deptService.findChildALLStr1234(deptId);//low
        }
        chartList = this.analyseService.AnalyseTotalEx2(this.startDate, this.endDate, allId, type, filterDeptids);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<chart caption='综合数据分析' xAxisName='日期' yAxisName='满意率' baseFontSize='12' rotateYAxisName='1' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0' slantLabels='1' labelDisplay='ROTATE' rotateNames='1' decimals='2'>");
        strXML1.append(mergeCategory2(3));
        strXML1.append(mergeDataSet2(3));
        strXML1.append("</chart>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("strXMLType", "MSColumn3D.swf");//占比
        return "success";
    }

    public String analyseContentRateAjaxDay() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String num = request.getParameter("num");
        final Integer numI = Integer.valueOf(num);
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, c.get(Calendar.DATE));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        this.endDate = df2.format(c.getTime());
        final Calendar cal = Calendar.getInstance();
        cal.set(6, cal.get(6) - numI);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        this.startDate = df2.format(cal.getTime());
        final String allId = this.getAllId();
        final List totalList = this.analyseService.AnalyseTotal(this.startDate, this.endDate, allId, "day", super.DEPTIDS);
        final String contentId = this.getContentId();
        final List contentList = this.analyseService.AnalyseContent(this.startDate, this.endDate, contentId, "day", super.DEPTIDS);
        this.chartList = this.rate(contentList, totalList);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='满意率柱状图' subCaption='"+this.startDate +" 至 "+ this.endDate+"' xAxisName='日期' yAxisName='满意率' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'>");

        Date start = df.parse(startDate);
        Date end = df.parse(endDate);
        Calendar s = Calendar.getInstance();
        s.setTime(df.parse(startDate));
        Calendar e = Calendar.getInstance();
        e.setTime(df.parse(endDate));

        while(start.getTime()<=end.getTime()){
            boolean iscontain = false;
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                if (df.format(start).equals(day)){
                    strXML1.append("<set name='" + day + "' value='" + chartM.get("num") + "' color='" + ColorHelper.getColor() + "' />");
                    iscontain = true;
                }

            }
            if(!iscontain){
                strXML1.append("<set name='" + df.format(start) + "' value='0' color='" + ColorHelper.getColor() + "' />");
            }
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(start);
            cal2.add(Calendar.DATE,1);

            start = cal2.getTime();
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("strXMLType", "Column3D.swf");
        return "success";
    }

    public String analyseResultRateAjaxDay() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String num = request.getParameter("num");
        final Integer numI = Integer.valueOf(num);
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.endDate = df2.format(new Date());
        final Calendar cal = Calendar.getInstance();
        cal.set(6, cal.get(6) - numI);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        this.startDate = df2.format(cal.getTime());

        cal.setTime(new Date());
        cal.set(Calendar.DATE, cal.get(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        this.endDate = df2.format(cal.getTime());

        final String allId = this.getAllId();
        final List totalList = this.analyseService.AnalyseTotal(this.startDate, this.endDate, allId, "day", super.DEPTIDS);
        final String contentId = this.getContentId();
        final List contentList = this.analyseService.AnalyseContent(this.startDate, this.endDate, contentId, "day", super.DEPTIDS);
        this.chartList = this.rate(contentList, totalList);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.appriesDataDiagram") + "' subCaption='"+this.startDate +" 至 "+ this.endDate+"' xAxisName='\u540d\u79f0' yAxisName='AAAA\u91cf' baseFontSize='14' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0'>");
        String keys = request.getParameter("keys");
        final List chatList = this.queryService.QueryResultChat(null, keys, this.startDate, this.endDate, null);
        for (int i = 0; i < chatList.size(); ++i) {
            final Map temp = (Map) chatList.get(i);
            final Map m = new HashMap();
            m.put("name", temp.get("name"));
            m.put("num", temp.get("num"));
            strXML1.append("<set name='" + temp.get("name") + "' value='" + temp.get("num") + "' color='" + ColorHelper.getColor() + "' />");
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        request.setAttribute("strXMLType", "Pie3D.swf");
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

        getDeptTree(999);

        return "success";
    }

    public String analyseDeptAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String deptId = request.getParameter("deptId");
        String deptIds = this.deptService.findChildALLStr1234(deptId);
        String filterDeptids = this.deptService.findChildALLStr1234(super.DEPTIDS);
        if (StringUtils.isNotBlank(deptId)){
            filterDeptids = this.deptService.findChildALLStr1234(deptId);//low
        }

        String type = request.getParameter("type");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        String field = req.getString("field");
        this.chartList = this.analyseService.AnalyseDeptTotalRpt(filterDeptids, null, this.startDate, this.endDate, allId, type, field);
        final StringBuilder strXML1 = new StringBuilder("");
        String chartTitle = "";
        String yName = "";
        switch (req.getString("field")){
            case "servicecount":
                chartTitle = "业务量分析";
                yName = "业务量";
                break;
            case "cancelcount":
                chartTitle = "弃号量分析";
                yName = "弃号量";
                break;
            case "totalwaittime":
                chartTitle = "平均等待时间分析";
                yName = "平均等待时间";
                break;
            case "totalservicetime":
                chartTitle = "平均办理时间分析";
                yName = "平均办理时间";
                break;
            case "totalkeymy":
                chartTitle = "满意额分析";
                yName = "满意额";
                break;
            case "totalmyd":
                chartTitle = "满意度分析";
                yName = "满意度";
                break;
        }
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='"+chartTitle+"' xAxisName='日期' yAxisName='"+yName+"' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'");
        if(req.getString("field").equals("totalwaittime") || req.getString("field").equals("totalservicetime"))
            strXML1.append(" formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'");
        else
            strXML1.append(" formatNumberScale='0' decimalPrecision='0'");
        strXML1.append(">");

        if (type.equals("day")) {
            strXML1.append(mergeCategory(1));
            strXML1.append(mergeDataSet(1));
        }
        else if (type.equals("month")) {
            strXML1.append(mergeCategory(2));
            strXML1.append(mergeDataSet(2));
        }
        else {
            strXML1.append(mergeCategory(3));
            strXML1.append(mergeDataSet(3));
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("w", request.getParameter("w"));
        request.setAttribute("strXMLType", "MSLine.swf");
        return "success";
    }

    public String analyseDeptContentAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        final String deptIds = this.deptService.findChildALLStr1234(deptId);
        String type = request.getParameter("type");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        final String contentId = this.getContentId();
        this.chartList = this.analyseService.AnalyseDeptContent(deptIds, this.startDate, this.endDate, contentId, type);
        final StringBuilder strXML1 = new StringBuilder("");
        String chartTitle = "";
        String yName = "";
        switch (req.getString("field")){
            case "servicecount":
                chartTitle = "业务量分析";
                yName = "业务量";
                break;
            case "cancelcount":
                chartTitle = "弃号量分析";
                yName = "弃号量";
                break;
            case "totalwaittime":
                chartTitle = "平均等待时间分析";
                yName = "平均等待时间";
                break;
            case "totalservicetime":
                chartTitle = "平均办理时间分析";
                yName = "平均办理时间";
                break;
            case "totalkeymy":
                chartTitle = "满意额分析";
                yName = "满意额";
                break;
            case "totalmyd":
                chartTitle = "满意度分析";
                yName = "满意度";
                break;
        }
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='"+chartTitle+"' xAxisName='时间' yAxisName='"+yName+"' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'");
        if(req.getString("field").equals("totalwaittime") || req.getString("field").equals("totalservicetime"))
            strXML1.append(" formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'");
        else
            strXML1.append(" formatNumberScale='0' decimalPrecision='0'");
        strXML1.append(">");
        if (type.equals("day")) {
            strXML1.append(mergeCategory(1));
            strXML1.append(mergeDataSet(1));
        }
        else if (type.equals("month")) {
            strXML1.append(mergeCategory(2));
            strXML1.append(mergeDataSet(2));
        }
        else {
            strXML1.append(mergeCategory(3));
            strXML1.append(mergeDataSet(3));
        }
        strXML1.append("</graph>");
        request.setAttribute("w", request.getParameter("w"));
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("strXMLType", "MSLine.swf");
        return "success";
    }

    public String analyseDeptContentRateAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        final String deptIds = this.deptService.findChildALLStr1234(deptId);
        String type = request.getParameter("type");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        final List totalList = this.analyseService.AnalyseDeptTotal(deptIds,null, this.startDate, this.endDate, allId, type);
        //final String contentId = this.getContentId();
        //final List contentList = this.analyseService.AnalyseDeptContent(deptIds, this.startDate, this.endDate, contentId, type);
        this.chartList = totalList;//this.rate(contentList, totalList);
        for (Object item: chartList)
        {
            Map m = (Map)item;
            Object d = m.get("d");
            if(d!=null)
                m.put("num", Math.floor(Double.valueOf(d.toString())));
            else
                m.put("num",0);
        }
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='窗口满意度分析' xAxisName='日期' yAxisName='满意度' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'>");

        if (type.equals("day")) {
            strXML1.append(mergeCategory(1));
            strXML1.append(mergeDataSet(1));
        }
        else if (type.equals("month")) {
            strXML1.append(mergeCategory(2));
            strXML1.append(mergeDataSet(2));
        }
        else {
            strXML1.append(mergeCategory(3));
            strXML1.append(mergeDataSet(3));
        }
        strXML1.append("</graph>");
        request.setAttribute("w", request.getParameter("w"));
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("strXMLType", "MSLine.swf");
        return "success";
    }

    public String analyseEmployee() throws Exception {
        getEmployeeTree();
        return "success";
    }
    public void getEmployeeTree() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager2 = (Map)request.getSession().getAttribute("manager");
        final Integer groupid2 = Integer.valueOf(manager2.get("userGroupId").toString());
        final Map power2 = this.powerService.getUserGroup(groupid2);
        final String deptIdGroup2 = power2.get("deptIdGroup").toString();
        this.deptJSON = this.deptService.findChildALL(deptIdGroup2, 2);

        final List temp = new ArrayList();
        for (int i = 0; i < this.deptJSON.size(); ++i) {
            Map map = (Map)deptJSON.get(i);
            temp.addAll(this.employeeService.findEmployeeByDeptId(Integer.valueOf(map.get("id").toString())));
        }
        employeeTreeData = temp;
    }
    public String analyseEmployeeAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String employeeId = request.getParameter("employeeId");
        String deptid= req.getString("deptid");
        String type = request.getParameter("type");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        //getEmployeeTree();

        final String keyno = this.getAllId();
        String deptIds = this.deptService.findChildALLStr1234(super.DEPTIDS);
        if (StringUtils.isNotBlank(deptid)) {
            deptIds = this.deptService.findChildALLStr1234(deptid);
            employeeId = this.employeeService.findEmployeeByDeptId(deptid);
        }
        this.chartList = this.analyseService.AnalyseEmployeeTotalRpt(deptIds, employeeId, this.startDate, this.endDate, keyno, type, req.getString("field"));
        final StringBuilder strXML1 = new StringBuilder("");
        String chartTitle = "";
        String yName = "";
        switch (req.getString("field")){
            case "servicecount":
                chartTitle = "业务量分析";
                yName = "业务量";
                break;
            case "cancelcount":
                chartTitle = "弃号量分析";
                yName = "弃号量";
                break;
            case "totalwaittime":
                chartTitle = "平均等待时间分析";
                yName = "平均等待时间";
                break;
            case "totalservicetime":
                chartTitle = "平均办理时间分析";
                yName = "平均办理时间";
                break;
            case "totalkeymy":
                chartTitle = "满意额分析";
                yName = "满意额";
                break;
            case "totalmyd":
                chartTitle = "满意度分析";
                yName = "满意度";
                break;
        }
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='"+chartTitle+"' xAxisName='时间' yAxisName='"+yName+"' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'");
        if(req.getString("field").equals("totalwaittime") || req.getString("field").equals("totalservicetime"))
            strXML1.append(" formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'");
        else
            strXML1.append(" formatNumberScale='0' decimalPrecision='0'");
        strXML1.append(">");

        if (type.equals("day")) {
            strXML1.append(mergeCategory(1));
            strXML1.append(mergeDataSet(1));
        }
        else if (type.equals("month")) {
            strXML1.append(mergeCategory(2));
            strXML1.append(mergeDataSet(2));
        }
        else {
            strXML1.append(mergeCategory(3));
            strXML1.append(mergeDataSet(3));
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("w", request.getParameter("w"));
        request.setAttribute("strXMLType", "MSLine.swf");//占比
        return "success";
    }

    public String analyseEmployeeContentAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String employeeId = request.getParameter("employeeId");
        String deptid = req.getString("deptid");
        String type = request.getParameter("type");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        final String contentId = this.getContentId();
        String deptIds = this.deptService.findChildALLStr1234(super.DEPTIDS);
        if (StringUtils.isNotBlank(deptid)) {
            deptIds = this.deptService.findChildALLStr1234(deptid);
            employeeId = this.employeeService.findEmployeeByDeptId(deptid);
        }
        this.chartList = this.analyseService.AnalyseEmployeeContent2(deptIds, employeeId, this.startDate, this.endDate, contentId, type);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='满意额分析' xAxisName='日期' yAxisName='满意额' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'>");

        if (type.equals("day")) {
            strXML1.append(mergeCategory(1));
            strXML1.append(mergeDataSet(1));
        }
        else if (type.equals("month")) {
            strXML1.append(mergeCategory(2));
            strXML1.append(mergeDataSet(2));
        }
        else {
            strXML1.append(mergeCategory(3));
            strXML1.append(mergeDataSet(3));
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("w", request.getParameter("w"));
        request.setAttribute("strXMLType", "MSLine.swf");//占比
        return "success";
    }

    public StringBuilder mergeCategory(int fmt) throws ParseException {
        StringBuilder strXML1 = new StringBuilder();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if (fmt==2)
            df=new SimpleDateFormat("yyyy-MM");
        if (fmt==3)
            df=new SimpleDateFormat("yyyy");
        Date start = df.parse(startDate);
        Date end = df.parse(endDate);
        Calendar s = Calendar.getInstance();
        s.setTime(df.parse(startDate));
        Calendar e = Calendar.getInstance();
        e.setTime(df.parse(endDate));
        strXML1.append("<categories>");

        while(start.getTime()<=end.getTime()){
            strXML1.append("<category label='"+df.format(start)+"' />");
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            if (fmt==1)
                cal.add(Calendar.DATE,1);
            if (fmt==2)
                cal.add(Calendar.MONTH,1);
            if (fmt==3)
                cal.add(Calendar.YEAR,1);

            start = cal.getTime();
        }
        strXML1.append("</categories>");
        return strXML1;
    }
    public StringBuilder mergeCategory2(int fmt) throws ParseException {
        StringBuilder strXML1 = new StringBuilder();
        strXML1.append("<categories>");
        strXML1.append("<category label='业务总量' />");
        strXML1.append("<category label='弃号量' />");
        strXML1.append("<category label='平均等候时长' />");
        strXML1.append("<category label='平均办理时长' />");
        strXML1.append("</categories>");
        return strXML1;
    }
    /*
    1按天,2按月,3按年
     */
    public StringBuilder mergeData(int fmt) throws ParseException {
        StringBuilder strXML1 = new StringBuilder();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if (fmt==2)
            df=new SimpleDateFormat("yyyy-MM");
        if (fmt==3)
            df=new SimpleDateFormat("yyyy");
        Date start = df.parse(startDate);
        Date end = df.parse(endDate);
        Calendar s = Calendar.getInstance();
        s.setTime(df.parse(startDate));
        Calendar e = Calendar.getInstance();
        e.setTime(df.parse(endDate));

        while(start.getTime()<=end.getTime()){
            boolean iscontain = false;
            for (int i = 0; i < this.chartList.size(); ++i) {
                final Map chartM = (Map) this.chartList.get(i);
                String day = chartM.get("serviceDate").toString();
                if (df.format(start).equals(day)){
                    strXML1.append("<set name='" + day + "' value='" + chartM.get("num") + "' color='" + ColorHelper.getColor() + "' />");
                    iscontain = true;
                }
            }
            if(!iscontain){
                strXML1.append("<set name='" + df.format(start) + "' value='0' color='" + ColorHelper.getColor() + "' />");
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            if (fmt==1)
                cal.add(Calendar.DATE,1);
            if (fmt==2)
                cal.add(Calendar.MONTH,1);
            if (fmt==3)
                cal.add(Calendar.YEAR,1);

            start = cal.getTime();
        }
        return strXML1;
    }

    /*
    1按天,2按月,3按年
     */
    public StringBuilder mergeDataSet(int fmt) throws ParseException {
        StringBuilder strXML1 = new StringBuilder();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if (fmt==2)
            df=new SimpleDateFormat("yyyy-MM");
        if (fmt==3)
            df=new SimpleDateFormat("yyyy");

        HashMap<String,String> seriesHashMap = new HashMap<>();
        for (int i = 0; i < this.chartList.size(); ++i) {
            final Map chartM = (Map) this.chartList.get(i);
            String seriesName = chartM.get("seriesName").toString();
            String seriesId = chartM.get("seriesId").toString();
            if (!seriesHashMap.containsKey(seriesId)){
                seriesHashMap.put(seriesId, seriesName);
            }
        }

        for (Map.Entry<String, String> item : seriesHashMap.entrySet()){
            strXML1.append("<dataset seriesName='"+item.getValue()+"' color='"+ColorHelper.getColor()+"' showValues='0'>");
            Date start = df.parse(startDate);
            Date end = df.parse(endDate);
            Calendar s = Calendar.getInstance();
            s.setTime(df.parse(startDate));
            Calendar e = Calendar.getInstance();
            e.setTime(df.parse(endDate));
            while(start.getTime()<=end.getTime()){
                boolean iscontain = false;
                for (int i = 0; i < this.chartList.size(); ++i) {
                    final Map chartM = (Map) this.chartList.get(i);
                    String seriesId = chartM.get("seriesId").toString();
                    String day = String.valueOf(chartM.get("serviceDate"));
                    if (df.format(start).equals(day) && item.getKey().equals(seriesId)){
                        strXML1.append("<set value='" + chartM.get("num") + "' />");
                        iscontain = true;
                    }
                }
                if(!iscontain){
                    strXML1.append("<set value='0' />");
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(start);
                if (fmt==1)
                    cal.add(Calendar.DATE,1);
                if (fmt==2)
                    cal.add(Calendar.MONTH,1);
                if (fmt==3)
                    cal.add(Calendar.YEAR,1);

                start = cal.getTime();
            }
            strXML1.append("</dataset>");
        }
        return strXML1;
    }
    /*
        1按天,2按月,3按年
         */
    public StringBuilder mergeDataSet2(int fmt) throws ParseException {
        StringBuilder strXML1 = new StringBuilder();

        HashMap<String, String> seriesHashMap = new HashMap<>();

        for (int i = 0; i < this.chartList.size(); ++i) {
            final Map chartM = (Map) this.chartList.get(i);
            Object servercount = chartM.get("servercount");
            Object seriesName = chartM.get("seriesName");
            String unkey = chartM.get("unkey").toString();
            String waittimeavg = chartM.get("waittimeavg").toString();
            String servicetimeavg = chartM.get("servicetimeavg").toString();
            strXML1.append(String.format("<dataset seriesName='%s' color='"+ColorHelper.getColor()+"' showValues='1'>", seriesName));
            strXML1.append("<set value='" + servercount + "' />");
            strXML1.append("<set value='" + unkey + "' />");
            strXML1.append("<set value='" + waittimeavg + "' />");
            strXML1.append("<set value='" + servicetimeavg + "' />");
            strXML1.append("</dataset>");
        }

        /*strXML1.append("<dataset seriesName='弃号量' color='"+ColorHelper.getColor()+"' showValues='1'>");
        for (int i = 0; i < this.chartList.size(); ++i) {
            final Map chartM = (Map) this.chartList.get(i);
            String unkey = chartM.get("unkey").toString();
            strXML1.append("<set value='" + unkey + "' />");
        }
        strXML1.append("</dataset>");
        strXML1.append("<dataset seriesName='平均等候时长' color='"+ColorHelper.getColor()+"' showValues='1'>");
        for (int i = 0; i < this.chartList.size(); ++i) {
            final Map chartM = (Map) this.chartList.get(i);
            String waittimeavg = chartM.get("waittimeavg").toString();
            strXML1.append("<set value='" + waittimeavg + "' />");
        }
        strXML1.append("</dataset>");
        strXML1.append("<dataset seriesName='平均办理时长' color='"+ColorHelper.getColor()+"' showValues='1'>");
        for (int i = 0; i < this.chartList.size(); ++i) {
            final Map chartM = (Map) this.chartList.get(i);
            String servicetimeavg = chartM.get("servicetimeavg").toString();
            strXML1.append("<set value='" + servicetimeavg + "' />");
        }*/
        return strXML1;
    }
    public String analyseEmployeeContentRateAjax() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String employeeId = request.getParameter("employeeId");
        String deptid = req.getString("deptid");
        String type = request.getParameter("type");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        String deptIds = this.deptService.findChildALLStr1234(super.DEPTIDS);
        if (StringUtils.isNotBlank(deptid)) {
            deptIds = this.deptService.findChildALLStr1234(deptid);
            employeeId = this.employeeService.findEmployeeByDeptId(deptid);
        }
        final List totalList = this.analyseService.AnalyseEmployeeTotal(deptIds, employeeId, this.startDate, this.endDate, null, type);
        //final String contentId = this.getContentId();
        //final List contentList = this.analyseService.AnalyseEmployeeContent2(deptIds, employeeId, this.startDate, this.endDate, contentId, type);
        this.chartList = totalList;//this.rate2(contentList, totalList);
        for (Object item: chartList)
        {
            Map m = (Map)item;
            m.put("num", Math.floor(Double.valueOf(String.valueOf(m.get("d")))));
        }
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='满意度分析' xAxisName='日期' yAxisName='满意度' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'>");

        if (type.equals("day")) {
            strXML1.append(mergeCategory(1));
            strXML1.append(mergeDataSet(1));
        }
        else if (type.equals("month")) {
            strXML1.append(mergeCategory(2));
            strXML1.append(mergeDataSet(2));
        }
        else {
            strXML1.append(mergeCategory(3));
            strXML1.append(mergeDataSet(3));
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("w", request.getParameter("w"));
        request.setAttribute("strXMLType", "MSLine.swf");//占比
        return "success";
    }

    public String analyseTotal() {
        return "success";
    }

    public String analyseTotalAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("type");
        String w = request.getParameter("w");
        String deptId = req.getString("deptId");

        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        if (type == null) {
            type = "day";
        }
        final String allId = this.getAllId();
        String filterDeptids = this.deptService.findChildALLStr1234(super.DEPTIDS);
        if (StringUtils.isNotBlank(deptId)){
            filterDeptids = this.deptService.findChildALLStr1234(deptId);//low
        }
        this.chartList = this.analyseService.AnalyseTotalExRpt(this.startDate, this.endDate, allId, type, filterDeptids, req.getString("field"));
        String chartTitle = "";
        String yName = "";
        switch (req.getString("field")){
            case "servicecount":
                chartTitle = "业务量分析";
                yName = "业务量";
                break;
            case "cancelcount":
                chartTitle = "弃号量分析";
                yName = "弃号量";
                break;
            case "totalwaittime":
                chartTitle = "平均等待时间分析";
                yName = "平均等待时间";
                break;
            case "totalservicetime":
                chartTitle = "平均办理时间分析";
                yName = "平均办理时间";
                break;
            case "totalkeymy":
                chartTitle = "满意额分析";
                yName = "满意额";
                break;
            case "totalmyd":
                chartTitle = "满意度分析";
                yName = "满意度";
                break;
        }

        StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='"+chartTitle+"' xAxisName='时间' yAxisName='"+yName+"' rotateYAxisName='1' baseFontSize='12' rotateYAxisName='1' slantLabels='1' labelDisplay='ROTATE' rotateNames='1'");
        if(req.getString("field").equals("totalwaittime") || req.getString("field").equals("totalservicetime"))
            strXML1.append(" formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'");
        else
            strXML1.append(" formatNumberScale='0' decimalPrecision='0'");
        strXML1.append(">");

        if (type.equals("day")) {
            strXML1.append(mergeCategory(1));
            strXML1.append(mergeDataSet(1));
        }
        else if (type.equals("month")) {
            strXML1.append(mergeCategory(2));
            strXML1.append(mergeDataSet(2));
        }
        else {
            strXML1.append(mergeCategory(3));
            strXML1.append(mergeDataSet(3));
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("w", request.getParameter("w"));
        request.setAttribute("strXMLType", "MSLine.swf");
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
        this.chartList = this.analyseService.AnalyseTotal(this.startDate, this.endDate, allId, "day", super.DEPTIDS);
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
        result += ",7";
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
                    num = Math.floor(num * 10000.0) / 100.0;
                    chart.put("num", num);
                    chart.put("serviceDate", serviceDate);
                    chart.put("seriesId", total.get("seriesId"));
                    chart.put("seriesName", total.get("seriesName"));
                    rsList.add(chart);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                chart.put("num", 0);
                chart.put("serviceDate", serviceDate);
                chart.put("seriesId", total.get("seriesId"));
                chart.put("seriesName", total.get("seriesName"));
                rsList.add(chart);
            }
        }
        return rsList;
    }

    public List rate2(final List contentList, final List totalList) {
        final List rsList = new ArrayList();
        boolean flag = true;
        for (int i = 0; i < totalList.size(); ++i) {
            flag = true;
            final Map chart = new HashMap();
            final Map total = (Map) totalList.get(i);
            final Integer totalnum = Integer.valueOf(total.get("num").toString());
            final String serialId = total.get("seriesId").toString();
            final String serviceDate = total.get("serviceDate").toString();
            for (int j = 0; j < contentList.size(); ++j) {
                final Map content = (Map) contentList.get(j);
                if (serviceDate.equals(content.get("serviceDate").toString()) && serialId.equals(content.get("seriesId").toString())) {
                    final Integer contentNum = Integer.valueOf(content.get("num").toString());
                    double num = contentNum * 1.0 / (totalnum * 1.0);
                    num = Math.floor(num * 10000.0) / 100.0;
                    chart.put("num", num);
                    chart.put("serviceDate", serviceDate);
                    chart.put("seriesId", total.get("seriesId"));
                    chart.put("seriesName", total.get("seriesName"));
                    rsList.add(chart);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                chart.put("num", 0);
                chart.put("serviceDate", serviceDate);
                chart.put("seriesId", total.get("seriesId"));
                chart.put("seriesName", total.get("seriesName"));
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
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null || startDate.equals("")) {
            startDate = dateHelper.getDataString_1(dateHelper.getMonthFirstDate());
        }
        if(endDate == null || endDate.equals("")) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
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
