package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.util.*;

public class AttendanceService extends SuperDao
{
    public boolean add(final AttendanceVo attendanceVo) {
        final String sql = "insert into appries_attendance (userId,attendDate,attendUp,attendDown)values(?,?,?,?)";
        final Object[] args = { attendanceVo.getUserId(), attendanceVo.getAttendDate(), attendanceVo.getAttendUp(), attendanceVo.getAttendDown() };
        try {
            this.getJdbcTemplate().update(sql, args);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean addToList(final int userid, final String aDate, final String aTime, final String deptName) {
        final String sql = "insert into appries_attendanceinfo (userId,attendDate,optionTime,deptName)values(?,?,?,?)";
        final Object[] args = { userid, aDate, aTime, deptName };
        try {
            this.getJdbcTemplate().update(sql, args);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean edit(final AttendanceVo attendanceVo) {
        final String sql = "update appries_attendance set userId=?,attendDate=?,attendUp=?,attendDown=? where attendanceId=? ";
        final Object[] args = { attendanceVo.getUserId(), attendanceVo.getAttendDate(), attendanceVo.getAttendUp(), attendanceVo.getAttendDown(), attendanceVo.getAttendanceId() };
        try {
            this.getJdbcTemplate().update(sql, args);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public int count(final String startDate, final String endDate) {
        final String sql = "select count(*) from appries_attendance where attendDate   >=left( '" + startDate + "',10)  and attendDate   <=left( '" + endDate + "',10)     ";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List query(final String startDate, final String endDate, final int start, final int num) {
        final String sql = "select appries_employee.Name ,appries_employee.id,appries_employee.CardNum ,appries_attendance.attendDate,appries_attendance.attendUp,appries_attendance.attendDown ,concat(appries_dept.name,'') as deptName from appries_attendance,appries_employee,appries_dept where appries_attendance.userId=appries_employee.Id and appries_dept.id=appries_employee.deptid and  attendDate   >=left( '" + startDate + "',10)  and attendDate   <=left( '" + endDate + "',10)   order by attendanceId desc limit " + start + "," + num;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public Map query(final String attendDate, final int userId) {
        final String sql = "select * from appries_attendance where attendDate='" + attendDate + "' and userId=" + userId;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public List queryNO(final String startDate, final String endDate) {
        final String sql = "select name from  appries_employee where   id not  in ( select userId from appries_attendance where concat(attendDate,' ',attendUp)  >='" + startDate + "'  and  concat(attendDate,' ',attendUp)<= '" + endDate + "')";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
