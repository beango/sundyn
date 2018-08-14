package com.sundyn.util;

import java.util.*;
import java.text.*;
import javax.servlet.http.*;

public class OnLineListener implements HttpSessionListener, HttpSessionAttributeListener
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
}
