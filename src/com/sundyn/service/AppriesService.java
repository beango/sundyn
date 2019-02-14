package com.sundyn.service;

import com.sundyn.dao.*;
import org.apache.log4j.*;
import com.sundyn.vo.*;
import java.util.*;

public class AppriesService extends SuperDao
{
    private static final Logger logger;
    
    static {
        logger = Logger.getLogger((Class)AppriesService.class.getClass());
    }
    
    public boolean addAppries(final AppriesVo appriesVo) {
        final String sql = "insert appries_appries (serviceDate,serviceCycle, CustorTime,DeptId, EmployeeId, serviceType, appriesTime, updateTime, JieshouTime, saveTime, remark, deptarl, ext1,ext2, ext3, ext4, keyno, mechinetype,videofile) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        final Object[] args = { appriesVo.getServiceDate(), appriesVo.getServiceCycle(), appriesVo.getCustorTime(), appriesVo.getDeptId(), appriesVo.getEmployeeId(), appriesVo.getServiceType(), appriesVo.getAppriesTime(), appriesVo.getUpdateTime(), appriesVo.getJieshouTime(), appriesVo.getSaveTime(), appriesVo.getRemark(), appriesVo.getDeptarl(), appriesVo.getExt1(), appriesVo.getExt2(), appriesVo.getExt3(), appriesVo.getExt4(), appriesVo.getKeyno(), appriesVo.getMechinetype(), appriesVo.getVideofile() };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean addArrires(final String mac, final String tt, final String cardnum, final String pj, final String demo, final String businessType, final String cf) {
        final String sql = "{call testAdd(?,?,?,?,?,?,?)}";
        final Object[] args = { mac, tt, cardnum, pj, demo, businessType, cf };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean addArrires(final String mac, final String tt, final String cardnum, final String pj, final String demo) {
        final String sql = "{call appriesAdd(?,?,?,?,?,?)}";
        final Object[] args = { mac, tt, cardnum, pj, demo, 1 };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean addArriresAuto(final String mac, final String tt, final String cardnum, final String pj, final String demo) {
        final String sql = "{call appriesAddAuto(?,?,?,?,?,?)}";
        final Object[] args = { mac, tt, cardnum, pj, demo, 1 };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean addArriresXiangYang(final String mac, final String tt, final String cardnum, final String pj, final String demo,
                                       final String videofile, final String businessTime, final int min, final int sec, final String tel,
                                       final String idCard, final String name, final String phone, String imgfile, String busRst) {
        final String sql = "{call appriesAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        final Object[] args = {
                mac==null?"":mac,
                tt==null?"":tt,
                cardnum==null?"":cardnum,
                pj==null?"":pj,
                demo==null?"":demo, 1,
                videofile==null?"":videofile,
                businessTime==null?"":businessTime,
                min,
                sec,
                name==null?"":name,
                phone==null?"":phone,
                imgfile==null?"":imgfile,
                busRst==null?"":busRst };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean addAppriesContact(final String mac, final String tt, final String name, final String phone, final String remark){
        if (tt==null || tt.equals("") || mac==null || mac.equals("")){
            return false;
        }
        final String sql = "update appries_appries set ext1=?, ext2=?, remark=? where JieshouTime=? and deptid in(select id from appries_dept where remark=?)";
        final Object[] args = {
                name==null?"":name,
                phone==null?"":phone,remark==null?"":remark,
                tt, mac
        };
        try {
            return this.getJdbcTemplate().update(sql, args)>0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addArrires2(final String mac, final String tt, final String cardnum, final String pj, final String demo, final String videofile, final String businessTime) {
        final String sql = "{call appriesAdd(?,?,?,?,?,?,?,?)}";
        final Object[] args = { mac, tt, cardnum, pj, demo, 1, videofile, businessTime };
        try {
            return this.getJdbcTemplate().update(sql, args)>0;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean addArrires3(final String mac, final String tt, final String cardnum, final String pj, final String demo, final String videofile, final String businessTime, final int min, final int sec) {
        final String sql = "{call appriesAdd(?,?,?,?,?,?,?,?,?,?)}";
        final Object[] args = { mac, tt, cardnum, pj, demo, 1, videofile, businessTime, min, sec };
        try {
            return this.getJdbcTemplate().update(sql, args)>0;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean addArrires(final String mac, final String tt, final String cardnum, final String pj, final String demo, final String businessTypeId) {
        final String sql = "{call appriesAdd(?,?,?,?,?,?)}";
        final Object[] args = { mac, tt, cardnum, pj, demo, businessTypeId };
        try {
            return this.getJdbcTemplate().update(sql, args)>0;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public Map getAppriesInfo(final String cardNum, final String mac, final String key) {
        final String sql = "select a.name as dating , a.id as datingid, b.name as window, b.id as windowid " +
                "from appries_dept a,appries_dept b where a.id =b.fatherid and b.remark='" + mac + "'";
        final String sql2 = "select id as  bumenid ,name as bumen from appries_dept where id= 1 ";
        final String sql3 = "select Name, deptid from appries_employee where cardnum='" + cardNum + "'";
        final String sql4 = "select name from appries_keytype where keyNo='" + key + "'";
        Map m = new HashMap();
        try {
            m = this.getJdbcTemplate().queryForMap(sql);
            Map temp = this.getJdbcTemplate().queryForMap(sql2);
            m.put("danwei", temp.get("name"));
            m.put("danweiid", temp.get("id"));
            temp = this.getJdbcTemplate().queryForMap(sql3);
            m.put("Name", temp.get("Name"));
            m.put("deptid", temp.get("deptid"));
            temp = this.getJdbcTemplate().queryForMap(sql4);
            m.put("keyname", temp.get("name"));
        }
        catch (Exception e) {
            m = null;
        }
        return m;
    }
    
    public int del(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate) {
        String sql = "delete from appries_appries where 1=1  ";
        if (!employeeId.equals(new Integer(0))) {
            sql = String.valueOf(sql) + " and appries_appries.EmployeeId=" + employeeId + " ";
        }
        if (!keys.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.keyno in (" + keys + ")  ";
        }
        if (!deptIds.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.DeptId in (" + deptIds + ")  ";
        }
        if (!startDate.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.JieshouTime  >='" + startDate + "' ";
        }
        if (!endDate.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.JieshouTime  <='" + endDate + "' ";
        }
        try {
            return this.getJdbcTemplate().update(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    public int del(final String Ids) {
        String sql = "delete from appries_appries   ";
        if (!Ids.equals("")) {
            sql = String.valueOf(sql) + " where  appries_appries.id in(" + Ids + ") ";
        }
        try {
            return this.getJdbcTemplate().update(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    public List getAllMac() {
        final String sql = "select * from appries_dept where remark!=''";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public List checkAppries(final String time, final String keyno, final String mac) {
        final String sql = "select * from appries_appries where JieshouTime='" + time + "' and keyno='" + keyno + "' and DeptId=(select id from appries_dept where remark='" + mac + "')";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
}
