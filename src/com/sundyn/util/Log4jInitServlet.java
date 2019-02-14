package com.sundyn.util;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Log4jInitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
    }
    public void init() throws ServletException {
        /*String filePath=this.getServletConfig().getServletContext().getRealPath("/");
        System.setProperty("logPath", filePath);*/

        //PropertyConfigurator.configure(getServletContext().getRealPath("/") + getInitParameter("configfile"));
    }
}

