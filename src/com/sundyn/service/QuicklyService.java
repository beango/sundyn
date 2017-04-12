package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.util.*;

public class QuicklyService extends SuperDao
{
    public boolean addSearch(final QuicklyVo sea) {
        final String sql = "Insert into appries_quickly (name, excuteSql,startDate,managerId)values(?,?,?,?)";
        final Object[] arg = { sea.getName(), sea.getExcuteSql(), sea.getStartDate(), sea.getManagerId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateSearch(final QuicklyVo sea) {
        final String sql = "Update appries_apprieser set name=?, remark=?,excuteSql=?,startDate=?,endDate=?,serviceType=?,resultType=?,deptIDs=?,employees=?,managerId=?,count=? where id=?";
        final Object[] arg = { sea.getName(), sea.getRemark(), sea.getExcuteSql(), sea.getStartDate(), sea.getEndDate(), sea.getServiceType(), sea.getResultType(), sea.getDeptIDs(), sea.getEmployees(), sea.getManagerId(), sea.getClass(), sea.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delSearchById(final Integer Id) {
        final String sql = "delete from appries_quickly where id=" + Id;
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List findByManagerId(final Integer managerID) {
        final String sql = "select id,name,excuteSql,count,startDate from appries_quickly where managerId = " + managerID + "  order by  id desc limit 0,10";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map findById(final Integer id) {
        final String sql = "select * from appries_quickly where id = " + id;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
