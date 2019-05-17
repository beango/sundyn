package com.sundyn.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActiveSessionsListener {
    private ServletContext context = null;


    public ActiveSessionsListener(ServletContext context)
    {
        if (null == context)
        {
            throw new IllegalArgumentException("Null servletContent is accept.");
        }
        this.context = context;
    }


    public void valueBound(HttpSessionBindingEvent sessionBindingEvent)
    {        System.out.println("2////////////////////////////////");

        ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");

        if (null == map)
        {
            map = new ConcurrentHashMap<String, Object>();
            context.setAttribute("activeSessions", map);
        }

        HttpSession session = sessionBindingEvent.getSession();
        Map manager = (Map)session.getAttribute("manager");
        map.put(session.getId(), manager.get("realname") + "   登陆时间:"
                + new Date().toLocaleString());
        System.out.println("////////////////////////////////");
        context.setAttribute("activeSessions", map);
    }


    public void valueUnbound(HttpSessionBindingEvent sessionBindingEvent)
    {
        Object obj = null;
        try
        {

            obj = sessionBindingEvent.getSession().getAttribute("user");
        }
        catch (Exception e)
        {
            obj = null;
        }
        if (null == obj)
        {
            ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");

            if (null != map)
            {
                if(map.containsKey(sessionBindingEvent.getSession().getId()))
                {
                    map.remove(sessionBindingEvent.getSession().getId());
                    context.setAttribute("activeSessions", map);
                }
            }
        }
    }
}
