package com.sundyn.service;

import com.sundyn.dao.*;
import org.apache.log4j.*;
import java.util.*;

public class ErrorInfoService extends SuperDao
{
    private static final Logger logger;
    
    static {
        logger = Logger.getLogger((Class)AppriesService.class.getClass());
    }
    
    public List query(final String startDate, final String endDate, final int start, final int end) {
        String sql = "select * from appries_erroinfo where 1=1 ";
        if (!startDate.equals("")) {
            sql = String.valueOf(sql) + " and left(appriestime,10)>='" + startDate + "' ";
        }
        if (!endDate.equals("")) {
            sql = String.valueOf(sql) + " and left(appriestime,10)<='" + endDate + "' ";
        }
        sql = String.valueOf(sql) + "  order by id desc  ";
        sql = String.valueOf(sql) + " limit " + start + "," + end;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int query(final String startDate, final String endDate) {
        String sql = "select count(*) from appries_erroinfo where 1=1 ";
        if (!startDate.equals("")) {
            sql = String.valueOf(sql) + " and left(appriestime,10)>='" + startDate + "' ";
        }
        if (!endDate.equals("")) {
            sql = String.valueOf(sql) + " and left(appriestime,10)<='" + endDate + "' ";
        }
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    public int del(final String ids) {
        final String sql = "delete   from appries_erroinfo where  id in(" + ids + ") ";
        try {
            return this.getJdbcTemplate().update(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    public boolean add(final String mac, final String cardnum, final String pj) {
        final String sql = "insert into appries_erroinfo (appriestime,reamark,m_devid,cardnum,keyid) value (now(),?,-1,?,?)";
        final Object[] args = { mac, cardnum, pj };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            ErrorInfoService.logger.debug((Object)e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean addDetail(final String mac, final String cardnum, final String pj, final String errorInfo) {
        final String sql = "insert into appries_erroinfo (appriestime,reamark,m_devid,cardnum,keyid,ext1) value (now(),?,-1,?,?,?)";
        final Object[] args = { mac, cardnum, pj, errorInfo };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            ErrorInfoService.logger.debug((Object)e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
