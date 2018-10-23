package com.sundyn.dao;

import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class SuperDao
{
    JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }
    
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected Integer getUserDept(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        Map map = (Map) session.getAttribute("manager");
        if (map == null)
            return null;
        return Integer.valueOf(map.get("deptid").toString());
    }
}
