package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.util.*;

public class ApprieserService extends SuperDao
{
    public boolean addApprieser(final ApprieserVo apprieser) {
        final String sql = "Insert into appries_Apprieser (name, remark)values(?,?)";
        final Object[] arg = { apprieser.getName(), apprieser.getName() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateApprieser(final ApprieserVo form) {
        final String sql = "Update appries_apprieser set name=?,remark=? where apprieserid=?";
        final Object[] arg = { form.getName(), form.getRemark(), form.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delApprieser(final ApprieserVo form) {
        final String sql = "Update appries_apprieser set name=?,remark=? where apprieserid=?";
        final Object[] arg = { form.getName(), form.getRemark(), form.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List findAll() {
        final String sql = "select * from appries_apprieser";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map findById(final String apprieserId) {
        final String sql = "select * from appries_apprieser where apprieserid = " + apprieserId;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findKeyById(final String apprieserId) {
        final String sql = "select id,name,keyNo,isJoy,apprieserid,remark from appries_keytype where apprieserid = " + apprieserId;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
