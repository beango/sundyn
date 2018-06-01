package com.sundyn.service;

import com.sundyn.dao.*;
import org.apache.log4j.Logger;

import java.util.*;

public class AnalyseService extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public List AnalyseTotal(final String startDate, final String endDate, final String allId, final String analyseType, final String ids) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),JieshouTime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),4) as serviceDate ";
        }
        sql +=  "from appries_appries where JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "'  and  keyno in (" + allId + ")  and deptId in(" + ids + ") ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            logger.debug("满意率柱状图AnalyseTotal-SQL:" + sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseContent(final String startDate, final String endDate, final String contentId, final String analyseType, final String deptIds) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + "CONVERT(varchar(30),JieshouTime,23)";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + "left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + "left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        sql +=" as serviceDate from appries_appries where keyno in (" + contentId + ") and JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' and deptId in(" + deptIds + ") ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            logger.debug("满意率柱状图AnalyseContent-SQL:" + sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseNotContent(final String startDate, final String endDate, final String analyseType) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " CONVERT(varchar(30),JieshouTime,23)";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        sql +=" as serviceDate from appries_appries where keyno in (2,3,4,5,6) JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23)";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseDeptTotal(final String deptId, final String startDate, final String endDate, final String allId, final String analyseType) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),JieshouTime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),4) as serviceDate ";
        }
        sql += "from appries_appries where DeptId in (" + deptId + ") and JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' and keyno in (" + allId + ")  ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            logger.debug("AnalyseDeptTotal.sql="+sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List AnalyseDeptContent(final String deptId, final String startDate, final String endDate, final String contentId, final String analyseType) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),JieshouTime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),4) as serviceDate ";
        }
        sql += "from appries_appries where DeptId in (" + deptId + ") and keyno in (0,1) and JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' and  keyno in (" + contentId + ")  ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseDeptNotContent(final String deptId, final String startDate, final String endDate, final String analyseType) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        sql +=" as serviceDate from appries_appries where DeptId in (" + deptId + ") and keyno in (2,3,4,5,6) JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseSectionTotal(final String sectionName, final String startDate, final String endDate, final String allId, final String analyseType) {
        String sql = "select count(*) as num, left(JieshouTime,10) as serviceDate from appries_appries where deptId in(select id from appries_dept where name ='" + sectionName + "') and JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' and keyno in (" + allId + ")  ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseSectionContent(final String sectionName, final String startDate, final String endDate, final String contentId, final String analyseType) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),JieshouTime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),4) as serviceDate ";
        }
        sql += "from appries_appries where deptId in(select id from appries_dept where name ='" + sectionName + "')  and keyno in (0,1) and JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' and  keyno in (" + contentId + ")  ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            logger.debug("AnalyseSectionContent.sql = "+sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List AnalyseSectionNotContent(final String sectionName, final String startDate, final String endDate, final String analyseType) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        sql += " as serviceDate from appries_appries where deptId in(select id from appries_dept where name ='" + sectionName + "')  and keyno in (2,3,4,5,6) JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseEmployeeTotal(final String employeeId, final String startDate, final String endDate, final String allId, final String analyseType) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),JieshouTime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),4) as serviceDate ";
        }
        sql += "from appries_appries where 1=1 ";
        if(employeeId!=null && !"".equals(employeeId))
            sql += "and EmployeeId in (" + employeeId + ") ";
        sql += "and JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' and  keyno in (" + allId + ")   ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            logger.debug("AnalyseEmployeeTotal.sql = " + sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List AnalyseEmployeeContent(final String employeeId, final String startDate, final String endDate, final String contentId, final String analyseType) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),JieshouTime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),JieshouTime,23),4) as serviceDate ";
        }
        sql += "from appries_appries where EmployeeId in (" + employeeId + ") and keyno in (0,1) and JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "'  and  keyno in (" + contentId + ")  ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23)";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseEmployeeNotContent(final String employeeId, final String startDate, final String endDate, final String analyseType) {
        String sql = "select count(*) as num, ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        sql += " as serviceDate from appries_appries where EmployeeId in (" + employeeId + ") and keyno in (2,3,4,5,6) JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by CONVERT(varchar(30),JieshouTime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(CONVERT(varchar(30),JieshouTime,23),4)";
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseContentD(final String startDate, final String endDate, final String allId, final String analyseType, final String ids) {
        String sql = "select    serviceDate ";
        sql = String.valueOf(sql) + ", sum(key0)   as key0";
        sql = String.valueOf(sql) + ", sum(key1)   as key1";
        sql = String.valueOf(sql) + ", sum(key2)   as key2";
        sql = String.valueOf(sql) + ", sum(key3)  as  key3";
        sql = String.valueOf(sql) + ", sum(key4)  as  key4 ";
        sql = String.valueOf(sql) + ", sum(key5)   as key5";
        sql = String.valueOf(sql) + ", sum(key6)   as key6";
        sql = String.valueOf(sql) + " from (";
        sql = String.valueOf(sql) + " select id,left(JieshouTime,10) as serviceDate,DeptId,employeeId, businessType,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, ";
        sql = String.valueOf(sql) + " (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,";
        sql = String.valueOf(sql) + "  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,";
        sql = String.valueOf(sql) + "(case keyno WHEN 6  THEN   1 else 0    end ) as  key6 ";
        sql = String.valueOf(sql) + " from appries_appries  ";
        sql = String.valueOf(sql) + " where   JieshouTime>='" + startDate + "' and JieshouTime <='" + endDate + "' and keyno in(" + allId + ")  and DeptId in (" + ids + ")      ";
        sql = String.valueOf(sql) + ") as temp  ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by left(serviceDate,10) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by left(serviceDate,7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by left(serviceDate,4)";
        }
        final String sql2 = "select ((t1.key0*5)+(t1.key1)*4+(t1.key2)*3 +(t1.key3)*2 +(t1.key4))/(t1.key0+t1.key1+t1.key2+t1.key3+t1.key4) as num  ,serviceDate  from((" + sql + " )  as t1   ) ";
        try {
            return this.getJdbcTemplate().queryForList(sql2);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
