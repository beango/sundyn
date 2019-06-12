package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import com.sundyn.utils.*;
import java.util.*;

public class NoticeService extends SuperDao
{
    public boolean noticeAdd(final NoticeVo notice) {
        final String sql = "Insert into appries_notice (title, content,date) values(?,?,?)";
        final Object[] arg = { notice.getTitle(), notice.getContent(), DateFormat.dateFormat(new Date()) };
        try {
            this.getJdbcTemplate().update(sql, arg);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public List findNotices() {
        final String sql = "select * from appries_notice order by date desc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public List findNotices(final int startrow, final int pageSize) {
        String sql = "select row_number() over(order by date desc) as rows, * from appries_notice";
        sql = "select * from ("+sql+") t2 where t2.rows>" + startrow + " and t2.rows<=" + (startrow+pageSize);
        try {
            return this.getJdbcTemplate().queryForList(sql, new Object[] { });
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public boolean noticeDelete(final int id) {
        final String sql = "delete from appries_notice where id=?";
        int num = 0;
        final Object[] arg = { id };
        try {
            num = this.getJdbcTemplate().update(sql, arg);
            if (num > 0) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean noticeUpdate(final NoticeVo notice) {
        int num = 0;
        final String sql = "update appries_notice set title=?,content=? where id=?";
        final Object[] args = { notice.getTitle(), notice.getContent(), notice.getId() };
        try {
            num = this.getJdbcTemplate().update(sql, args);
            if (num > 0) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Map findNoticeById(final int id) {
        final String sql = "select * from appries_notice  where id=?";
        final Object[] arg = { id };
        try {
            return this.getJdbcTemplate().queryForMap(sql, arg);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int getCount() {
        final String sql = "select count(id) from appries_notice";
        return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
    }

    public boolean existsByName(String id, String title) {
        String sql = "select count(*) from appries_notice  where title=? ";
        if (id!=null)
            sql += "and id!=" + id;
        final Object[] arg = { title };
        try {
            return this.getJdbcTemplate().queryForObject(sql, arg, Integer.class)>0;
        }
        catch (Exception e) {
            return false;
        }
    }
}
