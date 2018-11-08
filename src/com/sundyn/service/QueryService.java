package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.sundyn.vo.DeptVo;
import com.xuan.xutils.utils.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class QueryService extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    /*public List<DeptVo> queryDept(final String deptids, final String startDate, final String endDate, final int start, final int num) {
        String sql = "select row_number() over(order by appries_appries.id desc) as rows, temp_appries.id,temp_appries.videofile as videofile, temp_appries.businessTime as businessTime,temp_appries.businessMin as businessMin,temp_appries.businessSec as businessSec,appries_employee.CardNum ,appries_employee.Name as 'employeeName',appries_keytype.name as 'keyName', temp_appries.JieshouTime ,appries_dept.name as 'deptName', temp_appries.serviceDate,temp_appries.appriesTime,temp_appries.CustorTime,appries_dept.dept_camera_url ,appries_dept.fatherId from  (select appries_appries.id, appries_appries.videofile,appries_appries.businessTime,appries_appries.businessMin,appries_appries.businessSec,appries_appries.DeptId,appries_appries.EmployeeId, appries_appries.JieshouTime,appries_appries.keyno, appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime  from appries_appries where appries_appries.DeptId in (" + deptids + ") " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "'";
        sql += ")as temp_appries, appries_employee,appries_dept,appries_keytype " + "where  temp_appries.EmployeeId= appries_employee.Id " + "and temp_appries.DeptId=appries_dept.id " + "and temp_appries.keyno=appries_keytype.keyNo ";
        sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        sql = " select a.*,b.name as fatherName from (" + sql + ") as a left join appries_dept b on a.fatherId=b.id  ORDER BY a.JieshouTime DESC";
        try {
            return (List<DeptVo>)this.getJdbcTemplate().queryForList(sql,null,DeptVo.class);
        }
        catch (Exception e) {
            return null;
        }
    }*/

    public int countQueryDept(final String deptids, final String startDate, final String endDate) {
        final String sql = "select count(*) from appries_appries where  appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' and  appries_appries.DeptId in (" + deptids + ") ";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public List QueryDeptChat(final String deptids, final String startDate, final String endDate, String keynos) {
        String sql = "select ISNULL(appries_keytype.name,'未评价') name,count(*) as num from appries_appries left join appries_keytype on appries_appries.keyno=appries_keytype.keyNo where 1=1 ";
        if(null!=deptids && !"".equals(deptids))
            sql += "and appries_appries.DeptId in (" + deptids + ") ";
        sql += "and appries_appries.JieshouTime>='" + startDate + "' " + "and appries_appries.JieshouTime<='" + endDate + "' ";
        if (!StringUtils.isBlank(keynos))
            sql += "and appries_appries.keyno in("+keynos+") ";
        sql += "group by appries_keytype.name";
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryEmployee(final Integer employeeId, final String startDate, final String endDate, final int start, final int num) {
        String sql = "select row_number() over(order by appries_appries.id desc) as rows, appries_appries.id, appries_employee.CardNum ,appries_employee.Name as 'employeeName'," +
                "appries_keytype.name as 'keyName', appries_appries.JieshouTime ,appries_dept.name as 'deptName', " +
                "appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url ,appries_appries.videofile videofile," +
                "appries_appries.businessTime businessTime,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype " +
                "where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo " +
                "and appries_appries.EmployeeId =" + employeeId + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " +
                "and appries_appries.JieshouTime  <='" + endDate + "' ";
        sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        sql = " select a.*,b.name as fatherName from  (" + sql + ")   as a left join appries_dept b  on  a.fatherId=b.id   ORDER BY a.JieshouTime DESC ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryEmployee2(final String deptIds, final Integer employeeId, final String startDate, final String endDate,
                               final int start, final int num, final String keys, int[] rc) {
        String sql = "select row_number() over(order by appries_appries.JieshouTime desc) as rows, appries_appries.id, appries_employee.CardNum ," +
                "appries_employee.Name as 'employeeName',ISNULL(appries_keytype.name,'未评价') as 'keyName', appries_appries.JieshouTime," +
                "appries_appries.ext1,appries_appries.ext2,appries_appries.remark ,appries_appries.videofile,appries_appries.businessMin," +
                "appries_appries.businessSec,appries_dept.name as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime," +
                "appries_appries.CustorTime , appries_appries.imgfile,appries_dept.dept_camera_url ,appries_dept.fatherId,appries_appries.ywlsh from appries_appries " +
                "left join appries_keytype on appries_appries.keyno=appries_keytype.keyNo, appries_employee," +
                "appries_dept where appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id ";
        if(null!=deptIds && !deptIds.equals(""))
            sql += "and appries_dept.id in(" + deptIds + ") ";
        if(null!=employeeId && employeeId!=0)
                sql += "and appries_appries.EmployeeId =" + employeeId + " ";
        if(null!=startDate && startDate!="")
            sql += "and appries_appries.JieshouTime  >='" + startDate + "' ";
        if(null!=endDate && endDate!="")
            sql += "and appries_appries.JieshouTime  <='" + endDate + "' ";
        if(null!=keys && keys!="")
            sql += "and appries_appries.keyno in (" + keys + " ) ";
        if (rc!=null && rc.length==1){
            String countSql = "select count(*) from (" + sql + ")t";
            rc[0] = this.getJdbcTemplate().queryForObject(countSql,null, java.lang.Integer.class);
        }
        if(num>0)
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);

        sql = " select a.*,b.name as fatherName,c.*,d.countername as hjcountername,dbo.FN_SecondToString(waittime) as waittimename, " +
                "dbo.FN_SecondToString(servicetime) as servicetimename from  (" + sql + ") as a left join appries_dept b on a.fatherId=b.id " +
                "left join v_queuedetail c on a.ywlsh=c.ywlsh left join sys_queuecounter d on d.counterno=c.hjcounter " +
                "ORDER BY a.JieshouTime DESC";
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int countQueryEmployee(final Integer employeeId, final String startDate, final String endDate) {
        final String sql = "select   count(*)from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.EmployeeId =" + employeeId + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' ";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
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

    public List QueryEmployeeChat2(final String deptIds, final Integer employeeId, final String startDate, final String endDate, String keynos) {
        String sql = "select ISNULL(appries_keytype.name,'未评价') name,count(*) as num from appries_appries left join appries_keytype on appries_appries.keyno=appries_keytype.keyNo, appries_dept " +
                "where appries_appries.deptid=appries_dept.id ";
        if(employeeId!=null)
            sql += "and appries_appries.EmployeeId =" + employeeId + " ";
        if(deptIds!=null && !deptIds.equals(""))
            sql += "and appries_dept.id in(" + deptIds + ") ";

        sql += "and appries_appries.JieshouTime  >='" + startDate + "' " +
                "and appries_appries.JieshouTime  <='" + endDate + "' " +
                "and appries_appries.CustorTime  >='" + startDate + "' " +
                "and appries_appries.CustorTime  <='" + endDate + "' ";
        if (!StringUtils.isBlank(keynos))
            sql += "and appries_appries.keyno in("+keynos+") ";
        sql += "group by  appries_keytype.name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryResult(final String deptIds, final Integer result, final String startDate, final String endDate, final int start, final int num, String allKeyInUse, int[] rowsCount) {
        String sql = "select row_number() over(order by appries_appries.JieshouTime desc) as rows,appries_appries.id, appries_appries.videofile videofile,appries_appries.imgfile," +
                "appries_appries.businessTime businessTime, appries_appries.businessMin businessMin,appries_appries.businessSec businessSec,appries_employee.CardNum ," +
                "appries_employee.Name as 'employeeName',ISNULL(appries_keytype.name,'未评价') as 'keyName', appries_appries.ext1,appries_appries.ext2,appries_appries.remark,appries_appries.JieshouTime ," +
                "appries_dept.name as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url,appries_dept.fatherId,appries_appries.ywlsh " +
                "from appries_appries left join appries_keytype on appries_appries.keyno=appries_keytype.keyNo,appries_employee,appries_dept " +
                "where appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id ";
        if(null!=result)
            sql += "and appries_appries.keyno =" + result + " ";
        sql += "and appries_appries.JieshouTime>='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId in(" + deptIds + ") ";
        if (!allKeyInUse.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.keyno in (" + allKeyInUse + ")  ";
        }
        if(null!=rowsCount && rowsCount.length==1){
            String countSql = "select count(*) from ("+sql+") t";
            rowsCount[0] = this.getJdbcTemplate().queryForObject(countSql, null, Integer.class);
        }
        if (num>0)
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        sql = " select a.*,b.name as fatherName,c.*,d.countername as hjcountername,dbo.FN_SecondToString(waittime) as waittimename, " +
                "dbo.FN_SecondToString(servicetime) as servicetimename " +
                "from  (" + sql + ") as a left join appries_dept b on a.fatherId=b.id " +
                "left join v_queuedetail c on a.ywlsh=c.ywlsh " +
                "left join sys_queuecounter d on d.counterno=c.hjcounter " +
                "ORDER BY a.JieshouTime DESC ";
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List queryResult(final String deptIds, final Integer result, final String startDate, final String endDate) {
        String sql = "select appries_appries.id, appries_appries.videofile videofile,appries_appries.businessTime businessTime, appries_appries.businessMin businessMin,appries_appries.businessSec businessSec,appries_employee.CardNum ," +
                "appries_employee.Name as 'employeeName',appries_keytype.name as 'keyName', appries_appries.ext1,appries_appries.ext2,appries_appries.remark,appries_appries.JieshouTime ," +
                "appries_dept.name as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url,appries_dept.fatherId " +
                "from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id " +
                "and appries_appries.keyno=appries_keytype.keyNo and appries_appries.keyno =" + result + " " +
                "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " +
                "and appries_appries.DeptId in(" + deptIds + ") " + " order by appries_appries.id desc";
        sql = " select a.*,b.name as fatherName  from  (" + sql + ") as a left join appries_dept b  on  a.fatherId=b.id ORDER BY a.JieshouTime DESC ";
        System.out.println("queryResult-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryResultTel(final String deptIds, final String tel, final String startDate, final String endDate, final int start, final int num) {
        String sql = "select row_number() over(order by appries_appries.id desc) as rows, appries_appries.id,appries_appries.ext1,appries_appries.ext2,appries_appries.remark, appries_appries.videofile videofile," +
                "appries_appries.businessTime businessTime, appries_appries.businessMin businessMin,appries_appries.businessSec businessSec,appries_employee.CardNum ," +
                "appries_employee.Name as 'employeeName',appries_keytype.name as 'keyName', appries_appries.JieshouTime ,appries_dept.name as 'deptName', " +
                "appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url,appries_dept.fatherId " +
                "from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id " +
                "and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext1 =" + tel + " " +
                "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " +
                "and appries_appries.DeptId in(" + deptIds + ") ";
        sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        sql = " select a.*,b.name as fatherName  from  (" + sql + ") as a left join appries_dept b  on  a.fatherId=b.id ORDER BY a.JieshouTime DESC ";
        System.out.println("queryResult-sql" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryResultIdCard(final String deptIds, final String tel, final String startDate, final String endDate, final int start, final int num, final String allKeyInUse, int[] rowsCount) {
        String sql = "select row_number() over(order by appries_appries.id desc) as rows, appries_appries.id,appries_appries.ext1,appries_appries.ext2,appries_appries.remark, " +
                "appries_appries.videofile videofile,appries_appries.businessTime businessTime, appries_appries.businessMin businessMin,appries_appries.businessSec businessSec," +
                "appries_employee.CardNum ,appries_employee.Name as 'employeeName',appries_keytype.name as 'keyName', appries_appries.JieshouTime ,appries_dept.name as 'deptName', " +
                "appries_appries.serviceDate,appries_appries.appriesTime,appries_appries.CustorTime ,appries_dept.dept_camera_url,appries_dept.fatherId " +
                "from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id " +
                "and appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext2 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " +
                "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId in(" + deptIds + ") ";
        if (!allKeyInUse.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.keyno in (" + allKeyInUse + ")  ";
        }
        if(null!=rowsCount && rowsCount.length==1){
            String countSql = "select count(*) from ("+sql+") t";
            rowsCount[0] = this.getJdbcTemplate().queryForObject(countSql, null, Integer.class);
        }

        sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);

        sql = "select a.*,b.name as fatherName  from  (" + sql + ") as a left join appries_dept b  on  a.fatherId=b.id ORDER BY a.JieshouTime DESC";
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int countQueryResultTel(final String deptIds, final String tel, final String startDate, final String endDate) {
        final String sql = "select   count(*)from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext1 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + "and appries_appries.DeptId  in (" + deptIds + ")";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public List QueryResultChat(final String deptIds, final Integer result, final String startDate, final String endDate, String allKeyInUse){
        return QueryResultChat(deptIds, result==null?"":String.valueOf(result), startDate, endDate, allKeyInUse);
    }

    public List QueryResultChat(final String deptIds, final String result, final String startDate, final String endDate, String allKeyInUse) {
        String sql = "select ISNULL(appries_keytype.name,'未评价') name ,count(*) as num from appries_appries left join appries_keytype ";
        sql += "on appries_appries.keyno=appries_keytype.keyNo where 1=1 " ;
        if(null!=result && !"".equals(result))
            sql += "and appries_appries.keyno =" + result + " ";
        if (!StringUtils.isBlank(allKeyInUse))
            sql += "and appries_appries.keyno in("+allKeyInUse+") ";
        if(null!=deptIds && !"".equals(deptIds))
            sql += "and appries_appries.deptid in(" + deptIds + ") ";
        sql += "and appries_appries.JieshouTime>='" + startDate + "' " + "and appries_appries.JieshouTime<='" + endDate + "' " + " group by appries_keytype.name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List QueryResultChatTel(final String tel, final String startDate, final String endDate) {
        final String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype where   appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext1 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + " group by  appries_keytype.name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List QueryResultChatIdCard(final String tel, final String startDate, final String endDate) {
        final String sql = "select  appries_keytype.name ,count(*) as num from appries_appries ,appries_keytype where   appries_appries.keyno=appries_keytype.keyNo and appries_appries.ext2 =" + tel + " " + "and appries_appries.JieshouTime  >='" + startDate + "' " + "and appries_appries.JieshouTime  <='" + endDate + "' " + " group by  appries_keytype.name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryZh(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate, final Integer start, final Integer num) {
        String sql = "select row_number() over(order by appries_appries.id  desc) as rows, appries_appries.id , appries_appries.videofile videofile,appries_appries.businessTime businessTime," +
                "appries_appries.businessMin businessMin,appries_appries.businessSec businessSec, appries_employee.CardNum ,appries_employee.Name as 'employeeName'," +
                "appries_keytype.name as 'keyName', appries_appries.JieshouTime ,appries_dept.name as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime," +
                "appries_appries.CustorTime ,appries_dept.dept_camera_url ,appries_dept.fatherId from appries_appries,appries_employee,appries_dept,appries_keytype " +
                "where appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo ";
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
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        sql = " select a.*, b.name as fatherName from  (" + sql + ")   as a left join appries_dept b on  a.fatherId=b.id  ORDER BY a.JieshouTime DESC";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryZh2(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate, final Integer start,
                         final Integer num, int[] rowscount) {
        String sql = "select row_number() over(order by appries_appries.JieshouTime desc) as rows, appries_appries.id , appries_appries.videofile videofile," +
                "appries_appries.imgfile imgfile,appries_appries.businessTime businessTime,appries_appries.businessMin businessMin,appries_appries.businessSec businessSec, " +
                "appries_employee.CardNum ,appries_employee.Name as 'employeeName',ISNULL(appries_keytype.name,'未评价') as 'keyName', appries_appries.ext1,appries_appries.ext2," +
                "appries_appries.remark,appries_appries.JieshouTime ,appries_dept.name as 'deptName', appries_appries.serviceDate,appries_appries.appriesTime," +
                "appries_appries.CustorTime ,appries_dept.dept_camera_url ,appries_dept.fatherId,appries_appries.ywlsh " +
                "from appries_appries left join appries_keytype on appries_appries.keyno=appries_keytype.keyNo,appries_employee,appries_dept " +
                "where appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id ";
        if (employeeId!=null && !employeeId.equals(new Integer(0))) {
            sql = String.valueOf(sql) + " and appries_appries.EmployeeId=" + employeeId + " ";
        }
        if (!keys.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.keyno in (" + keys + ")  ";
        }
        if (deptIds!=null && !deptIds.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.DeptId in (" + deptIds + ")  ";
        }
        if (startDate!=null && !startDate.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.JieshouTime  >='" + startDate + "' ";
        }
        if (endDate!=null && !endDate.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.JieshouTime  <='" + endDate + "' ";
        }
        if(null!=rowscount && rowscount.length==1){
            String countSql = "select count(*) from ("+sql+") t";
            rowscount[0] = this.getJdbcTemplate().queryForObject(countSql, null, Integer.class);
        }
        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        sql = " select a.*,b.name as fatherName,c.*,d.countername as hjcountername,dbo.FN_SecondToString(waittime) as waittimename," +
                "dbo.FN_SecondToString(servicetime) as servicetimename " +
                "from  (" + sql + ") as a left join appries_dept b on a.fatherId=b.id " +
                "left join v_queuedetail c on a.ywlsh=c.ywlsh " +
                "left join sys_queuecounter d on d.counterno=c.hjcounter " +
                "ORDER BY a.JieshouTime DESC";
        try {
            logger.info(sql);
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
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public int countQueryZh2(final Integer employeeId, final String keys, final String deptIds, final String startDate, final String endDate) {
        String sql = "select   count(*) from appries_appries,appries_employee,appries_dept,appries_keytype where  appries_appries.EmployeeId= appries_employee.Id and appries_appries.DeptId=appries_dept.id and appries_appries.keyno=appries_keytype.keyNo ";
        if (employeeId!= null && !employeeId.equals(new Integer(0))) {
            sql = String.valueOf(sql) + " and appries_appries.EmployeeId=" + employeeId + " ";
        }
        if (!keys.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.keyno in (" + keys + ")  ";
        }
        if (deptIds!=null && !deptIds.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.DeptId in (" + deptIds + ")  ";
        }
        if (!startDate.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.JieshouTime  >='" + startDate + "' ";
            sql = String.valueOf(sql) + " and appries_appries.JieshouTime  >='" + startDate + "' ";
        }
        if (endDate!=null && !endDate.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.JieshouTime  <='" + endDate + "' ";
        }
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
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
        String sql = "select ISNULL(appries_keytype.name, '未评价') name,count(*) as num from appries_appries left join appries_keytype on appries_appries.keyno=appries_keytype.keyNo where 1=1 ";
        if (null!=employeeId && !employeeId.equals(new Integer(0))) {
            sql = String.valueOf(sql) + " and appries_appries.EmployeeId=" + employeeId + " ";
        }
        if (!keys.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.keyno in (" + keys + ")  ";
        }
        if (!deptIds.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.DeptId in (" + deptIds + ")  ";
        }
        if (!startDate.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.JieshouTime>='" + startDate + "' ";
        }
        if (null!= endDate && !endDate.equals("")) {
            sql = String.valueOf(sql) + " and appries_appries.JieshouTime<='" + endDate + "' ";
        }
        sql = String.valueOf(sql) + " group by  appries_keytype.name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryType(final String ids, final String keys, final Integer num) {
        if(ids == null || ids.equals(""))
            return null;
        final String sql_sqlserver = "select appries_employee.CardNum ,appries_employee.Name as 'employeeName'," +
                "appries_keytype.name as 'keyName', temp.JieshouTime ,temp.serviceDate,temp.CustorTime,temp.appriesTime,appries_dept.dept_camera_url from (select top " + num+" id,EmployeeId,DeptId,keyno,JieshouTime,serviceDate,CustorTime,appriesTime from appries_appries where appries_appries.DeptId in (" + ids + ") and appries_appries.keyno  in (" + keys + ")  order by appries_appries.id desc) as temp,appries_employee,appries_dept,appries_keytype " + "where  temp.EmployeeId= appries_employee.Id " + "and temp.DeptId=appries_dept.id " + "and temp.keyno=appries_keytype.keyNo order by temp.JieshouTime desc";
        //final String sql_mysql = "select  appries_employee.CardNum ,CONCAT(appries_employee.Name,'') as 'employeeName',CONCAT(appries_keytype.name,'') as 'keyName', temp.JieshouTime ,temp.serviceDate,temp.CustorTime,temp.appriesTime,appries_dept.dept_camera_url from (select id,EmployeeId,DeptId,keyno,JieshouTime,serviceDate,CustorTime,appriesTime from appries_appries where appries_appries.DeptId in (" + ids + ") and appries_appries.keyno  in (" + keys + ")  order by appries_appries.id desc  limit  " + 0 + "," + num + "  ) as temp,appries_employee,appries_dept,appries_keytype " + "where  temp.EmployeeId= appries_employee.Id " + "and temp.DeptId=appries_dept.id " + "and temp.keyno=appries_keytype.keyNo order by temp.JieshouTime desc";
        logger.debug("queryType：" + sql_sqlserver);
        try {
            return this.getJdbcTemplate().queryForList(sql_sqlserver);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryTypeByDoor(final String ids, final String keys, final Integer num) {
        String sql = "select row_number() over(order by appries_appries.id desc) as rows, appries_dept.remark as 'CardNum' ,appries_dept.Name as 'employeeName',appries_keytype.name as 'keyName', temp.JieshouTime ,temp.serviceDate,temp.CustorTime,temp.appriesTime,appries_dept.dept_camera_url from (select id,EmployeeId,DeptId,keyno,JieshouTime,serviceDate,CustorTime,appriesTime from appries_appries where appries_appries.DeptId in (" + ids + ") and appries_appries.keyno  in (" + keys + ")";
        sql = "select * from ("+sql+") t where t.rows>" + 0 + " and t.rows<=" + (num);
        //"  order by appries_appries.id desc  limit  " + 0 + "," + num + "  ) as temp,appries_employee,appries_dept,appries_keytype " + "where  temp.DeptId=appries_dept.id " + "and temp.keyno=appries_keytype.keyNo order by temp.JieshouTime desc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Map queryById(final Integer id) {
        final String sql = "select * from appries_appries  where id=" + id;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryAppriesForDel(final String deptIds, final String startDate, final String endDate) {
        final String sql = "SELECT * FROM appries_appries where DeptId IN (" + deptIds + ") and JieshouTime>'" + startDate + "' and JieshouTime<'" + endDate + "'";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryAppriesForDel(final String deptIds, final String keyNo, final String startDate, final String endDate) {
        final String sql = "SELECT * FROM appries_appries where DeptId IN (" + deptIds + ") and keyNo=" + keyNo + " and JieshouTime>'" + startDate + "' and JieshouTime<'" + endDate + "'";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
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
            return false;
        }
    }

    public int countQueryDept2(final String deptids, final String startDate, final String endDate, final String keys) {
        String sql = "select count(*) from appries_appries where 1=1 ";
        if(startDate!=null && startDate!="")
            sql += "and appries_appries.JieshouTime  >='" + startDate + "' ";
        if(endDate!=null && endDate!="")
            sql += "and appries_appries.JieshouTime  <='" + endDate + "' ";
        if(deptids!=null && deptids!="")
            sql += "and  appries_appries.DeptId in (" + deptids + ") ";
        if(keys!=null && keys!="")
            sql += "and keyno in (" + keys + ")";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public List queryDept2(final String deptids, final String startDate, final String endDate, final int start, final int num, final String keys, int[] rowcounts) {
        String sql = "select row_number() over(order by temp_appries.JieshouTime desc) as rows, temp_appries.id, appries_employee.CardNum ," +
                "appries_employee.Name as 'employeeName',ISNULL(appries_keytype.name,'未评价') as 'keyName', " +
                "temp_appries.JieshouTime ,temp_appries.ext1,temp_appries.ext2,temp_appries.remark,appries_dept.name as 'deptName', " +
                "temp_appries.serviceDate,temp_appries.appriesTime,temp_appries.CustorTime,temp_appries.videofile,temp_appries.businessMin,temp_appries.ywlsh," +
                "temp_appries.businessSec, temp_appries.imgfile, appries_dept.dept_camera_url ,appries_dept.fatherId " +
                "from (select * from appries_appries where 1=1 ";
        if(null!=deptids && deptids !="")
            sql += "and appries_appries.DeptId in (" + deptids + ") ";
        if(null!=keys && keys !="")
            sql +=  "and keyno in (" + keys + ") ";
        if(null!=startDate && startDate !="")
            sql += "and appries_appries.JieshouTime  >='" + startDate + "' ";
        if(null!=endDate && endDate !="")
            sql += "and appries_appries.JieshouTime  <='" + endDate + "'";
        sql += ")as temp_appries left join appries_keytype on temp_appries.keyno=appries_keytype.keyNo, appries_employee,appries_dept where temp_appries.EmployeeId= appries_employee.Id "
                + "and temp_appries.DeptId=appries_dept.id ";
        if (rowcounts!=null && rowcounts.length==1){
            String countsql = "select count(*) from(" + sql + ") t";
            rowcounts[0] = this.getJdbcTemplate().queryForObject(countsql,null, java.lang.Integer.class);
        }

        if(num>0)
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        sql = " select a.*,b.name as fatherName,c.*,d.countername as hjcountername,dbo.FN_SecondToString(waittime) as waittimename," +
                "dbo.FN_SecondToString(servicetime) as servicetimename " +
                "from  (" + sql + ") as a left join appries_dept b on a.fatherId=b.id " +
                "left join v_queuedetail c on a.ywlsh=c.ywlsh " +
                "left join sys_queuecounter d on d.counterno=c.hjcounter " +
                "ORDER BY a.JieshouTime DESC";
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List queryDept2Ex(final String deptids,String bizname, Integer employeeid,Integer keyno, final String startDate, final String endDate, final int start, final int num, final String keys, int[] rowcounts) {
        String sql = "select row_number() over(order by tickettime desc) as rows,* from v_queuedetail where 1=1 ";
        if(null!=deptids && deptids !="")
            sql += "and deptid in (" + deptids + ") ";
        if(null!=employeeid && employeeid!=0)
            sql += "and staffno=(select cardnum from appries_employee where id=" + employeeid + ") ";
        if(null!=keyno && keyno!= -1)
            sql += "and appriseresult=" + keyno + " ";
        //if(null!=keys && keys !="")
        //    sql +=  "and appriseresult in (" + keys + ") ";
        if(StringUtils.isNotBlank(bizname))
            sql += "and bizname like '%"+bizname+"%' ";
        if(null!=startDate && startDate !="")
            sql += "and tickettime>='" + startDate + "' ";
        if(null!=endDate && endDate !="")
            sql += "and tickettime<='" + endDate + "'";
        if (rowcounts!=null && rowcounts.length==1){
            String countsql = "select count(*) from(" + sql + ") t";
            rowcounts[0] = this.getJdbcTemplate().queryForObject(countsql,null, java.lang.Integer.class);
        }
        if(num>0)
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        sql = "select t1.*,dbo.FN_SecondToString(waittime) as waittimename,dbo.FN_SecondToString(servicetime) as servicetimename," +
                "t3.countername as hjcountername,null videofile,null imgfile " +
                "from("+sql+") t1 " +
                "left join sys_queuecounter t3 on t1.hjcounter=t3.counterno and t1.hallno=t3.hallno order by tickettime desc";
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    /*
    最近n条评价
     */
    public List getLstAppries(int n, String deptid) {
        String sql = "select top "+n+" t2.name appriesname,t1.apprisetime,t3.name countername, t1.staffname from queue_detail t1 join appries_keytype t2 on t1.appriseresult=t2.keyno " +
                "join appries_dept t3 on t3.id=t1.hjcounterdeptid " +
                "where apprisetime is not null and appriseresult!=-1 ";
        if (StringUtils.isNotBlank(deptid)){
            sql += " and deptid in("+deptid+")";
        }
        sql += "order by apprisetime desc";
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    /*
    当前人流量-当天业务总量，人流量（取号且未办结）
     */
    public List getQueueTotal(String deptid) {
        String sql = "select ISNULL(SUM(ticketcount),0) ticketcount,ISNULL(SUM(servicecount),0) servicecount,ISNULL(SUM(cancelcount), 0) cancelcount," +
                "ISNULL(SUM(totalkeymy),0) totalkeymy,ISNULL(SUM(totalkeybmy),0) totalkeybmy," +
                "convert(varchar,CONVERT(decimal(18, 2),SUM(totalkeymy)*100.0/SUM(servicecount))) myl,FlOOR(SUM(totalmyd)*10.0/SUM(servicecount)) myd," +
                "dbo.FN_SecondToString(sum(totalpausetime)) pausetime," +
                "dbo.FN_SecondToString(sum(totalwaittime)/nullif(sum(servicecount),0)) as waittimeavg, " +
                "dbo.FN_SecondToString(sum(totalservicetime)/nullif(sum(servicecount),0)) as servicetimeavg " +
                "from rpt_deptdata where DateDiff(dd,servicedate,getdate())=0";
        if (StringUtils.isNotBlank(deptid)){
            sql += " and deptid in("+deptid+")";
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    /*
    当前人流量-当天业务总量，人流量（取号且未办结）
     */
    public List getQueueDeptAnysle(String deptid) {
        String sql = "select deptid,deptname,servicedate,SUM(totalservicetime)/NULLIF(SUM(servicecount), 0) totalservicetime," +
                "SUM(totalservicetime) totalservicetime2,SUM(totalwaittime)/nullif(SUM(servicecount),0) totalwaittime,SUM(totalwaittime) totalwaittime2," +
                "ISNULL(SUM(totalpausetime), 0) totalpausetime,sum(servicecount) servicecount,sum(ticketcount) ticketcount,SUM(totalkeybmy) totalkeybmy,SUM(totalwait) totalwait " +
                "from rpt_deptdata t1 where DateDiff(dd,servicedate,getdate())=0 ";
        if (StringUtils.isNotBlank(deptid)){
            sql += " and deptid in("+deptid+")";
        }
         sql += " group by deptid,deptname,servicedate";
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List getQueueEmployeeAnysle(String deptid) {
        String sql = "select eno,ename,deptname,servicedate,SUM(totalservicetime)/NULLIF(SUM(servicecount), 0) totalservicetime," +
                "dbo.FN_SecondToString(sum(totalservicetime)/nullif(sum(servicecount),0)) as totalservicetimename, " +
                "SUM(totalservicetime) totalservicetime2,SUM(totalwaittime)/nullif(SUM(servicecount),0) totalwaittime,SUM(totalwaittime) totalwaittime2," +
                "ISNULL(SUM(totalpausetime), 0) totalpausetime,sum(servicecount) servicecount,sum(ticketcount) ticketcount,SUM(totalkeybmy) totalkeybmy " +
                "from rpt_employeedata t1 where DateDiff(dd,servicedate,getdate())=0 ";
        if (StringUtils.isNotBlank(deptid)){
            sql += " and deptid in("+deptid+")";
        }
        sql += " group by eno,ename,deptname,servicedate";
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    /*
    差评预警 - 月
     */
    public List getEmployeeWarn(String deptid) {
        String sql = "select eno,ename, deptname,SUM(servicecount) servicecount," +
                "CONVERT(decimal(18, 2),SUM(servicecount)*1.0/COUNT(*)) servicecountavg, SUM(totalkeybmy) totalkeybmy, SUM(totalmyd) totalmyd" +
                ",t2.dictvalue avg " +
                "from rpt_employeedata,(select dictvalue from sys_dictinfo where dictkey='warn_keybmyvalue') t2 " +
                "where DATEDIFF(d,servicedate,GETDATE())<ISNULL((select dictvalue from sys_dictinfo where dictkey='warn_keybmytime'),30) " ;
        if (StringUtils.isNotBlank(deptid)){
            sql += " and deptid in("+deptid+")";
        }
        sql += "group by eno,ename,deptname,t2.dictvalue";
        try {
            logger.debug(sql);
            List list = this.getJdbcTemplate().queryForList(sql);
            sql = "select (select ISNULL(dictvalue, 30) from sys_dictinfo where dictkey='warn_keybmytime') as warn_keybmytime" +
                    ",(select dictvalue from sys_dictinfo where dictkey='warn_keybmyvalue') warn_keybmyvalue";
            List list2 = this.getJdbcTemplate().queryForList(sql);
            for (Object o : list) {
                Map m = (Map)o;Map m2 = (Map)list2.get(0);
                m.put("warn_keybmytime", m2.get("warn_keybmytime"));
                m.put("warn_keybmyvalue", m2.get("warn_keybmyvalue"));
            }
            return list;
        }
        catch (Exception e) {
            return null;
        }
    }
    /*
    查询超时记录
     */
    public List getSerialTimeout() {
        String sql = "select top 5 t1.*,t2.name countername, dbo.FN_SecondToString(t1.servicetime) servicetimename " +
                "from queue_detail t1 left join appries_dept t2 on t1.hjcounterdeptid=t2.id where serviceout=1 order by tickettime desc";
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    /*
       查询差评预警
        */
    public List getHallEval6Warn() {
        String sql = "select top 10 * from warn_ontimedetail t1 join queue_detail t2 on t1.ywlsh=t2.ywlsh where t1.type=3 order by t1.ctime desc";
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
}
