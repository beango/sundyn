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
            e.printStackTrace();
            return false;
        }
    }
    
    public List findNotices() {
        final String sql = "select * from appries_notice order by date desc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findNotices(final int startrow, final int pageSize) {
        final String sql = "select * from appries_notice order by date desc limit ?,?";
        try {
            return this.getJdbcTemplate().queryForList(sql, new Object[] { startrow, pageSize });
        }
        catch (Exception e) {
            e.printStackTrace();
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
            final String a = "\u6ca1\u6709\u627e\u5230id\u4e3a\uff1a\u201c+id.toSring+\u201d\u7684\u901a\u77e5\u516c\u544a\u9605\u8bfb\u6570\u66f4\u65b0\u5931\u8d25\uff01";
            System.out.println(a);
            e.printStackTrace();
            return null;
        }
    }
    
    public int getCount() {
        final String sql = "select count(id) from appries_notice";
        return this.getJdbcTemplate().queryForInt(sql);
    }
}
