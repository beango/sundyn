package com.sundyn.util;

import com.sundyn.utils.*;
import java.util.*;
import org.apache.struts2.*;
import org.springframework.web.context.support.*;
import com.sundyn.service.*;
import org.springframework.context.*;

public class EmployeeList
{
    private static EmployeeList employeeList;
    private static Map mE;
    private static Object obj;
    
    static {
        EmployeeList.employeeList = null;
        EmployeeList.mE = new HashMap();
        EmployeeList.obj = null;
    }
    
    public static EmployeeList getInstance() {
        if (EmployeeList.employeeList == null) {
            DBConnHelper.emptyTable("delete from appries_onlineemployee");
        }
        return EmployeeList.employeeList = new EmployeeList();
    }
    
    public static EmployeeList getInstance(final int num) {
        if (EmployeeList.employeeList == null) {
            DBConnHelper.emptyTable("delete from appries_onlineemployee");
        }
        return EmployeeList.employeeList = new EmployeeList();
    }
    
    public static void add(final Object o) {
        final Map m = (Map)o;
        addMac(m);
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
