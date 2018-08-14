package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.*;
import com.sundyn.util.*;
import java.util.*;
import java.util.Date;

public class M7InfoAction extends MainAction
{
    private DeptService deptService;
    private PowerService powerService;
    
    public PowerService getPowerService() {
        return this.powerService;
    }
    
    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public String m7InfoQuery() {
        M7Info.getInstance();
        final List ls = M7Info.getList(false);
        final List temp = new ArrayList();
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Pager pager = new Pager("currentpage", pageSize, ls.size(), request);
        for (int start = (pager.getCurrentPage() - 1) * pager.getPageSize(), end = start + pager.getPageSize(); start < end && start < ls.size(); ++start) {
            temp.add(ls.get(start));
        }
        pager.setPageList(temp);
        request.setAttribute("pager", (Object)pager);
        return "success";
    }
    
    public String m7InfoOnline() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        final Date cDate = new Date();
        int minutes = cDate.getMinutes();
        minutes -= 30;
        cDate.setMinutes(minutes);
        final String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDate);
        final List ls = this.deptService.getAllleafesAll3(deptIds, dt);
        int num = 0;
        if (ls != null) {
            num = ls.size();
        }
        final Pager pager = new Pager("currentPage", pageSize, num, request);
        final List lsOnline = this.deptService.findOnlineMacNotNull3(dt, (pager.getCurrentPage() - 1) * pager.getPageSize(), pager.getPageSize(), deptIds);
        pager.setPageList(lsOnline);
        request.setAttribute("pager", (Object)pager);
        return "success";
    }
    
    public String m7OnlineExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        final Date cDate = new Date();
        int minutes = cDate.getMinutes();
        minutes -= 30;
        cDate.setMinutes(minutes);
        final String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDate);
        final List lsOnline = this.deptService.getAllleafesAll3(deptIds, dt);
        final Date date = new Date();
        final DateFormat d1 = DateFormat.getDateTimeInstance();
        final DateFormat d2 = DateFormat.getDateTimeInstance(1, 1);
        final String args = d1.format(date);
        final String args2 = d2.format(date);
        final StringBuffer excelBuf = new StringBuffer();
        excelBuf.append("\t" + this.getText("sundyn.system.checkM7Info.macAddress") + "\t" + this.getText("sundyn.system.checkM7Info.windowName") + "\t" + this.getText("sundyn.system.checkOnlineM7.unitName") + "\t" + this.getText("sundyn.system.checkOnlineM7.onlineOrNot") + "\n");
        for (int t = 0; t < lsOnline.size(); ++t) {
            final Map m2 = (Map) lsOnline.get(t);
            excelBuf.append("\t" + m2.get("remark") + "\t" + m2.get("name") + "\t" + m2.get("fatherName") + "\t");
            System.out.println("m2.get(online)=" + m2.get("online").toString());
            if (m2.get("online").toString().equals("\u5728\u7ebf")) {
                System.out.println(this.getText("sundyn.system.m7Online.online"));
                excelBuf.append(this.getText("sundyn.system.m7Online.online"));
            }
            else if (m2.get("online").toString().equals("\u4e0d\u5728\u7ebf")) {
                System.out.println(this.getText("sundyn.system.m7Online.offline"));
                excelBuf.append(this.getText("sundyn.system.m7Online.offline"));
            }
            excelBuf.append("\n");
        }
        System.out.println("m7OnlineExcel.args=" + args);
        System.out.println();
        final String excelString = excelBuf.toString();
        final Poi poi = new Poi();
        poi.addText(excelString);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        final String path2 = String.valueOf(path) + "standard.xls";
        path = String.valueOf(path) + "m7OnlineState\\" + args2 + ".xls";
        System.out.println("m7OnlineExcel.path2=" + path2);
        poi.createFile(path2);
        return "success";
    }
    
    public void autoM7OnlineExcel() throws Exception {
        M7List.getInstance();
        final Map m7 = M7List.getList();
        Iterator i = m7.keySet().iterator();
        String mac = "";
        while (i.hasNext()) {
            mac = String.valueOf(mac) + "'" + i.next() + "'" + ",";
        }
        if (mac.endsWith(",")) {
            mac = mac.substring(0, mac.length() - 1);
        }
        if (mac.equals("")) {
            mac = "''";
        }
        int num = 0;
        System.out.println("XX");
        final List ls = this.deptService.getAllleafes();
        System.out.println("XXA");
        if (ls != null) {
            num = ls.size();
        }
        String errorMac = "";
        i = m7.keySet().iterator();
        while (i.hasNext()) {
            final String m8 = i.next().toString();
            int j;
            for (j = 0; j < ls.size(); ++j) {
            	Map map = (Map)ls.get(j);
                final String remark = map.get("remark").toString();
                if (m8.equals(remark)) {
                    break;
                }
            }
            if (j == ls.size()) {
                errorMac = String.valueOf(errorMac) + m8 + ",";
            }
        }
        final List lsOnline = this.deptService.findAllOnline(mac);
        final Date date = new Date();
        final DateFormat d1 = DateFormat.getDateTimeInstance();
        final DateFormat d2 = DateFormat.getDateTimeInstance(1, 1);
        final String args = d1.format(date);
        final String args2 = d2.format(date);
        final StringBuffer excelBuf = new StringBuffer();
        excelBuf.append("\t\t" + this.getText("\u5728\u7ebfM7\u65e5\u5fd7") + "\n");
        excelBuf.append(String.valueOf(this.getText("\u7edf\u8ba1\u65f6\u95f4:")) + this.getText(args) + "\n");
        excelBuf.append("\t" + this.getText("Mac\u5730\u5740") + "\t" + this.getText("\u7a97\u53e3\u540d\u79f0") + "\t" + this.getText("\u5355\u4f4d\u540d\u79f0") + "\t" + this.getText("\u662f\u5426\u5728\u7ebf") + "\n");
        for (int t = 0; t < lsOnline.size(); ++t) {
            final Map m9 = (Map) lsOnline.get(t);
            excelBuf.append("\t" + m9.get("remark") + "\t" + m9.get("name") + "\t" + m9.get("fatherName") + "\t" + m9.get("online") + "\n");
        }
        System.out.println("m7OnlineExcel.args=" + args);
        System.out.println();
        final String excelString = excelBuf.toString();
        final Poi poi = new Poi();
        poi.addText(excelString);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        path = String.valueOf(path) + "m7OnlineState\\" + args2 + ".xls";
        System.out.println("m7OnlineExcel.path=" + path);
        poi.createFile(path);
    }
}
