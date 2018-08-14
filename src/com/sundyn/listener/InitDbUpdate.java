package com.sundyn.listener;

import com.sundyn.service.ManagerService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitDbUpdate implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("webexit ... contextDestroyed");
    }

    public void contextInitialized(ServletContextEvent arg0) {

        System.out.println("webinit ... contextInitialized");
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
        ManagerService managerService = (ManagerService)ctx.getBean("managerService");
        int count = managerService.countByName("admin");
        System.out.println("webinit ... count:" + count);
    }
}