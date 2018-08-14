package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.text.*;
import java.util.*;

public class PlayService extends SuperDao
{
    public boolean playAdd(final PlayVo playVo) {
        final String sql = "insert into appries_play (playName,playType,playSource,playUpdateDate,playTimes,playIndex,orgname)values(?,?,?,?,?,?,?) ";
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String d = df.format(new Date());
        final Object[] args = { playVo.getPlayName(), playVo.getPlayType(), playVo.getPlaySource(), d, playVo.getPlayTimes(), playVo.getPlayIndex(), playVo.getOrgname()==null?"":playVo.getOrgname()};
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean playEdit(final PlayVo playVo) {
        final String sql = "update  appries_play  set playName=?,playType=?,playSource=?,playUpdateDate=?,playTimes=?,playIndex=?,orgname=? where playId=?";
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String d = df.format(new Date());
        final Object[] args = { playVo.getPlayName(), playVo.getPlayType(), playVo.getPlaySource(), d, playVo.getPlayTimes(), playVo.getPlayIndex(), playVo.getOrgname(), playVo.getPlayId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
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
            return false;
        }
    }
    
    public List playQuery(final String playName, final Integer start, final Integer num) {
        String sql = "select row_number() over(order by playIndex, playId desc) as rows, * from appries_play where 1=1 ";
        if (playName!=null && !playName.equals(""))
            sql += "and playName like '%" + playName + "%'";
        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public int countPlayQuery(final String playName) {
        final String sql = "select count(*) from  appries_play where playName like '%" + playName + "%'";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
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
            return null;
        }
    }
    
    public List findByIds(final String playIds) {
        if(playIds==null || playIds.equals(""))
            return null;
        final String sql = "select * from appries_play where playId in(" + playIds + ") order by playIndex,playId desc ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean existsByName(Object id, String playTitle) {
        String sql = "select count(*) from appries_play where playname=? ";
        if (id!=null)
            sql += "and playid!=" + id;
        final Object[] arg = { playTitle };
        try {
            return this.getJdbcTemplate().queryForInt(sql, arg)>0;
        }
        catch (Exception e) {
            return false;
        }
    }
}
