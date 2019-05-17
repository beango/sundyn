package com.sundyn.util;

import com.sundyn.service.EmployeeService;
import com.sundyn.service.ManagerService;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;

public class ClearServlet extends HttpServlet
{
    public void init() {
        EmployeeList.getInstance(0);
        M7List.getInstance(0);
    }
}
