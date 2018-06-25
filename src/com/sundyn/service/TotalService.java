package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import org.jfree.util.Log;

import java.util.List;
import java.util.Map;

public class TotalService extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public Map totalDept(final String ids, final String startDate, final String endDate) {
        String sql_mysql = "select id,serviceDate,DeptId,employeeId";
        String sql ="select sum(key0) as key0";
        sql = String.valueOf(sql) + ", sum(key1) as key1";
        sql = String.valueOf(sql) + ", sum(key2) as key2";
        sql = String.valueOf(sql) + ", sum(key3) as key3";
        sql = String.valueOf(sql) + ", sum(key4) as key4 ";
        sql = String.valueOf(sql) + ", sum(key5) as key5";
        sql = String.valueOf(sql) + ", sum(key6) as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select id,serviceDate,DeptId,employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1 THEN 1 else 0 end ) as key1 ,";
        sql = String.valueOf(sql) + " (case keyno WHEN 2 THEN 1 else 0 end ) as key2  ,";
        sql = String.valueOf(sql) + " (case keyno WHEN 3 THEN 1 else 0 end ) as key3  ,";
        sql = String.valueOf(sql) + " (case keyno WHEN 4 THEN 1 else 0 end ) as key4 ,";
        sql = String.valueOf(sql) + " (case keyno WHEN 5 THEN 1 else 0 end ) as key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6 THEN 1 else 0 end ) as key6,";
        sql = String.valueOf(sql) + " 1 as g";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where 1=1 ";
        if(ids !=null && ids.length()>0)
            sql += "and DeptId in (" + ids + ")";
        if(null!=startDate && !"".equals(startDate)) {
        	sql = String.valueOf(sql) + "  and serviceDate>='" + startDate + "'";
        }
        if(null!=endDate && !"".equals(endDate))
        	sql = String.valueOf(sql) + " and serviceDate <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by g";

        System.out.println("totalDept-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Map totalDept(final String ids, final String employeeid, final String startDate, final String endDate) {
        String sql_mysql = "select id,serviceDate,DeptId,employeeId";
        String sql ="select sum(key0) as key0";
        sql = String.valueOf(sql) + ", sum(key1) as key1";
        sql = String.valueOf(sql) + ", sum(key2) as key2";
        sql = String.valueOf(sql) + ", sum(key3) as key3";
        sql = String.valueOf(sql) + ", sum(key4) as key4 ";
        sql = String.valueOf(sql) + ", sum(key5) as key5";
        sql = String.valueOf(sql) + ", sum(key6) as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select id,serviceDate,DeptId,employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1 THEN 1 else 0 end ) as key1 ,";
        sql = String.valueOf(sql) + " (case keyno WHEN 2 THEN 1 else 0 end ) as key2  ,";
        sql = String.valueOf(sql) + " (case keyno WHEN 3 THEN 1 else 0 end ) as key3  ,";
        sql = String.valueOf(sql) + " (case keyno WHEN 4 THEN 1 else 0 end ) as key4 ,";
        sql = String.valueOf(sql) + " (case keyno WHEN 5 THEN 1 else 0 end ) as key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6 THEN 1 else 0 end ) as key6,";
        sql = String.valueOf(sql) + " 1 as g";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where 1=1 ";
        if(ids !=null && ids.length()>0)
            sql += "and DeptId in (" + ids + ")";
        if(employeeid !=null && employeeid.length()>0)
            sql += "and employeeid in (" + employeeid + ")";
        if(null!=startDate && !"".equals(startDate)) {
            sql = String.valueOf(sql) + "  and serviceDate>='" + startDate + "'";
        }
        if(null!=endDate && !"".equals(endDate))
            sql = String.valueOf(sql) + " and serviceDate <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by g";

        System.out.println("totalDept-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List totalDating(final String ids, final String dating, final String startDate, final String endDate, final Integer start, final Integer num, Integer[] total) {
        String sql = "select row_number() over(order by datingid) as rows,datingname, sum(key0)   as key0 , sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as key3, sum(key4)  as key4, sum(key5)   as key5, sum(key6)   as key6 from ( select   temp. id,serviceDate,DeptId,employeeId,   key0,key1 ,key2  ,key3  ,key4 ,key5,key6 ,t1.id as windowid ,t1.name as windowname,t2.id as datingid ,t2.name as datingname from ( select id,serviceDate,DeptId,employeeId, (case keyno WHEN 0  THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 , (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  , (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  , (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 , (case keyno WHEN 5  THEN   1 else 0    end ) as  key5, (case keyno WHEN 6  THEN   1 else 0    end ) as  key6 from appries_appries where 1=1 ";
        if(null!=startDate)
            sql += " and serviceDate>='" + startDate + "'  ";
        if(null!=endDate)
            sql += " and serviceDate<='" + endDate + "'  " ;
        if(null!=dating)
            sql += " and serviceDate<='" + endDate + "'  " ;
        sql += " ) as temp,appries_dept t1,appries_dept t2" + " where temp.DeptId=t1.id and t1.fatherId=t2.id ";
        if(null!=ids && !"".equals(ids))
            sql += "and t2.fatherId in( " + ids + ")";
        if(null!=dating && !"".equals(dating))
            sql += "and t2.id in( " + dating + ")";
        sql += ") as A group by datingid,datingname ";

        if(total != null){
            String totalsql = "select max(rows) from ("+sql+") t";
            total[0] = this.getJdbcTemplate().queryForInt(totalsql);
        }

        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        try {
            logger.debug("totalDating:"+sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int counttotalDating(final String ids, final String startDate, final String endDate) {
        String sql = "select   sum(key0)   as key0 , sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as key3, sum(key4)  as key4, sum(key5)   as key5, sum(key6)   as key6 ,datingid, datingname " +
                "from(" +
                "    select temp.id,serviceDate,DeptId,employeeId,key0,key1 ,key2  ,key3  ,key4 ,key5,key6 ,t1.id as windowid ,t1.name as windowname,t2.id as datingid ,t2.name as datingname        " +
                "    from(select id,serviceDate,DeptId,employeeId, (case keyno WHEN 0  THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 , (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  , (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  , (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 , (case keyno WHEN 5  THEN   1 else 0    end ) as  key5, (case keyno WHEN 6  THEN   1 else 0    end ) as  key6 " +
                "        from appries_appries  where 1=1 ";
        if(startDate!=null)
            sql += "and serviceDate>='" + startDate + "' ";
        if(endDate!=null)
            sql += "  and serviceDate<='" + endDate + "' ";
        sql += ")as temp,appries_dept t1,appries_dept t2" + " where   temp.DeptId=t1.id and  t1.fatherId=t2.id and t2.fatherId in( " + ids + ")" + " ) " + " " +
                "as A group by datingid, datingname  ";
        sql = "select count(*) from (" + sql + " ) as tempcount";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List totalWindow(final String datingId, final String startDate, final String endDate, final Integer start, final Integer num) {
        /*
        // mysql--
        String sql = "select id,serviceDate,DeptId,employeeId, sum(key0)   as key0 , sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as key3, sum(key4)  as key4, sum(key5)   as key5, sum(key6)   as key6 ,windowid,windowname from ( select   temp. id,serviceDate,DeptId,employeeId,   key0,key1 ,key2  ,key3  ,key4 ,key5,key6 ,t1.id as windowid ,t1.name as windowname   from ( select id,serviceDate,DeptId,employeeId, (case keyno WHEN 0  THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 , (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  , (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  , (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 , (case keyno WHEN 5  THEN   1 else 0    end ) as  key5, (case keyno WHEN 6  THEN   1 else 0    end ) as  key6 from appries_appries  where serviceDate>='" + startDate + "'  and serviceDate<='" + endDate + "'  " + " ) as temp" + ",appries_dept t1  where temp.DeptId=t1.id  and  t1.fatherId in( " + datingId + ") " + " )as A group by windowname";
        if (start != null && num != null) {
            sql = String.valueOf(sql) + "  limit " + start + "," + num + " ";
        }*/
        String sql = "select windowname,deptid,ROW_NUMBER() over(order by windowname) as rows,sum(key0)   as key0 , sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as key3, sum(key4)  as key4, sum(key5)   as key5, " +
                "sum(key6)   as key6 " +
                "from ( select   temp. id,serviceDate,DeptId,employeeId,   key0,key1 ,key2  ,key3  ,key4 ,key5,key6 ,t1.id as windowid ,t1.name as windowname   " +
                "from ( select id,serviceDate,DeptId,employeeId, (case keyno WHEN 0 THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 , " +
                "(case keyno WHEN 2 THEN 1 else 0 end ) as  key2  , (case keyno WHEN 3 THEN   1 else 0 end ) as  key3  , (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 , " +
                "(case keyno WHEN 5 THEN 1 else 0 end ) as  key5, (case keyno WHEN 6 THEN   1 else 0    end ) as  key6 from appries_appries  " +
                "where 1=1 and ";
        if(startDate!="")
            sql += "serviceDate>='" + startDate + "'  ";
        if(endDate!="")
            sql += "and serviceDate<='" + endDate + "'  " + " ";
        sql += ") as temp" + ",appries_dept t1 " + "where temp.DeptId=t1.id  ";
        if(datingId!=null && datingId!="" && datingId!="-1")
            sql += "and t1.id in(" + datingId + ") ";
        sql += " )as A group by windowname,deptid";
        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        try {
            logger.info("totalWindow: " + sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int counttotalWindow(final String datingId, final String startDate, final String endDate) {
        //mysql String sql = "select   sum(key0)   as key0 , sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as key3, sum(key4)  as key4, sum(key5)   as key5, sum(key6)   as key6 ,windowid from ( select   temp. id,serviceDate,DeptId,employeeId,   key0,key1 ,key2  ,key3  ,key4 ,key5,key6 ,t1.id as windowid ,t1.name as windowname   from ( select id,serviceDate,DeptId,employeeId, (case keyno WHEN 0  THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 , (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  , (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  , (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 , (case keyno WHEN 5  THEN   1 else 0    end ) as  key5, (case keyno WHEN 6  THEN   1 else 0    end ) as  key6 from appries_appries  where serviceDate>='" + startDate + "'  and serviceDate<='" + endDate + "'  " + " ) as temp" + ",appries_dept t1  where temp.DeptId=t1.id  ";
        String sql = "select   sum(key0)   as key0 , sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as key3, sum(key4)  as key4, sum(key5)   as key5, sum(key6)   as key6 ,windowid from ( select   temp. id,serviceDate,DeptId,employeeId,   key0,key1 ,key2  ,key3  ,key4 ,key5,key6 ,t1.id as windowid ,t1.name as windowname from ( select id,serviceDate,DeptId,employeeId, (case keyno WHEN 0  THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 , (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  , (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  , (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 , (case keyno WHEN 5  THEN   1 else 0    end ) as  key5, (case keyno WHEN 6  THEN   1 else 0    end ) as  key6 from appries_appries where 1=1 ";
        if(null!=startDate)
            sql += "and serviceDate>='" + startDate + "'";
        if(null!=endDate)
            sql += " and serviceDate<='" + endDate + "'  " ;
        sql += " ) as temp" + ",appries_dept t1  where temp.DeptId=t1.id  ";
        if(datingId!=null && !"".equals(datingId))
            sql += " and  t1.fatherId in( " + datingId + ") ";
        sql += " )as A group by windowname,windowid ";
        sql = "select count(*) from (" + sql + " ) as tempcount";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List totalPerson3(final String windowId, final String startDate, final String endDate, final Integer start, final Integer num) {
        String sql = "select t.id,t.serviceDate,t.DeptId, appries_employee.Id as employeeId,IFNULL(t.key0,0) as key0,IFNULL(t.key1,0) as key1,IFNULL(t.key2,0) as key2,IFNULL(t.key3,0) as key3,IFNULL(t.key4,0) as key4,IFNULL(t.key5,0) as key5,IFNULL(t.key6,0) as key6,appries_employee.Name as employeeName from (select id,serviceDate,DeptId,employeeId, sum(key0)   as key0, sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as  key3, sum(key4)  as  key4 , sum(key5)   as key5, sum(key6)   as key6 from ( select id,serviceDate,DeptId,employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  , (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,(case keyno WHEN 6  THEN   1 else 0    end ) as  key6  from appries_appries   where DeptId in (" + windowId + ") and serviceDate>='" + startDate + "' " + " and serviceDate <='" + endDate + "') as temp  group by employeeId )" + " as t right JOIN appries_employee ON t.employeeId=appries_employee.Id  ";
        if (start != null && num != null) {
            sql = String.valueOf(sql) + " limit  " + start + "," + num;
        }
        System.out.println("totalPerson3-sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List totalPerson(final String windowId, final String startDate, final String endDate, final Integer start, final Integer num) {
        String sql = "select employeeId";
        sql = String.valueOf(sql) + ", sum(key0)   as key0";
        sql = String.valueOf(sql) + ", sum(key1)   as key1";
        sql = String.valueOf(sql) + ", sum(key2)   as key2";
        sql = String.valueOf(sql) + ", sum(key3)  as  key3";
        sql = String.valueOf(sql) + ", sum(key4)  as  key4 ";
        sql = String.valueOf(sql) + ", sum(key5)   as key5";
        sql = String.valueOf(sql) + ", sum(key6)   as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6  THEN   1 else 0    end ) as  key6 ";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where 1=1 ";
        if(windowId!=null && windowId!="")
                sql += "and DeptId in (" + windowId + ") ";
        if(startDate!=null && startDate!="")
            sql += "and serviceDate>='" + startDate + "' ";
        if(endDate!=null && endDate!="")
            sql += "and serviceDate <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by employeeId ";
        sql = "select row_number() over(order by t.employeeId) as rows, t.employeeId,t.key0,t.key1,t.key2,t.key3,t.key4,t.key5,t.key6,appries_employee.Name as employeeName from (" + sql + ") as t,appries_employee where t.employeeId=appries_employee.Id  ";
        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        logger.debug("totalPerson-sql: " + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List totalPerson(final String windowId, final String employeeId, final String startDate, final String endDate, final Integer start,
                            final Integer num, Integer[] totalrows) {
        String sql = "select employeeId";
        sql = String.valueOf(sql) + ", sum(key0)   as key0";
        sql = String.valueOf(sql) + ", sum(key1)   as key1";
        sql = String.valueOf(sql) + ", sum(key2)   as key2";
        sql = String.valueOf(sql) + ", sum(key3)  as  key3";
        sql = String.valueOf(sql) + ", sum(key4)  as  key4 ";
        sql = String.valueOf(sql) + ", sum(key5)   as key5";
        sql = String.valueOf(sql) + ", sum(key6)   as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6  THEN   1 else 0    end ) as  key6 ";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where 1=1 ";
        if(windowId!=null && windowId!="")
            sql += "and DeptId in (" + windowId + ") ";
        if(employeeId!=null && employeeId!="")
            sql += "and employeeId in (" + employeeId + ") ";
        if(startDate!=null && startDate!="")
            sql += "and serviceDate>='" + startDate + "' ";
        if(endDate!=null && endDate!="")
            sql += "and serviceDate <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by employeeId ";
        sql = "select row_number() over(order by t.employeeId) as rows, t.employeeId,t.key0,t.key1,t.key2,t.key3,t.key4,t.key5,t.key6,appries_employee.Name as employeeName from (" + sql + ") as t,appries_employee where t.employeeId=appries_employee.Id  ";

        String totalsql = "select max(rows) from ("+sql+") t";
        totalrows[0] = this.getJdbcTemplate().queryForInt(totalsql);
        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        logger.debug("totalPerson-sql.totalrows: " + totalrows);
        logger.debug("totalPerson-sql: " + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List totalPerson(final String employeeId, final String startDate, final String endDate) {
        String sql = "select employeeId";
        sql = String.valueOf(sql) + ", sum(key0)   as key0";
        sql = String.valueOf(sql) + ", sum(key1)   as key1";
        sql = String.valueOf(sql) + ", sum(key2)   as key2";
        sql = String.valueOf(sql) + ", sum(key3)  as  key3";
        sql = String.valueOf(sql) + ", sum(key4)  as  key4 ";
        sql = String.valueOf(sql) + ", sum(key5)   as key5";
        sql = String.valueOf(sql) + ", sum(key6)   as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6  THEN   1 else 0    end ) as  key6 ";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where EmployeeId = " + employeeId + "  ";
        if (startDate != null) {
            sql = String.valueOf(sql) + "and serviceDate>='" + startDate + "'";
        }
        if (endDate != null) {
            sql = String.valueOf(sql) + "and serviceDate <='" + endDate + "'";
        }
        sql = String.valueOf(sql) + ") as temp  group by employeeId ";
        sql = "select t.employeeId,t.key0,t.key1,t.key2,t.key3,t.key4,t.key5,t.key6,appries_employee.Name as employeeName from (" + sql + ") as t,appries_employee where t.employeeId=appries_employee.Id  ";
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countTotalPerson(final String windowId, final String startDate, final String endDate) {
        String sql = "select   employeeId";
        sql = String.valueOf(sql) + ", sum(key0)   as key0";
        sql = String.valueOf(sql) + ", sum(key1)   as key1";
        sql = String.valueOf(sql) + ", sum(key2)   as key2";
        sql = String.valueOf(sql) + ", sum(key3)  as  key3";
        sql = String.valueOf(sql) + ", sum(key4)  as  key4 ";
        sql = String.valueOf(sql) + ", sum(key5)   as key5";
        sql = String.valueOf(sql) + ", sum(key6)   as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select id,serviceDate,DeptId,employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6  THEN   1 else 0    end ) as  key6 ";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where 1=1 ";
        if (windowId != null && windowId!="")
            sql += "and DeptId in (" + windowId + ") ";
        if (startDate != null)
            sql += "and serviceDate>='" + startDate + "' ";
        if (endDate != null)
            sql += "and serviceDate <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by employeeId ";
        sql = "select count(*) from (" + sql + " ) as tempcount";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int countTotalPerson(final String windowId, final String employeeId, final String startDate, final String endDate) {
        String sql = "select   employeeId";
        sql = String.valueOf(sql) + ", sum(key0)   as key0";
        sql = String.valueOf(sql) + ", sum(key1)   as key1";
        sql = String.valueOf(sql) + ", sum(key2)   as key2";
        sql = String.valueOf(sql) + ", sum(key3)  as  key3";
        sql = String.valueOf(sql) + ", sum(key4)  as  key4 ";
        sql = String.valueOf(sql) + ", sum(key5)   as key5";
        sql = String.valueOf(sql) + ", sum(key6)   as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select id,serviceDate,DeptId,employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6  THEN   1 else 0    end ) as  key6 ";
        sql = String.valueOf(sql) + " from appries_appries ";
        sql = String.valueOf(sql) + " where 1=1 ";
        if (windowId != null && windowId!="")
            sql += "and DeptId in (" + windowId + ") ";
        if (employeeId != null && employeeId!="")
            sql += "and employeeId in (" + employeeId + ") ";
        if (startDate != null)
            sql += "and serviceDate>='" + startDate + "' ";
        if (endDate != null)
            sql += "and serviceDate <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by employeeId ";
        sql = "select count(*) from (" + sql + " ) as tempcount";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int countTotalBusiness(final String deptIds, final String startDate, final String endDate) {
        String sql = "select   businessType";
        sql = String.valueOf(sql) + ", sum(key0)   as key0";
        sql = String.valueOf(sql) + ", sum(key1)   as key1";
        sql = String.valueOf(sql) + ", sum(key2)   as key2";
        sql = String.valueOf(sql) + ", sum(key3)  as  key3";
        sql = String.valueOf(sql) + ", sum(key4)  as  key4 ";
        sql = String.valueOf(sql) + ", sum(key5)   as key5";
        sql = String.valueOf(sql) + ", sum(key6)   as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select id,serviceDate,DeptId,employeeId, businessType,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6  THEN   1 else 0    end ) as  key6 ";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where DeptId in (" + deptIds + ") and serviceDate>='" + startDate + "' and serviceDate <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by businessType ";
        sql = "select count(*) from (" + sql + " ) as tempcount";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List totalBusiness(final String deptIds, final String startDate, final String endDate, final Integer start, final Integer num) {
        String sql = "select    id,serviceDate,DeptId,employeeId,businessType";
        sql = String.valueOf(sql) + ", sum(key0)   as key0";
        sql = String.valueOf(sql) + ", sum(key1)   as key1";
        sql = String.valueOf(sql) + ", sum(key2)   as key2";
        sql = String.valueOf(sql) + ", sum(key3)  as  key3";
        sql = String.valueOf(sql) + ", sum(key4)  as  key4 ";
        sql = String.valueOf(sql) + ", sum(key5)   as key5";
        sql = String.valueOf(sql) + ", sum(key6)   as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select id,serviceDate,DeptId,employeeId, businessType,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6  THEN   1 else 0    end ) as  key6 ";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where DeptId in (" + deptIds + ") and serviceDate>='" + startDate + "' and serviceDate <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by businessType ";
        sql = "select tempbusiness.* ,appries_business.businessName from (" + sql + ") as tempbusiness,appries_business where  tempbusiness.businessType = appries_business.businessId";
        if (start != null && num != null) {
            sql = String.valueOf(sql) + " limit  " + start + "," + num;
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List totalD(final String startDate, final String endDate, final String ids) {
        String sql = "select    id,serviceDate,DeptId,employeeId,businessType";
        sql = String.valueOf(sql) + ", sum(key0)   as key0";
        sql = String.valueOf(sql) + ", sum(key1)   as key1";
        sql = String.valueOf(sql) + ", sum(key2)   as key2";
        sql = String.valueOf(sql) + ", sum(key3)  as  key3";
        sql = String.valueOf(sql) + ", sum(key4)  as  key4 ";
        sql = String.valueOf(sql) + ", sum(key5)   as key5";
        sql = String.valueOf(sql) + ", sum(key6)   as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select id,serviceDate,DeptId,employeeId, businessType,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6  THEN   1 else 0    end ) as  key6 ";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where   serviceDate>='" + startDate + "' and serviceDate <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by businessType ";
        final String sql2 = "select a.*, b.businessId as  three,b.businessName as threeName , c.businessId as two ,  c.businessName as twoName ,  d.businessId as one    , d.businessName as oneName   from appries_business a    inner join appries_business b ON  a.businessFatherId =b.businessId    inner join appries_business c ON  b.businessFatherId =c.businessId      inner join appries_business d ON  c.businessFatherId =d.businessId    where d.businessId   in(" + ids + ")";
        final String sql3 = "select ((t1.key0*5)+(t1.key1)*4+(t1.key2)*3 )/(t1.key0+t1.key1+t1.key2+t1.key3+t1.key4+t1.key5)*20 as d ,t2.businessName,t2.threeName,t2.twoName,t2.oneName from((" + sql + " )  as t1 ,  (" + sql2 + " )  as  t2 ) where t1.businessType=t2.businessId order by oneName, twoName,threeName,businessName";
        try {
            return this.getJdbcTemplate().queryForList(sql3);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List totalSection(final String deptIds, final String startDate, final String endDate, final Integer start, final Integer num) {
        String sql = "select id,serviceDate,DeptId,employeeId, sum(key0)   as key0 , sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as key3, sum(key4)  as key4, sum(key5)   as key5, sum(key6)   as key6  , windowid, windowname from ( select   temp. id,serviceDate,DeptId,employeeId,   key0,key1 ,key2  ,key3  ,key4 ,key5,key6 ,t1.id as windowid ,t1.name as windowname       from ( select id,serviceDate,DeptId,employeeId, (case keyno WHEN 0  THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 , (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  , (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  , (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 , (case keyno WHEN 5  THEN   1 else 0    end ) as  key5, (case keyno WHEN 6  THEN   1 else 0    end ) as  key6 from appries_appries  where serviceDate>='" + startDate + "'  and serviceDate<='" + endDate + "'   ) as temp,appries_dept t1 where   temp.DeptId=t1.id   and t1.id in( " + deptIds + ") )  as A group by trim( windowname   )";
        if (start != null && num != null) {
            sql = String.valueOf(sql) + " limit  " + start + "," + num;
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countTotalSection(final String deptIds, final String startDate, final String endDate) {
        String sql = "select id,serviceDate,DeptId,employeeId, sum(key0)   as key0 , sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as key3, sum(key4)  as key4, sum(key5)   as key5, sum(key6)   as key6  , windowid, windowname from ( select   temp. id,serviceDate,DeptId,employeeId,   key0,key1 ,key2  ,key3  ,key4 ,key5,key6 ,t1.id as windowid ,t1.name as windowname       from ( select id,serviceDate,DeptId,employeeId, (case keyno WHEN 0  THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 , (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  , (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  , (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 , (case keyno WHEN 5  THEN   1 else 0    end ) as  key5, (case keyno WHEN 6  THEN   1 else 0    end ) as  key6 from appries_appries  where serviceDate>='" + startDate + "'  and serviceDate<='" + endDate + "'   ) as temp,appries_dept t1 where   temp.DeptId=t1.id   and t1.id in( " + deptIds + ") )  as A group by trim( windowname   )";
        sql = "select count(*) from (" + sql + " ) as tempcount";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Map totalPersonM7(final String cardNum, final String startDate, final String endDate) {
        final String sql = "select t.key0,t.key1,t.key2,t.key3,t.key4,t.key5,t.key6 from (select  employeeId, sum(key0)   as key0, sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as  key3, sum(key4)  as  key4 , sum(key5)   as key5, sum(key6)   as key6 from ( select employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0,  (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,(case keyno WHEN 6  THEN   1 else 0    end ) as  key6  from appries_appries   where serviceDate>='" + startDate + "' and serviceDate <='" + endDate + "') as temp  group by employeeId ) as t,appries_employee where t.employeeId=appries_employee.Id  and appries_employee.cardNum='" + cardNum + "'";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List totalWindowVideo(final String datingId, final String startDate, final String endDate, final Integer start, final Integer num) {
        final String sql = "select id,serviceDate,DeptId,employeeId,videofile,windowid,windowname from ( select   temp. id,serviceDate,DeptId,employeeId,videofile, t1.id as windowid ,t1.name as windowname   from ( select id,serviceDate,DeptId,employeeId,videofile from appries_appries  where serviceDate>='" + startDate + "'  and serviceDate<='" + endDate + "'  " + " ) as temp" + ",appries_dept t1  where temp.DeptId=t1.id  and  t1.fatherId in( " + datingId + ") " + " )as A";
        System.out.println("videosql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            System.out.println("\u4f20\u5165\u7684\u90e8\u95e8id" + datingId);
            e.printStackTrace();
            return null;
        }
    }

    public int countEmployeeAll() {
        final String sql = "select count(*) from appries_employee";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
