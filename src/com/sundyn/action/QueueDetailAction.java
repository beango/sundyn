package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.sundyn.entity.QueueDetail;
import com.sundyn.entity.QueueEmployeereport;
import com.sundyn.service.*;
import com.sundyn.utils.NumberUtils;
import com.xuan.xutils.utils.DateUtils;
import com.xuan.xutils.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.*;

public class QueueDetailAction extends MainAction
{
    @Resource
    private IQueueDetailService detailService;
    @Resource
    private ISysQueuehallService hallService;
    @Resource
    private ISysQueueserialService serialService;
    @Resource
    private IQueueEmployeereportService queueEmployeereportService;
    @Resource
    private DeptService deptService;
    @Resource
    private IAppriesKeytypeService keytypeServiceEx;
    @Resource
    private QueryService queryService;
    @Getter
    @Setter
    Map<String,Object> jsonData = new HashMap<String,Object>();
    /*
    查询
     */
    public String queueDetailQuery() throws Exception {
        String key_hallid = req.getString("hallid");
        String key_seriid = req.getString("seriid");
        String key_status = req.getString("status");
        String key_queuetype = req.getString("queuetype");
        String key_countername = req.getString("countername");
        Wrapper<QueueDetail> ew =new EntityWrapper<QueueDetail>();
        if (null!=key_hallid && !"".equals(key_hallid))
            ew = ew.where("hallid={0}", key_hallid);
        if (null!=key_seriid && !"".equals(key_seriid))
            ew = ew.where("bizid={0}", key_seriid);
        if (null!=key_status && !"".equals(key_status))
            ew = ew.where("status={0}", key_status);
        if (null!=key_queuetype && !"".equals(key_queuetype))
            ew = ew.where("queuetype={0}", key_queuetype);
        if (null!=key_countername && !"".equals(key_countername))
            ew = ew.like("countername", key_countername);
        Page<QueueDetail> queryData = detailService.query_querydetail(new Page<QueueDetail>(pageindex, pageSize), ew.orderBy("tickettime desc"));
        String spath = ServletActionContext.getServletContext().getRealPath("/");

        request.setAttribute("hallid", key_hallid);
        request.setAttribute("seriid", key_seriid);
        request.setAttribute("status", key_status);
        request.setAttribute("queuetype", key_queuetype);
        request.setAttribute("queryData", queryData);
        request.setAttribute("hallList", hallService.selectList(null));

        JSONArray ja = new JSONArray();
        for (QueueDetail.QueueTypeEnum ynEnum : QueueDetail.QueueTypeEnum.values()) {
            JSONObject jo = new JSONObject();
            jo.put("id", ynEnum.getCode());
            jo.put("name", ynEnum.getMsg());
            ja.put(jo);
        }
        request.setAttribute("queueTypeList", ja.toString());

        ja = new JSONArray();
        for (QueueDetail.StatusNameEnum ynEnum : QueueDetail.StatusNameEnum.values()) {
            JSONObject jo = new JSONObject();
            jo.put("id", ynEnum.getCode());
            jo.put("name", ynEnum.getMsg());
            ja.put(jo);
        }
        request.setAttribute("statusList", ja.toString());
        return SUCCESS;
    }

    public String queueEmployeeReport(){
        String key_hallid = req.getString("hallid");
        String key_action = req.getString("action");
        String key_ename = req.getString("ename");
        Wrapper<QueueEmployeereport> ew =new EntityWrapper<QueueEmployeereport>();
        if (null!=key_hallid && !"".equals(key_hallid))
            ew = ew.where("hallid={0}", key_hallid);
        if (null!=key_action && !"".equals(key_action))
            ew = ew.where("action={0}", key_action);
        if (null!=key_ename && !"".equals(key_ename))
            ew = ew.like("ename", key_ename);
        Page<QueueEmployeereport> queryData = queueEmployeereportService.report_queueemployee(new Page<QueueEmployeereport>(pageindex, pageSize), ew.orderBy("starttime desc"));
        String spath = ServletActionContext.getServletContext().getRealPath("/");

        request.setAttribute("hallid", key_hallid);
        request.setAttribute("action", key_action);
        request.setAttribute("ename", key_ename);
        request.setAttribute("queryData", queryData);
        request.setAttribute("hallList", hallService.selectList(null));

        JSONArray ja = new JSONArray();
        for (QueueDetail.QueueTypeEnum ynEnum : QueueDetail.QueueTypeEnum.values()) {
            JSONObject jo = new JSONObject();
            jo.put("id", ynEnum.getCode());
            jo.put("name", ynEnum.getMsg());
            ja.put(jo);
        }
        request.setAttribute("queueTypeList", ja.toString());

        ja = new JSONArray();
        for (QueueDetail.StatusNameEnum ynEnum : QueueDetail.StatusNameEnum.values()) {
            JSONObject jo = new JSONObject();
            jo.put("id", ynEnum.getCode());
            jo.put("name", ynEnum.getMsg());
            ja.put(jo);
        }
        request.setAttribute("statusList", ja.toString());
        return SUCCESS;
    }

    public String queuealert(){
        int deptid = req.getInt("deptid");
        if (deptid != 0){
            Map map = deptService.findDeptById(deptid);
            request.setAttribute("deptObj", map);
        }
        else
        {
            Map map = deptService.findDeptById(Integer.valueOf(super.getUserDept()));
            request.setAttribute("deptObj", map);
        }

        return SUCCESS;
    }

    /*
    获取最近10条评价
     */
    public String getlstappries(){
        int count = req.getInt("count",5);
        List data = this.queryService.getLstAppries(count, null);
        request.setAttribute("data", data);
        return SUCCESS;
    }

    public String getQueueTotal(){
        int deptid = req.getInt("deptid", super.getUserDept());
        String filterDeptids = this.deptService.FN_GetDeptAndChild(deptid);
        jsonData.clear();

        List data2 = this.queryService.getQueueEmployeeAnysle(filterDeptids);
        List data2list = new ArrayList();
        Float totalservicetime2 = 0f, totalservicecount = 0f;
        for (Object datum : data2) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalservicetime2"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        Float avg = totalservicetime2 / totalservicecount;
        int intavg = (int)Math.floor(avg);
        for (Object datum : data2) {
            Map m = (Map) datum;
            m.put("avgservicetimename", DateUtils.secToTime(intavg));
            if (m.get("totalservicetime")!=null && (int)Math.floor(StringUtils.toFloat(m.get("totalservicetime"), 0)) > intavg)
                data2list.add(m);
        }
        Collections.sort(data2list, new SortByServiceCount("totalservicetime", "desc"));
        jsonData.put("dataservicetime123", data2list);

        List data2list2 = new ArrayList();
        totalservicetime2 = 0f;
        totalservicecount = 0f;
        for (Object datum : data2) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalkeybmy"), 0);//差评数
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
            m.put("keybmyrate", StringUtils.toFloat(m.get("totalservicetime2"), 0) / StringUtils.toFloat(m.get("totalservicetime2"), 0));
            m.put("keybmyrate", totalservicetime2 / totalservicecount);
        }
        avg = totalservicetime2 * 100.0f / totalservicecount;
        for (Object datum : data2) {
            Map m = (Map) datum;
            m.put("avgtotalkeybmy", String.format("%.2f", avg, 2));
            float rate = StringUtils.toFloat(m.get("keybmyrate"), 0);
            m.put("keybmyrate", String.format("%.2f", rate*100.0f, 2));
            if (rate > avg)
                data2list2.add(m);
        }
        Collections.sort(data2list2, new SortByServiceCount("keybmyrate", "desc"));
        jsonData.put("datakeybmy", data2list2);

        List data2list3 = new ArrayList();
        totalservicetime2 = 0f;
        totalservicecount = 0f;
        for (Object datum : data2) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalpausetime"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        avg = totalservicetime2 / data2.size();
        intavg = (int)Math.floor(avg);
        for (Object datum : data2) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("totalpausetime"), 0);
            m.put("avgpausetimename", DateUtils.secToTime(intavg));
            m.put("totalpausetimename", DateUtils.secToTime((int)Math.floor(f)));
            if ((int)Math.floor(f) > intavg)
                data2list3.add(m);
        }
        Collections.sort(data2list3, new SortByServiceCount("totalpausetime", "desc"));
        jsonData.put("totalpausetime", data2list3);
        return SUCCESS;
    }

    public String getQueueTotalOnce(){
        int deptid = req.getInt("deptid", super.getUserDept());
        String filterDeptids = this.deptService.FN_GetDeptAndChild(deptid);
        //不满意
        List employeeWarn = this.queryService.getEmployeeWarn(filterDeptids);
        Float totalservicecount = 0f;
        for (Object datum : employeeWarn) {
            Map m = (Map) datum;
            totalservicecount += StringUtils.toFloat(m.get("totalkeybmy"), 0);
        }
        Float avg = totalservicecount / employeeWarn.size();
        if (employeeWarn!=null && employeeWarn.size()>0)
            avg = StringUtils.toFloat(((Map)employeeWarn.get(0)).get("avg"), 0);
        List data2list = new ArrayList();
        for (Object datum : employeeWarn) {
            Map m = (Map) datum;
            if (StringUtils.toFloat(m.get("totalkeybmy"), 0) > avg)
                data2list.add(m);
        }
        Collections.sort(data2list, new SortByServiceCount("totalkeybmy", "desc"));
        jsonData.clear();
        jsonData.put("TableMonthDeptBMY", data2list);
        //工作量
        totalservicecount = 0f;
        for (Object datum : employeeWarn) {
            Map m = (Map) datum;
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        avg = totalservicecount / employeeWarn.size();
        data2list = new ArrayList();
        for (Object datum : employeeWarn) {
            Map m = (Map) datum;
            if (Float.compare(StringUtils.toFloat(m.get("servicecount"), 0), avg)==-1) {
                data2list.add(m);
            }
        }
        Collections.sort(data2list, new SortByServiceCount("servicecount", "asc"));
        jsonData.put("employeeservicecount", data2list);
        //满意度
        totalservicecount = 0f;
        float totalmyd = 0f;
        for (Object datum : employeeWarn) {
            Map m = (Map) datum;
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
            totalmyd += StringUtils.toFloat(m.get("totalmyd"), 0);
        }
        avg = (float)Math.round(totalmyd*10.0 / totalservicecount);
        data2list = new ArrayList();
        for (Object datum : employeeWarn) {
            Map m = (Map) datum;
            m.put("totalmyd", (int)Math.round(StringUtils.toFloat(m.get("totalmyd"), 0)*10.0/StringUtils.toFloat(m.get("servicecount"), 0)));
            if (Float.compare(StringUtils.toFloat(m.get("totalmyd"), 0), avg)==-1)
                data2list.add(m);
        }
        Collections.sort(data2list, new SortByServiceCount("totalmyd", "asc"));
        jsonData.put("myd", data2list);
        return SUCCESS;
    }

    class SortByServiceCount implements Comparator {
        private String field;
        private String desc;

        public SortByServiceCount(String field, String desc) {
            this.field = field;
            this.desc = desc;
        }
        public int compare(Object o1, Object o2) {
            Map s1 = (Map) o1;
            Map s2 = (Map) o2;
            int r = 1;
            if (s1.get(field) == null || s2.get(field)==null || !NumberUtils.isNumber(s1.get(field).toString())
                    || !NumberUtils.isNumber(s2.get(field).toString()))
                return -1;
            if (StringUtils.toFloat(s1.get(field), 0) > StringUtils.toFloat(s2.get(field), 0))
                r = desc.equals("desc") ? -1 : 1;
            else
                r = desc.equals("desc") ? 1 : -1;
            return r;
        }
    }

    public String getQueueDeptAnysleAjax() throws SQLException {
        int deptid = req.getInt("deptid", super.getUserDept());
        String filterDeptids = this.deptService.FN_GetDeptAndChild(deptid);

        List data = this.queryService.getQueueDeptAnysle(filterDeptids);
        /*
         * 业务平均办理时间
         */
        StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='当日人流量' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='1' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1' formatNumberScale='1'>");

        Float totalservicetime2 = 0f, totalservicecount = 0f;
        for (Object datum : data) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalservicetime2"), 0f);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0f);
        }
        Float avg = totalservicetime2 / totalservicecount;
        for (Object datum : data) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("ticketcount"), 0);
            String Color = "60AAA3";
            if (f > avg)
                Color = "FF0000";
            strXML1.append("<set name='" + m.get("deptname") + "' value='" + f + "' color='" + Color + "' />");
        }
        strXML1.append("</graph>");
        jsonData.clear();
        jsonData.put("DeptServiceCount", strXML1.toString());

        strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='当前等待人数' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='0' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1'>");

        for (Object datum : data) {
            Map m = (Map) datum;
            Integer f = StringUtils.toInt(m.get("totalwait"), 0);
            String Color = "60AAA3";
            if (f > avg)
                Color = "FF0000";
            strXML1.append("<set name='" + m.get("deptname") + "' value='" + f + "' color='" + Color + "' />");
        }
        strXML1.append("</graph>");
        jsonData.put("DeptWaitCount", strXML1.toString());

        List lst5Appries = this.queryService.getLstAppries(5, filterDeptids);
        jsonData.put("lst5Appries", lst5Appries);

        /*
        * 业务平均办理时间
        */
        strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='服务大厅平均办理时间预警' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='1' rotateNames='1' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1' formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'>");

        totalservicetime2 = 0f; totalservicecount = 0f;
        for (Object datum : data) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalservicetime2"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        avg = totalservicetime2 / totalservicecount;
        for (Object datum : data) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("totalservicetime"), 0);
            String Color = "60AAA3";
            if (f>avg)
                Color="FF0000";
            strXML1.append("<set name='"+m.get("deptname")+"' value='"+f+"' color='" + Color + "' />");
        }
        strXML1.append("<trendLines>");
        strXML1.append("<line startValue='"+ avg +"' showOnTop='1' color='FF0000' displayvalue='平均办理时间' thickness='2' /> ");
        strXML1.append("</trendLines>");
        strXML1.append("</graph>");
        jsonData.put("ChartTodayDeptServiceTime", strXML1.toString());
        /*
         ***********************************************************
         */
        /*
         * 业务平均等待时间
         */
        strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='服务大厅平均等待时间预警' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='1' rotateNames='1' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1' formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'>");

        totalservicetime2 = 0f;
        totalservicecount = 0f;
        for (Object datum : data) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalwaittime2"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        avg = totalservicetime2 / totalservicecount;
        for (Object datum : data) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("totalwaittime"), 0);
            String Color = "60AAA3";
            if (f>avg)
                Color="FF0000";
            strXML1.append("<set name='"+m.get("deptname")+"' value='"+f+"' color='" + Color + "' />");
        }
        strXML1.append("<trendLines>");
        strXML1.append("<line startValue='"+ avg +"' showOnTop='1' color='FF0000' displayvalue='平均等待时间' thickness='2' /> ");
        strXML1.append("</trendLines>");
        strXML1.append("</graph>");
        jsonData.put("ChartTodayDeptWaitTime", strXML1.toString());
        /*
         ***********************************************************
         */
        /*
         * 差评数预警
         */
        strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='服务大厅差评数预警' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='1' rotateNames='1' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1' >");

        totalservicetime2 = 0f;
        totalservicecount = 0f;
        for (Object datum : data) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalkeybmy"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        avg = totalservicetime2 / data.size();
        for (Object datum : data) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("totalkeybmy"), 0);
            String Color = "60AAA3";
            if (f>avg)
                Color="FF0000";
            strXML1.append("<set name='"+m.get("deptname")+"' value='"+f+"' color='" + Color + "' />");
        }
        strXML1.append("<trendLines>");
        strXML1.append("<line startValue='"+ avg +"' showOnTop='1' color='FF0000' displayvalue='差评数' thickness='2' /> ");
        strXML1.append("</trendLines>");
        strXML1.append("</graph>");
        jsonData.put("ChartTodayDeptBMY", strXML1.toString());
        /*
         ***********************************************************
         */
        /*
         * 暂停时长
         */
        strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='服务大厅暂停时长预警' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='1' rotateNames='1' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1' formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'>");

        totalservicetime2 = 0f;
        totalservicecount = 0f;
        for (Object datum : data) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalpausetime"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        avg = totalservicetime2 / data.size();
        for (Object datum : data) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("totalpausetime"), 0);
            String Color = "60AAA3";
            if (f>avg)
                Color="FF0000";
            strXML1.append("<set name='"+m.get("deptname")+"' value='"+f+"' color='" + Color + "' />");
        }
        strXML1.append("<trendLines>");
        strXML1.append("<line startValue='"+ avg +"' showOnTop='1' color='FF0000' displayvalue='暂停时长' thickness='2' /> ");
        strXML1.append("</trendLines>");
        strXML1.append("</graph>");
        jsonData.put("ChartTodayDeptPauseTime", strXML1.toString());
        return Action.SUCCESS;
    }

    public String getQueueEmployeeAnysle() throws SQLException {
        int deptid = req.getInt("deptid", super.getUserDept());
        String filterDeptids = this.deptService.FN_GetDeptAndChild(deptid);

        List data = this.queryService.getQueueEmployeeAnysle(filterDeptids);
        /*
         * 业务平均办理时间
         */
        StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='员工预警图' subcaption='业务平均办理时间' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='1' rotateNames='1' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1' formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'>");

        Float totalservicetime2 = 0f, totalservicecount = 0f;
        for (Object datum : data) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalservicetime2"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        Float avg = totalservicetime2 / totalservicecount;
        for (Object datum : data) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("totalservicetime"), 0);
            String Color = "60AAA3";
            if (f>avg)
                Color="FF0000";
            strXML1.append("<set name='"+m.get("ename")+"' value='"+f+"' color='" + Color + "' />");
        }
        strXML1.append("<trendLines>");
        strXML1.append("<line startValue='"+ avg +"' showOnTop='1' color='FF0000' displayvalue='平均办理时间' thickness='2' /> ");
        strXML1.append("</trendLines>");
        strXML1.append("</graph>");
        request.setAttribute("strXML1", strXML1.toString());
        request.setAttribute("strXMLType1", "Column3D.swf");
        /*
         ***********************************************************
         */
        /*
         * 业务平均等待时间
         */
        strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='员工预警图' subcaption='平均等待时间' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='1' rotateNames='1' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1' formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'>");

        totalservicetime2 = 0f;
        totalservicecount = 0f;
        for (Object datum : data) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalwaittime2"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        avg = totalservicetime2 / totalservicecount;
        for (Object datum : data) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("totalwaittime"), 0);
            String Color = "60AAA3";
            if (f>avg)
                Color="FF0000";
            strXML1.append("<set name='"+m.get("ename")+"' value='"+f+"' color='" + Color + "' />");
        }
        strXML1.append("<trendLines>");
        strXML1.append("<line startValue='"+ avg +"' showOnTop='1' color='FF0000' displayvalue='平均等待时间' thickness='2' /> ");
        strXML1.append("</trendLines>");
        strXML1.append("</graph>");
        request.setAttribute("strXML2", strXML1.toString());
        request.setAttribute("strXMLType2", "Column3D.swf");
        /*
         ***********************************************************
         */
        /*
         * 差评数预警
         */
        strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='员工预警图' subcaption='差评数' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='1' rotateNames='1' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1' >");

        totalservicetime2 = 0f;
        totalservicecount = 0f;
        for (Object datum : data) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalkeybmy"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        avg = totalservicetime2 / data.size();
        for (Object datum : data) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("totalkeybmy"), 0);
            String Color = "60AAA3";
            if (f>avg)
                Color="FF0000";
            strXML1.append("<set name='"+m.get("ename")+"' value='"+f+"' color='" + Color + "' />");
        }
        strXML1.append("<trendLines>");
        strXML1.append("<line startValue='"+ avg +"' showOnTop='1' color='FF0000' displayvalue='差评数' thickness='2' /> ");
        strXML1.append("</trendLines>");
        strXML1.append("</graph>");
        request.setAttribute("strXML3", strXML1.toString());
        request.setAttribute("strXMLType3", "Column3D.swf");
        /*
         ***********************************************************
         */
        /*
         * 暂停时长
         */
        strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph animation='0' caption='员工预警图' subcaption='暂停时长' baseFontSize='12' rotateLabels='0' rotateYAxisName='1' decimalPrecision='1' rotateNames='1' showDivLineValues='1' bgColor='FFFFFF' canvasBgColor='FFFFFF' numDivLines='1' formatNumberScale='1' defaultNumberScale='秒' numberScaleValue='60,60,24,7' numberScaleUnit='分,时,天,周'>");

        totalservicetime2 = 0f;
        totalservicecount = 0f;
        for (Object datum : data) {
            Map m = (Map) datum;
            totalservicetime2 += StringUtils.toFloat(m.get("totalpausetime"), 0);
            totalservicecount += StringUtils.toFloat(m.get("servicecount"), 0);
        }
        avg = totalservicetime2 / data.size();
        for (Object datum : data) {
            Map m = (Map) datum;
            Float f = StringUtils.toFloat(m.get("totalpausetime"), 0);
            String Color = "60AAA3";
            if (f>avg)
                Color="FF0000";
            strXML1.append("<set name='"+m.get("ename")+"' value='"+f+"' color='" + Color + "' />");
        }
        strXML1.append("<trendLines>");
        strXML1.append("<line startValue='"+ avg +"' showOnTop='1' color='FF0000' displayvalue='暂停时长' thickness='2' /> ");
        strXML1.append("</trendLines>");
        strXML1.append("</graph>");
        request.setAttribute("strXML4", strXML1.toString());
        request.setAttribute("strXMLType4", "Column3D.swf");

        request.setAttribute("w", request.getParameter("w"));
        request.setAttribute("h", request.getParameter("h"));
        return SUCCESS;
    }
}
