package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.util.*;

public class PlayListService extends SuperDao
{
    public boolean playListAdd(final PlayListVo playListVo) {
        final String sql = "insert into appries_playlist (playListName,playListDescription,playIds )values(?,?,?) ";
        final Object[] args = { playListVo.getPlayListName(), playListVo.getPlayListDescription(), playListVo.getPlayIds() };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean playListEdit(final PlayListVo playListVo) {
        final String sql = "update appries_playlist set playListName=?,playListDescription=?,playIds=? where playListId=? ";
        final Object[] args = { playListVo.getPlayListName(), playListVo.getPlayListDescription(), playListVo.getPlayIds(), playListVo.getPlayListId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean playListDel(final Integer playListId) {
        final String sql = "delete from appries_playlist  where playListId=?";
        final Object[] args = { playListId };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List playListQuery(final String playListName, final String deptIds, final Integer start, final Integer num) {
        String sql = "select * from appries_playlist where playListName like '%" + playListName + "%'   ";
        if (deptIds != null && !deptIds.equals("")) {
            sql = String.valueOf(sql) + "and  playListId  in(select dept_playListId from appries_dept where id in( " + deptIds + "))  ";
        }
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
    
    public List playListQuery() {
        final String sql = "select * from appries_playlist  ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int countPlayListQuery(final String playListName, final String deptIds) {
        String sql = "select count(*) from  appries_playlist where playListName like '%" + playListName + "%'  ";
        if (deptIds != null && !deptIds.equals("")) {
            sql = String.valueOf(sql) + "and  playListId  in(select dept_playListId from appries_dept where id in( " + deptIds + ")) ";
        }
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public Map findById(final Integer playListId) {
        final String sql = "select * from appries_playlist where playListId=?";
        final Object[] args = { playListId };
        try {
            return this.getJdbcTemplate().queryForMap(sql, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int getLastPlayListId() {
        final String sql = "select max(playListId) from appries_playlist ";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
