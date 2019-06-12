package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.gson.Gson;
import com.sundyn.entity.AuditLock;
import com.sundyn.entity.AuditLog;
import com.sundyn.service.DeptService;
import com.sundyn.service.EmployeeService;
import com.sundyn.service.IAuditLockService;
import com.sundyn.service.IAuditLogService;
import com.sundyn.util.impl.util;
import com.sundyn.vo.LogTypeEnum;
import com.xuan.xutils.utils.DateUtils;
import com.xuan.xutils.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

public class AuditAction extends MainAction {
    @Resource
    private IAuditLogService auditLoginService;
    @Resource
    private IAuditLockService auditLockService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private DeptService deptService;
    @Resource
    private IAuditLogService auditLogService;
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;
    @Getter @Setter
    private InputStream excel;
    @Getter @Setter
    Map<String,Object> jsonData = new HashMap<String,Object>();
    /*

     */
    public String auditlogin() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            String logtype = req.getString("logtype");
            String deptId = req.getString("deptId");
            String deptname = req.getString("deptname");
            final String ids = this.deptService.findChildALLStr1234(deptId);
            String sort = req.getString("sort");
            if (StringUtils.isBlank(sort))
                sort = "audit_log.ctime,desc";
            final Integer employeeId = req.getInt("employeeId");
            Map e = this.employeeService.findEmployeeById(employeeId);
            String keyCardNum = null;
            if (e!=null)
            {
                keyCardNum = e.get("cardnum").toString();
            }

            sort = sort.replace(",", " ");
            String ypfj = req.getString("ypfj");

            Wrapper<Map> ew =new EntityWrapper<Map>();
            ew.where("logtype not in ('登录','退出')");
            if (StringUtils.isNotBlank(logtype)){
                ew = ew.where("logtype='" + logtype + "'");
            }
            if (ypfj.equalsIgnoreCase("1")){
                ew = ew.where("(ypfj is not null and ypfj!=0)");
            }
            if (ypfj.equalsIgnoreCase("0")){
                ew = ew.where("(ypfj is null or ypfj=0)");
            }
            request.setAttribute("ypfj", ypfj);
            Page<Map> queryData = auditLoginService.querypagemap(new Page<Map>(pageindex, pageSize), ew.orderBy(sort));
            request.setAttribute("queryData", queryData);
            request.setAttribute("logtype", logtype);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String managerlogin() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            String logtype = req.getString("logtype");
            String deptId = req.getString("deptId");
            //String deptname = req.getString("deptname");
            //final String ids = this.deptService.findChildALLStr1234(deptId);
            String sort = req.getString("sort");
            if (StringUtils.isBlank(sort))
                sort = "audit_log.ctime,desc";
            final Integer employeeId = req.getInt("employeeId");
            Map e = this.employeeService.findEmployeeById(employeeId);
            String keyCardNum = null;
            if (e!=null)
            {
                keyCardNum = e.get("cardnum").toString();
            }

            sort = sort.replace(",", " ");
            String ypfj = req.getString("ypfj");

            Wrapper<Map> ew =new EntityWrapper<Map>();
            ew.in("logtype", new String[]{LogTypeEnum.登录.toString(), LogTypeEnum.退出.toString()});

            if (StringUtils.isNotBlank(logtype)){
                ew = ew.where("logtype='" + logtype + "'");
            }
            if (ypfj.equalsIgnoreCase("1")){
                ew = ew.where("(ypfj is not null and ypfj!=0)");
            }
            if (ypfj.equalsIgnoreCase("0")){
                ew = ew.where("(ypfj is null or ypfj=0)");
            }
            request.setAttribute("ypfj", ypfj);
            Page<Map> queryData = auditLoginService.querypagemap(new Page<Map>(pageindex, pageSize), ew.orderBy(sort));
            request.setAttribute("queryData", queryData);
            request.setAttribute("logtype", logtype);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String genCheckDigit(Map model){
        StringBuilder digitStr = new StringBuilder();

        digitStr.append(model.get("ipadd"));
        digitStr.append("|");

        digitStr.append(model.get("logrst"));
        digitStr.append("|");

        digitStr.append(model.get("logtype"));
        digitStr.append("|");

        digitStr.append(DateUtils.date2String(DateUtils.string2Date(model.get("ctime").toString(),"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
        digitStr.append("|");

        return util.md5(digitStr.toString());
    }

    public String auditlock() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            String exportExcel = request.getParameter("export");
            String servicedate = req.getString("servicedate");
            String deptId = req.getString("deptId");
            String deptname = req.getString("deptname");
            final String ids = this.deptService.findChildALLStr1234(deptId);
            String sort = req.getString("sort");
            if (StringUtils.isBlank(sort))
                sort = "audit_lock.ctime,desc";
            final Integer employeeId = req.getInt("employeeId");
            Map e = this.employeeService.findEmployeeById(employeeId);
            String keyCardNum = null;
            if (e != null) {
                keyCardNum = e.get("cardnum").toString();
            }

            sort = sort.replace(",", " ");
            String ypfj = req.getString("ypfj");

            Wrapper<Map> ew = new EntityWrapper<Map>();

            if (StringUtils.isNotBlank(servicedate)) {
                ew = ew.where("servicedate='" + servicedate + "'");
            }
            if (ypfj.equalsIgnoreCase("1")) {
                ew = ew.where("(ypfj is not null and ypfj!=0)");
            }
            if (ypfj.equalsIgnoreCase("0")) {
                ew = ew.where("(ypfj is null or ypfj=0)");
            }
            request.setAttribute("ypfj", ypfj);
            if (exportExcel != null && exportExcel.toLowerCase().equals("true"))
                pageSize = 999999;
            Page<Map> queryData = auditLockService.querypagemap(new Page<Map>(pageindex, pageSize), ew.orderBy(sort));
            request.setAttribute("queryData", queryData);
            request.setAttribute("servicedate", servicedate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String auditunlock(){
        int id = req.getInt("id");
        if (id==0)
        {
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "参数错误!");
        }
        AuditLock lock = auditLockService.selectById(id);
        boolean b = auditLockService.updateForSet("status=0, realunlocktime='"+ DateUtils.date2String(new Date())+"', unlockuser=" + UserID,
                new EntityWrapper<AuditLock>().where("id=" + id));
        if (b){
            AuditLog t = new AuditLog();
            t.setName(super.UserName);
            Date logintime = new Date();
            String loginadd = util.getRemoteIpAddr();
            String logdevice = util.getRemoteBowser();
            if (StringUtils.isBlank(logdevice) && logdevice.length()>150)
                logdevice = logdevice.substring(0, 149);
            t.setLogdevice(logdevice);
            t.setCtime(logintime);
            t.setIpadd(loginadd);
            t.setLogtype("管理员解锁");
            t.setLogrst("管理员解锁");
            t.setLogrstdesc("管理员解锁\""+lock.getLocktype()+"\":"+lock.getName()+"成功");
            auditLogService.insert(t);

            jsonData.put("succ", true);
            jsonData.put("msg", "解锁成功!");
        }
        else
        {
            jsonData.put("succ", false);
            jsonData.put("msg", "解锁失败!");
        }
        return SUCCESS;
    }

    public String securitylogrpt(){
        Date startDate = req.getDate("startDate");
        Date endDate = req.getDate("endDate");

        Wrapper<Map> ew = new EntityWrapper<Map>();
        if (null != startDate)
            ew.where("ctime>={0}", startDate);
        if (null != endDate)
            ew.where("ctime<{0}", DateUtils.addDay(endDate, 1));
        List<Map> list = this.auditLoginService.securitylogrpt(ew);
        request.setAttribute("data", list);
        ArrayList xAxisData = new ArrayList(),
        seriesData1 = new ArrayList(), seriesData2 = new ArrayList();
        for (Map map : list) {
            xAxisData.add(map.get("realname"));
            seriesData1.add(map.get("succtimes"));
            seriesData2.add(map.get("errortimes"));
        }
        request.setAttribute("xAxisData", new Gson().toJson(xAxisData));
        request.setAttribute("seriesData1", new Gson().toJson(seriesData1));
        request.setAttribute("seriesData2", new Gson().toJson(seriesData2));
        request.setAttribute("startDate", DateUtils.date2String(startDate));
        request.setAttribute("endDate", DateUtils.date2String(endDate));
        return SUCCESS;
    }

    public String auditlogrpt(){
        Date startDate = req.getDate("startDate");
        Date endDate = req.getDate("endDate");

        Wrapper<Map> ew = new EntityWrapper<Map>();
        if (null != startDate)
            ew.where("ctime>={0}", startDate);
        if (null != endDate)
            ew.where("ctime<{0}", DateUtils.addDay(endDate, 1));
        List<Map> list = this.auditLoginService.auditlogrpt(ew);
        request.setAttribute("data", list);
        ArrayList xAxisData = new ArrayList(),
                seriesData = new ArrayList();
        for (Map map : list) {
            xAxisData.add(map.get("logtype"));
            seriesData.add(map.get("count"));
        }
        request.setAttribute("xAxisData", new Gson().toJson(xAxisData));
        request.setAttribute("seriesData", new Gson().toJson(seriesData));
        request.setAttribute("startDate", DateUtils.date2String(startDate));
        request.setAttribute("endDate", DateUtils.date2String(endDate));
        return SUCCESS;
    }

    public String syslogrpt(){
        Date startDate = req.getDate("startDate");
        Date endDate = req.getDate("endDate");

        Wrapper<Map> ew = new EntityWrapper<Map>();
        ew.where("actionname not in ('用户登录','用户退出')");
        if (null != startDate)
            ew.where("actiontime>={0}", startDate);
        if (null != endDate)
            ew.where("actiontime<{0}", DateUtils.addDay(endDate, 1));
        List<Map> list = this.auditLoginService.syslogrpt(ew);
        request.setAttribute("data", list);
        ArrayList xAxisData = new ArrayList(),
                seriesData = new ArrayList();
        for (Map map : list) {
            xAxisData.add(map.get("actionname"));
            seriesData.add(map.get("count"));
        }
        request.setAttribute("xAxisData", new Gson().toJson(xAxisData));
        request.setAttribute("seriesData", new Gson().toJson(seriesData));
        request.setAttribute("startDate", DateUtils.date2String(startDate));
        request.setAttribute("endDate", DateUtils.date2String(endDate));
        return SUCCESS;
    }
}
