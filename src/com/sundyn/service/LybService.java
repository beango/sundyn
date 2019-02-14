package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.sql.*;
import java.util.*;

public class LybService extends SuperDao
{
    public boolean add(final LybVo lybVo) throws SQLException {
        final String sql = "insert into appries_lyb(lybTitle,lybAsk,lybAskId,lybAskDate)values(?,?,?,?)";
        final Object[] arg = { lybVo.getLybTitle(), lybVo.getLybAsk(), lybVo.getLybAskId(), lybVo.getLybAskDate() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(final LybVo lybVo) {
        final String sql = "update appries_lyb set lybAnswer=?,lybAnswerId=?,lybAnswerDate=?,lybState=?  where lybId=?";
        final Object[] arg = { lybVo.getLybAnswer(), lybVo.getLybAnswerId(), lybVo.getLybAnswerDate(), lybVo.getLybState(), lybVo.getLybId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean del(final Integer lybId) {
        final String sql = "delete from appries_lyb   where lybId=?";
        final Object[] arg = { lybId };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Map findById(final Integer lybId) {
        final String sql = "select * from appries_lyb   where lybId=?";
        final Object[] arg = { lybId };
        try {
            return this.getJdbcTemplate().queryForMap(sql, arg);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findAll() {
        final String sql = "select * from appries_lyb  order by lybId desc ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int lybCount() {
        final String sql = "select count(*) from appries_lyb  ";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
