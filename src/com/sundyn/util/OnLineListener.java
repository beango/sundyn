package com.sundyn.util;

import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.util.Map;

public class OnLineListener implements HttpSessionListener, HttpSessionAttributeListener, HttpSessionBindingListener
{
    public void sessionCreated(final HttpSessionEvent se) {
    }
    
    public void sessionDestroyed(final HttpSessionEvent se) {
        final Map m = (Map)se.getSession().getAttribute("employee");
        if (m != null) {
            final EmployeeList employeeList = EmployeeList.getInstance();
            EmployeeList.remove(m);
        }
        final Object o = se.getSession().getAttribute("mac");
        if (o != null) {
            final String mac = o.toString();
            final M7List mList = M7List.getInstance();
            M7List.remove(mac);
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        final Map manager = (Map)se.getSession().getAttribute("manager");
        if (manager != null) {
            EmployeeList.removeManager(manager);
        }
    }
    
    public void attributeAdded(final HttpSessionBindingEvent se) {
        final Map m = (Map)se.getSession().getAttribute("employee");
        if (m != null) {
            final EmployeeList employeeList = EmployeeList.getInstance();
            EmployeeList.add(m);
        }
        final Object o = se.getSession().getAttribute("mac");
        if (o != null) {
            final String mac = o.toString();
            final M7List mList = M7List.getInstance();
            M7List.add(mac);
        }
        if (se.getName().equals("manager")){
            final Map manager = (Map)se.getSession().getAttribute("manager");
            EmployeeList.addManager(manager);
        }
    }
    
    public void attributeRemoved(final HttpSessionBindingEvent se) {
    }
    
    public void attributeReplaced(final HttpSessionBindingEvent se) {
        final Map m = (Map)se.getSession().getAttribute("employee");
        if (m != null) {
            final EmployeeList employeeList = EmployeeList.getInstance();
            EmployeeList.add(m);
        }
        final Object o = se.getSession().getAttribute("mac");
        if (o != null) {
            final String mac = o.toString();
            final M7List mList = M7List.getInstance();
            M7List.add(mac);
        }
    }

    public void valueBound(HttpSessionBindingEvent e)
    {
        //users.trimToSize();
        System.out.println("HttpSessionBindingEvent:" + e.getName());
        /*if(!existUser(e.getName()))
        {
            users.add(e.getName());
            System.out.print(e.getName()+"\t  登入到系统\t"+(new Date()));
            System.out.println("     在线用户数为："+getCount());
        }else
            System.out.println(e.getName()+"已经存在");*/
    }

    public void valueUnbound(HttpSessionBindingEvent e)
    {
       /* users.trimToSize();
        String userName=e.getName();
        deleteUser(userName);
        System.out.print(userName+"\t  退出系统\t"+(new Date()));
        System.out.println("     在线用户数为："+getCount());*/
    }
}
