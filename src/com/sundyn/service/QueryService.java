package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.util.*;

public class QueryService extends SuperDao
{
    public List<DeptVo> queryDept(final String deptids, final String startDate, final String endDate, final int start, final int num) {
        String sql = "select temp_appries.id,temp_appries.videofile as videofile, temp_appries.businessTime as businessTime,temp_appries.businessMin as businessMin,temp_appries.businessSec as businessSec,appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', temp_appries.JieshouTime ,CONCAT(appries_dept.name,'') as 'deptName', temp_appries.serviceDate,temp_appries.appriesTime,temp_appries.CustorTime,appries_dept.dept_camera_url ,appries_dept.fatherId from  (select appries_appries.id, appries_appries.videofile,appries_appries.businessTime,appries_appries.businessMin,appries_appries.businessSec,appries_appries.DeptId,appries_appries.EmployeeId, appries_appries.JieshouTime,appries_appries.keyno, appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime  from appries_appries where appries_appries.DeptId in (" + deptids + ") " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "'  order by appries_appries.id desc  limit  " + start + "," + num + " )as temp_appries, appries_employee,appries_dept,appries_keytype " + "where  temp_appries.EmployeeId= appries_employee.Id " + "and temp_appries.DeptId=appries_dept.id " + "and temp_appries.keyno=appries_keytype.keyNo ";
        sql = " select a.*,CONCAT(b.name,'') as fatherName  from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id  ORDER BY a.JieshouTime DESC ";
        System.out.println("queryDept-sql=" + sql);
        try {
            return (List<DeptVo>)this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int countQueryDept(final String deptids, final String startDate, final String endDate) {
        final String sql = "select   count(*)from appries_appries where  appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' and  appries_appries.DeptId in (" + deptids + ") ";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public List QueryDeptChat(final String deptids, final String startDate, final String endDate) {
        final String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype where   appries_appries.keyno=appries_keytype.keyNo and appries_appries.DeptId in (" + deptids + ") " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + " and  appries_keytype.yes=1 group by  appries_keytype.name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryEmployee(final Integer employeeId, final String startDate, final String endDate, final int start, final int num) {
        String sql = "select appries_appries.id, appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', appries_appries.JieshouTime ,CONCAT(appries_dept.name,'') as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url ,appries_appries.videofile videofile,appries_appries.businessTime businessTime,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.EmployeeId =" + employeeId + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + " order by appries_appries.id desc  limit  " + start + "," + num;
        sql = " select a.*,CONCAT(b.name,'') as fatherName   from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id   ORDER BY a.JieshouTime DESC ";
        System.out.println("queryEmployee-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryEmployee2(final Integer employeeId, final String startDate, final String endDate, final int start, final int num, final String keys) {
        String sql = "select appries_appries.id, appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', appries_appries.JieshouTime,appries_appries.ext1,appries_appries.ext2,appries_appries.remark ,appries_appries.videofile,appries_appries.businessMin,appries_appries.businessSec,CONCAT(appries_dept.name,'') as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url ,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.EmployeeId =" + employeeId + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' and appries_appries.keyno in (" + keys + " ) " + " order by appries_appries.id desc  limit  " + start + "," + num;
        sql = " select a.*,CONCAT(b.name,'') as fatherName   from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id    ORDER BY a.JieshouTime DESC";
        System.out.println("queryEmployee-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int countQueryEmployee(final Integer employeeId, final String startDate, final String endDate) {
        final String sql = "select   count(*)from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.EmployeeId =" + employeeId + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' ";
        System.out.println("countQueryEmployee-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public int countQueryEmployee2(final Integer employeeId, final String startDate, final String endDate, final String keys) {
        final String sql = "select   count(*)from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.EmployeeId =" + employeeId + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' and appries_appries.keyno in (" + keys + " )";
        System.out.println("countQueryEmployee-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public List QueryEmployeeChat(final Integer employeeId, final String startDate, final String endDate) {
        final String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype where   appries_appries.keyno=appries_keytype.keyNo and appries_appries.EmployeeId =" + employeeId + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + " and appries_keytype.yes=1  group by  appries_keytype.name";
        System.out.println("QueryEmployeeChat-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List QueryEmployeeChat2(final Integer employeeId, final String startDate, final String endDate) {
        final String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype where   appries_appries.keyno=appries_keytype.keyNo and appries_appries.EmployeeId =" + employeeId + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.CustorTime  >='" + startDate + "' " + "and appries_appries.CustorTime  <='" + endDate + "' " + " and appries_keytype.yes=1  group by  appries_keytype.name";
        System.out.println("QueryEmployeeChat-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryResult(final String deptIds, final Integer result, final String startDate, final String endDate, final int start, final int num) {
        String sql = "select appries_appries.id, appries_appries.videofile videofile,appries_appries.businessTime businessTime, appries_appries.businessMin businessMin,appries_appries.businessSec businessSec,appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', appries_appries.ext1,appries_appries.ext2,appries_appries.remark,appries_appries.JieshouTime ,CONCAT(appries_dept.name,'') as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.keyno =" + result + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId in(" + deptIds + ") " + " order by appries_appries.id desc  limit  " + start + "," + num;
        sql = " select a.*,CONCAT(b.name,'') as fatherName  from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id   ORDER BY a.JieshouTime DESC ";
        System.out.println("queryResult-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryResult(final String deptIds, final Integer result, final String startDate, final String endDate) {
        String sql = "select appries_appries.id, appries_appries.videofile videofile,appries_appries.businessTime businessTime, appries_appries.businessMin businessMin,appries_appries.businessSec businessSec,appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', appries_appries.ext1,appries_appries.ext2,appries_appries.remark,appries_appries.JieshouTime ,CONCAT(appries_dept.name,'') as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.keyno =" + result + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId in(" + deptIds + ") " + " order by appries_appries.id desc";
        sql = " select a.*,CONCAT(b.name,'') as fatherName  from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id   ORDER BY a.JieshouTime DESC ";
        System.out.println("queryResult-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    public int countQueryResult(final String deptIds, final Integer result, final String startDate, final String endDate) {
        final String sql = "select   count(*)from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.keyno =" + result + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId  in (" + deptIds + ")";
        System.out.println("countQueryResult-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public List queryResultTel(final String deptIds, final String tel, final String startDate, final String endDate, final int start, final int num) {
        String sql = "select appries_appries.id,appries_appries.ext1,appries_appries.ext2,appries_appries.remark, appries_appries.videofile videofile,appries_appries.businessTime businessTime, appries_appries.businessMin businessMin,appries_appries.businessSec businessSec,appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', appries_appries.JieshouTime ,CONCAT(appries_dept.name,'') as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext1 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId in(" + deptIds + ") " + " order by appries_appries.id desc  limit  " + start + "," + num;
        sql = " select a.*,CONCAT(b.name,'') as fatherName  from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id   ORDER BY a.JieshouTime DESC ";
        System.out.println("queryResult-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryResultIdCard(final String deptIds, final String tel, final String startDate, final String endDate, final int start, final int num) {
        String sql = "select appries_appries.id,appries_appries.ext1,appries_appries.ext2,appries_appries.remark, appries_appries.videofile videofile,appries_appries.businessTime businessTime, appries_appries.businessMin businessMin,appries_appries.businessSec businessSec,appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', appries_appries.JieshouTime ,CONCAT(appries_dept.name,'') as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext2 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId in(" + deptIds + ") " + " order by appries_appries.id desc  limit  " + start + "," + num;
        sql = " select a.*,CONCAT(b.name,'') as fatherName  from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id    ORDER BY a.JieshouTime DESC";
        System.out.println("queryResult-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int countQueryResultTel(final String deptIds, final String tel, final String startDate, final String endDate) {
        final String sql = "select   count(*)from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext1 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId  in (" + deptIds + ")";
        System.out.println("countQueryResult-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public int countQueryResultIdCard(final String deptIds, final String tel, final String startDate, final String endDate) {
        final String sql = "select   count(*)from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext2 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId  in (" + deptIds + ")";
        System.out.println("countQueryResult-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }
    public List QueryResultChat(final Integer result, final String startDate, final String endDate){
        return QueryResultChat(result+"", startDate, endDate);
    }
    public List QueryResultChat(final String result, final String startDate, final String endDate) {
        String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype ";
        sql += "where appries_appries.keyno=appries_keytype.keyNo " ;
        if(null!=result && !"".equals(result))
            sql += "and appries_appries.keyno =" + result;
        sql += "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + " group by  appries_keytype.name";
        System.out.println("QueryResultChat-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List QueryResultChatTel(final String tel, final String startDate, final String endDate) {
        final String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype where   appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext1 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + " group by  appries_keytype.name";
        System.out.println("QueryResultChatTel-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List QueryResultChatIdCard(final String tel, final String startDate, final String endDate) {
        final String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype where   appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext2 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + " group by  appries_keytype.name";
        System.out.println("QueryResultChatTel-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryZh(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate, final Integer start, final Integer num) {
        String sql = "select appries_appries.id , appries_appries.videofile videofile,appries_appries.businessTime businessTime,appries_appries.businessMin businessMin,appries_appries.businessSec businessSec, appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', appries_appries.JieshouTime ,CONCAT(appries_dept.name,'') as 'deptName'  , appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url ,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo ";
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
        sql = String.valueOf(sql) + " order by    appries_appries.id  desc  ";
        if (start != null && num != null) {
            sql = String.valueOf(sql) + " limit  " + start + "," + num;
        }
        sql = " select a.*,CONCAT(b.name,'') as fatherName  from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id  ORDER BY a.JieshouTime DESC";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryZh2(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate, final Integer start, final Integer num) {
        String sql = "select appries_appries.id , appries_appries.videofile videofile,appries_appries.businessTime businessTime,appries_appries.businessMin businessMin,appries_appries.businessSec businessSec, appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', appries_appries.ext1,appries_appries.ext2,appries_appries.remark,appries_appries.JieshouTime ,CONCAT(appries_dept.name,'') as 'deptName'  , appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url ,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo ";
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
        sql = String.valueOf(sql) + " order by    appries_appries.id  desc  ";
        if (start != null && num != null) {
            sql = String.valueOf(sql) + " limit  " + start + "," + num;
        }
        sql = " select a.*,CONCAT(b.name,'') as fatherName  from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id ORDER BY a.JieshouTime DESC ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int countQueryZh(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate) {
        String sql = "select   count(*)from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo ";
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

    public int countQueryZh2(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate) {
        String sql = "select   count(*)from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo ";
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

    public List QueryZhChat(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate) {
        String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype where   appries_appries.keyno=appries_keytype.keyNo ";
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
        sql = String.valueOf(sql) + "  and appries_keytype.yes=1 group by  appries_keytype.name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List QueryZhChat2(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate) {
        String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype where   appries_appries.keyno=appries_keytype.keyNo ";
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
        sql = String.valueOf(sql) + "  and appries_keytype.yes=1 group by  appries_keytype.name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryType(final String ids, final String keys, final Integer num) {
        final String sql = "select  appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', temp.JieshouTime ,temp.serviceDate,temp.CustorTime,temp.appriesTime,appries_dept.dept_camera_url from (select id,EmployeeId,DeptId,keyno,JieshouTime,serviceDate,CustorTime,appriesTime from appries_appries where appries_appries.DeptId in (" + ids + ") and appries_appries.keyno  in (" + keys + ")  order by appries_appries.id desc  limit  " + 0 + "," + num + "  ) as temp,appries_employee,appries_dept,appries_keytype " + "where  temp.EmployeeId= appries_employee.Id " + "and temp.DeptId=appries_dept.id " + "and temp.keyno=appries_keytype.keyNo order by temp.JieshouTime desc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List queryTypeByDoor(final String ids, final String keys, final Integer num) {
        final String sql = "select  CONCAT(appries_dept.remark) as 'CardNum' ,CONCAT(appries_dept.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', temp.JieshouTime ,temp.serviceDate,temp.CustorTime,temp.appriesTime,appries_dept.dept_camera_url from (select id,EmployeeId,DeptId,keyno,JieshouTime,serviceDate,CustorTime,appriesTime from appries_appries where appries_appries.DeptId in (" + ids + ") and appries_appries.keyno  in (" + keys + ")  order by appries_appries.id desc  limit  " + 0 + "," + num + "  ) as temp,appries_employee,appries_dept,appries_keytype " + "where  temp.DeptId=appries_dept.id " + "and temp.keyno=appries_keytype.keyNo order by temp.JieshouTime desc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map queryById(final Integer id) {
        final String sql = "select * from appries_appries  where id=" + id;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List queryAppriesForDel(final String deptIds, final String startDate, final String endDate) {
        final String sql = "SELECT * FROM appries_appries where DeptId IN (" + deptIds + ") and JieshouTime>'" + startDate + "' and JieshouTime<'" + endDate + "'";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List queryAppriesForDel(final String deptIds, final String keyNo, final String startDate, final String endDate) {
        final String sql = "SELECT * FROM appries_appries where DeptId IN (" + deptIds + ") and keyNo=" + keyNo + " and JieshouTime>'" + startDate + "' and JieshouTime<'" + endDate + "'";
        System.out.println("queryAppriesForDel-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateAppriesById(final String id) {
        final String sql = "update appries_appries set videofile=NULL where id=" + id;
        try {
            this.getJdbcTemplate().update(sql);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countQueryDept2(final String deptids, final String startDate, final String endDate, final String keys) {
        final String sql = "select   count(*)from appries_appries where  appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' and  appries_appries.DeptId in (" + deptids + ") and keyno in (" + keys + ")";
        System.out.println("countQueryDept2-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public List queryDept2(final String deptids, final String startDate, final String endDate, final int start, final int num, final String keys) {
        String sql = "select temp_appries.id, appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', temp_appries.JieshouTime ,temp_appries.ext1,temp_appries.ext2,temp_appries.remark,CONCAT(appries_dept.name,'') as 'deptName', temp_appries.serviceDate,temp_appries.appriesTime,temp_appries.CustorTime,temp_appries.videofile,temp_appries.businessMin,temp_appries.businessSec,appries_dept.dept_camera_url ,appries_dept.fatherId from  (select appries_appries.id, appries_appries.DeptId,appries_appries.EmployeeId, appries_appries.JieshouTime,appries_appries.ext1,appries_appries.ext2,appries_appries.remark,appries_appries.keyno, appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime,appries_appries.videofile,appries_appries.businessMin,appries_appries.businessSec  from appries_appries where appries_appries.DeptId in (" + deptids + ") and keyno in (" + keys + ") and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "'  order by appries_appries.id desc  limit  " + start + "," + num + " )as temp_appries, appries_employee,appries_dept,appries_keytype " + "where  temp_appries.EmployeeId= appries_employee.Id " + "and temp_appries.DeptId=appries_dept.id " + "and temp_appries.keyno=appries_keytype.keyNo ";
        sql = " select a.*,CONCAT(b.name,'') as fatherName  from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id    ORDER BY a.JieshouTime DESC";
        System.out.println("queryDept2-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
}
