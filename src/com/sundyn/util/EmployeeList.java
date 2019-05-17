package com.sundyn.util;

import com.sundyn.service.EmployeeService;
import com.xuan.xutils.utils.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeList
{
    private static EmployeeList employeeList;
    private static Map mE;
    private static Object obj;
    @Resource
    private static JdbcTemplate jdbcTemplate;

    //static JdbcTemplate jdbc;
    static {
        EmployeeList.employeeList = null;
        EmployeeList.mE = new HashMap();
        EmployeeList.obj = null;
    }
    
    public static EmployeeList getInstance() {
        if (EmployeeList.employeeList == null) {
            String sql = "delete from appries_onlineemployee";
            jdbcTemplate.update(sql);
        }
        return EmployeeList.employeeList = new EmployeeList();
    }
    
    public static EmployeeList getInstance(final int num) {
        if (EmployeeList.employeeList == null && jdbcTemplate!=null) {
            String sql = "delete from appries_onlineemployee";
            jdbcTemplate.update(sql);
        }
        return EmployeeList.employeeList = new EmployeeList();
    }
    
    public static void add(final Object o) {
        final Map m = (Map)o;
        addMac(m);
    }

    public static void addManager(final Object o) {
        final Map m = (Map)o;
        ServletContext context = ServletActionContext.getServletContext();
        ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");

        if (null == map)
        {
            map = new ConcurrentHashMap<String, Object>();
            context.setAttribute("activeSessions", map);
        }
        if (!map.contains(m.get("name")))
            map.put(m.get("name").toString(), m.get("name") + "   登陆时间:" + DateUtils.date2String(new Date()));
    }

    public static void removeManager(final Object o) {
        final Map m = (Map)o;
        ServletContext context = ServletActionContext.getServletContext();
        ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");
        if (null != map && map.contains(m.get("name")))
        {
            map.remove(m.get("name"));
            context.setAttribute("activeSessions", map);
        }
    }

    public static void remove(final Object o) {
        final Map m = (Map)o;
        deleteMac(m);
    }
    
    public static int getNum() {
        return EmployeeList.mE.size();
    }
    
    public static Map getList() {
        EmployeeList.mE = new HashMap();
        final List list = onlineMacs(null);
        for (final Object m1 : list) {
        	Map m = (Map)m1;
            EmployeeList.mE.put(m.get("id"), m);
        }
        return EmployeeList.mE;
    }
    
    public static void addMac(final Map m) {
        final ApplicationContext ac1 = (ApplicationContext)WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
        final EmployeeService a = (EmployeeService)ac1.getBean("employeeService");
        if (EmployeeList.obj == null) {
            EmployeeList.obj = a;
        }
        a.onlineEmployee(new StringBuilder().append(m.get("id")).toString(), (String)m.get("name"), (String)m.get("cardnum"));
    }
    
    public static void deleteMac(final Map m) {
        final EmployeeService a = (EmployeeService)EmployeeList.obj;
        a.onlineEmployeeDel(new StringBuilder().append(m.get("id")).toString());
    }
    
    public static List onlineMacs(final String mac) {
        final ApplicationContext ac1 = (ApplicationContext)WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
        final EmployeeService a = (EmployeeService)ac1.getBean("employeeService");
        return a.onlineEmployees(mac);
    }
}
