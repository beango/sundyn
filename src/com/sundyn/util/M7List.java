package com.sundyn.util;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.ManagerService;
import com.sundyn.utils.DBConnHelper;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class M7List extends ActionSupport
{
    private static M7List m7List;
    private static Map m7;
    private static Object obj;
    @Resource
    private static JdbcTemplate jdbcTemplate;
    static {
        M7List.m7List = null;
        M7List.m7 = new HashMap();
        M7List.obj = null;
    }
    
    public static M7List getInstance() {
        if (M7List.m7List == null) {
            M7List.m7List = new M7List();
        }
        return M7List.m7List;
    }
    
    public static M7List getInstance(final int num) {
        if (M7List.m7List == null && jdbcTemplate != null) {
            String sql = "delete from appries_onlinemac";
            jdbcTemplate.update(sql);
        }
        return M7List.m7List = new M7List();
    }
    
    public static void add(final String mac) {
        addMac(mac);
    }
    
    public static void remove(final String mac) {
        deleteMac(mac);
    }
    
    public static int getNum() {
        return M7List.m7.size();
    }
    
    public static Map getList() {
        M7List.m7 = new HashMap();
        final List list = onlineMacs(null);
        for (final Object m1 : list) {
        	Map m = (Map)m1;
            M7List.m7.put(m.get("mac"), m.get("mac"));
        }
        return M7List.m7;
    }
    
    public static void addMac(final String mac) {
        final ApplicationContext ac1 = (ApplicationContext)WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
        final ManagerService a = (ManagerService)ac1.getBean("managerService");
        if (M7List.obj == null) {
            M7List.obj = a;
        }
        a.onlineMac(mac);
    }
    
    public static void deleteMac(final String mac) {
        final ManagerService a = (ManagerService)M7List.obj;
        a.onlineMacDel(mac);
    }
    
    public static List onlineMacs(final String mac) {
        final ApplicationContext ac1 = (ApplicationContext)WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
        final ManagerService a = (ManagerService)ac1.getBean("managerService");
        return a.onlineMacs(mac);
    }
}
