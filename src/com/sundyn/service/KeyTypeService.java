package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.sql.*;
import java.util.*;

public class KeyTypeService extends SuperDao
{
    public boolean add(final KeyVo key) throws SQLException {
        final String sql = "insert into appries_keytype(name,keyNo,isJoy,apprieserid,remark,ext1,ext2,yes)vlues(?,?,?,?,?,?,?,?)";
        final Object[] arg = { key.getName(), key.getKeyNo(), key.getIsJoy(), key.getAppriesId(), key.getRemark(), key.getExt1(), key.getExt2(), key.getYes() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List findall() throws SQLException {
        final String sql = "select * from appries_keytype ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findByApprieserId(final Integer ApprieserId) throws SQLException {
        String sql = "select id,name,isJoy,yes,ext1 from appries_keytype where 1=1 ";
        if(ApprieserId != null)
            sql += "and apprieserid=" + ApprieserId;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findByApprieserYes(final Integer ApprieserId, final Integer yes) throws SQLException {
        final String sql = "select id,name,keyNo,isJoy,ext1 from appries_keytype where apprieserid=" + ApprieserId + " and yes=" + yes;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findByApprieserId(final Integer ApprieserId, final Integer yes, final String isJoy) throws SQLException {
        final String sql = "select  name ,keyNo, ext2 from appries_keytype where apprieserid=" + ApprieserId + " and isJoy='" + isJoy + "'  and yes=" + yes + "  order by keyNo";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findByApprieserId(final Integer ApprieserId, final Integer yes) {
        final String sql = "select  name ,keyNo from appries_keytype where apprieserid=" + ApprieserId + "    and yes=" + yes + "  order by keyNo";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean update(final KeyVo key) {
        final String sql = "update appries_keytype set name=?,keyNo=?,isJoy=?,apprieserid=?,remark=?,ext1=?,ext2=?,yes=? where id=?";
        final Object[] arg = { key.getName(), key.getKeyNo(), key.getIsJoy(), key.getAppriesId(), key.getRemark(), key.getExt1(), key.getExt2(), key.getYes(), key.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(final Integer id, final String name, final String isJoy, final String yes, final String ext1, final String ext2) {
        final String sql = "update appries_keytype set name=? ,isJoy=?,yes=?,ext1=? ,ext2=? where id=?";
        final Object[] arg = { name, isJoy, yes, ext1, ext2, id };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List getDissatisfiedKey() throws SQLException {
        final String sql = "SELECT keyNo FROM appries_keytype where yes=1 and isJoy!='ON'";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String findAllKeyInUse(final Integer yes) throws SQLException {
        final String sql = "select * from appries_keytype where yes=" + yes + " order by keyNo";
        try {
            final List l = this.getJdbcTemplate().queryForList(sql);
            String allKey = "";
            if (l != null || !l.equals("")) {
                for (int i = 0; i < l.size(); ++i) {
                    final Map m = (Map) l.get(i);
                    allKey = String.valueOf(allKey) + m.get("keyNo").toString() + ",";
                }
                allKey = allKey.substring(0, allKey.length() - 1);
            }
            return allKey;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "8";
        }
    }

    public String findAllKeyInUseWithNoEval(final Integer yes) throws SQLException {
        final String sql = "select * from appries_keytype where yes=" + yes + " order by keyNo";
        try {
            final List l = this.getJdbcTemplate().queryForList(sql);
            String allKey = "";
            if (l != null || !l.equals("")) {
                for (int i = 0; i < l.size(); ++i) {
                    final Map m = (Map) l.get(i);
                    allKey = String.valueOf(allKey) + m.get("keyNo").toString() + ",";
                }
                allKey = allKey.substring(0, allKey.length() - 1);
                allKey +=",6";
            }
            return allKey;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "8";
        }
    }
}
