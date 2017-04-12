package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.text.*;
import java.util.*;

public class PlayService extends SuperDao
{
    public boolean playAdd(final PlayVo playVo) {
        final String sql = "insert into appries_play (playName,playType,playSource,playUpdateDate,playTimes,playIndex)values(?,?,?,?,?,?) ";
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String d = df.format(new Date());
        final Object[] args = { playVo.getPlayName(), playVo.getPlayType(), playVo.getPlaySource(), d, playVo.getPlayTimes(), playVo.getPlayIndex() };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean playEdit(final PlayVo playVo) {
        final String sql = "update  appries_play  set playName=?,playType=?,playSource=?,playUpdateDate=?,playTimes=?,playIndex=? where playId=?";
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String d = df.format(new Date());
        final Object[] args = { playVo.getPlayName(), playVo.getPlayType(), playVo.getPlaySource(), d, playVo.getPlayTimes(), playVo.getPlayIndex(), playVo.getPlayId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean playDel(final Integer playId) {
        final String sql = "delete from appries_play  where playId=?";
        final Object[] args = { playId };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List playQuery(final String playName, final Integer start, final Integer num) {
        String sql = "select * from appries_play where playName like '%" + playName + "%' order by playIndex, playId desc ";
        if (start != null && num != null) {
            sql = String.valueOf(sql) + " limit " + start + "," + num;
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int countPlayQuery(final String playName) {
        final String sql = "select count(*) from  appries_play where playName like '%" + playName + "%'";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public Map findById(final Integer playId) {
        final String sql = "select * from appries_play where playId=?";
        final Object[] args = { playId };
        try {
            return this.getJdbcTemplate().queryForMap(sql, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findByIds(final String playIds) {
        final String sql = "select * from appries_play where playId in(" + playIds + ") order by playIndex,playId desc ";
        System.out.println("findByIds-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
