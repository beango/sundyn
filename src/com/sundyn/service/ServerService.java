package com.sundyn.service;

import com.sundyn.dao.*;
import java.sql.*;
import com.sundyn.vo.*;
import java.util.*;

public class ServerService extends SuperDao
{
    public boolean delServer(final String id) throws SQLException {
        final String sql = "delete from appries_serviceType where id =" + id;
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean AddServer(final ServiceVo form) throws SQLException {
        final String sql = "insert into appries_servicetype (businessName,remark) values(?,?)";
        final Object[] arg = { form.getBusinessName(), form.getRemark() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateServer(final ServiceVo form) throws SQLException {
        final String sql = "update appries_servicetype set businessName=?,remark=? where id=?";
        final Object[] arg = { form.getBusinessName(), form.getRemark(), form.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Map findById(final String id) throws SQLException {
        final String sql = "select id,businessName,remark from appries_servicetype where id =" + id;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findAll() {
        final String sql = "select id,businessName from appries_servicetype ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
}
