package com.sundyn.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.entity.SysDictinfo;
import com.sundyn.service.EmployeeService;
import com.sundyn.service.ISysDictinfoService;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictValueServlet
{
    private static DictValueServlet DATA;
    private static Map mE;
    private static Object obj;
    @Resource
    private static JdbcTemplate jdbcTemplate;

    static {
        DictValueServlet.DATA = null;
    }
    
    public static DictValueServlet getInstance() {
        if (DictValueServlet.mE == null) {
            String sql = "select dictkey, dictvalue from sys_dictinfo where isenable=1";
            mE = jdbcTemplate.queryForMap(sql);
        }
        return DictValueServlet.DATA = new DictValueServlet();
    }

}
