package com.sundyn.service;

import com.sundyn.dao.*;
import java.util.*;

public class ThreeService extends SuperDao
{
    public boolean del() {
        final String sql = "delete from appries_three";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<?> queryAll() {
        final String sql = "select * from appries_three  order by RealName ,ext9 ";
        try {
            return (List<?>)this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<?> query(final boolean flag) {
        String sql = "";
        if (flag) {
            sql = "select * from appries_three where appriesId <> 0  order by RealName ,ext9 ";
        }
        else {
            sql = "select * from appries_three where appriesId = 0   order by RealName ,ext9 ";
        }
        try {
            return (List<?>)this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean match(final String ext1, final String appriesId) {
        final String sql = "update appries_three set appriesId=? where ext1=?";
        final Object[] args = { appriesId, ext1 };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean add(final String[] args) {
        final String sql = "insert into  appries_three (ext1,ext2,ext3,ext4,ext5,ext6,ext7,ext8,ext9,userName,RealName,ext12,ext13,ext14) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            final int num = this.getJdbcTemplate().update(sql, (Object[])args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List queryAppries(final String startDate, final String endDate) {
        final String sql = "select appries_appries.id,appries_employee.Name,appries_employee.ext2,appries_appries.JieshouTime  from appries_appries,appries_employee   where  appries_appries.EmployeeId= appries_employee.Id  and  appries_appries.JieshouTime<= '" + endDate + "' " + " and  appries_appries.JieshouTime>= '" + startDate + "' " + " and  appries_appries.id  not in ( select appriesID from appries_three)" + " order by  appries_employee.Name, appries_appries.JieshouTime";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String getMaxDate() {
        final String sql = "select max(ext9) from appries_three";
        try {
            return (String)this.getJdbcTemplate().queryForObject(sql, (Class)String.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public String getMinDate() {
        final String sql = "select min(ext9) from appries_three";
        try {
            return (String)this.getJdbcTemplate().queryForObject(sql, (Class)String.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public List query(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate, final Integer start, final Integer num) {
        String sql = "select appries_three.ext1,appries_three.userName ,appries_three.ext9 ,appries_three.ext5,appries_appries.id , appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', appries_appries.JieshouTime ,CONCAT(appries_dept.name,'') as 'deptName'  , appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url ,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype,appries_three where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.id=appries_three.appriesID ";
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
        if (start != null && num != null) {
            sql = String.valueOf(sql) + " order by  employeeName, appries_appries.JieshouTime limit  " + start + "," + num;
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public int countQuery(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate) {
        String sql = "select   count(*) from appries_appries,appries_employee,appries_dept,appries_keytype,appries_three where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.id=appries_three.appriesID";
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
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }
}
