package com.sundyn.service;

import com.sundyn.dao.*;
import com.xuan.xutils.utils.StringUtils;
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
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List AnalyseTotalEx(final String startDate, final String endDate, final String allId, final String analyseType, final String ids) {
        String sql = "select deptid seriesId, deptname seriesName,count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),tickettime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),4) as serviceDate ";
        }
        sql += ",100.0*sum(t2.ext1)/count(*)/10.0 d ";
        sql +=  "from v_queuedetail t1 left join appries_keytype t2 on t1.appriseresult=t2.keyno " +
                "where tickettime>='" + startDate + "' and tickettime<='" + endDate + "' ";
        /*if(StringUtils.isNotBlank(allId))
            sql +=  "and appriseresult in (" + allId + ") ";*/
        if(StringUtils.isNotBlank(ids))
            sql += "and deptId in(" + ids + ") ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by deptid,deptname,CONVERT(varchar(30),tickettime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by deptid,deptname,left(CONVERT(varchar(30),tickettime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by deptid,deptname,left(CONVERT(varchar(30),tickettime,23),4)";
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List AnalyseTotalExRpt(final String startDate, final String endDate, final String allId, final String analyseType, final String ids, final String numtype) {
        String sql = "select deptid seriesId, deptname seriesName, ";
        if (StringUtils.isNotBlank(numtype)){
            if (numtype.equals("totalwaittime") || numtype.equals("totalservicetime"))
                sql += "FLOOR(sum("+numtype+")/nullif(sum(servicecount), 0)) as num, ";
            else if (numtype.equals("totalmyd"))
                sql += "FLOOR(sum("+numtype+"*10.0)/nullif(sum(servicecount), 0)) as num, ";
            else
                sql += "sum("+numtype+") as num, ";
        }
        else
            sql += "sum(servicecount) as num, ";

        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),servicedate,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),servicedate,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),servicedate,23),4) as serviceDate ";
        }
        sql += ",ISNULL(sum(totalmyd)/sum(servicecount), 0) d ";
        sql +=  "from rpt_deptdata where servicedate>='" + startDate + "' and servicedate<='" + endDate + "' ";
        /*if(StringUtils.isNotBlank(allId))
            sql +=  "and appriseresult in (" + allId + ") ";*/
        if(StringUtils.isNotBlank(ids))
            sql += "and deptId in(" + ids + ") ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by deptid,deptname,CONVERT(varchar(30),servicedate,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by deptid,deptname,left(CONVERT(varchar(30),servicedate,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by deptid,deptname,left(CONVERT(varchar(30),servicedate,23),4)";
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List AnalyseTotalEx2(final String startDate, final String endDate, final String allId, final String analyseType, final String ids) {
        String sql = "select deptid seriesId, deptname seriesName,count(*) servercount,sum(case when t1.status=-1 then 1 else 0 end) unkey " +
                ",sum(waittime)/count(*) as waittimeavg, sum(servicetime)/count(*) as servicetimeavg ";
        sql +=  "from v_queuedetail t1 left join appries_keytype t2 on t1.appriseresult=t2.keyno " +
                "where tickettime>='" + startDate + "' and tickettime<='" + endDate + "' ";
        if(StringUtils.isNotBlank(ids))
            sql += "and deptId in(" + ids + ") ";
        sql += " group by deptid,deptname";
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
        /*String sql = "select t2.id seriesId,t2.name seriesName,convert(nvarchar(50),count(*)) num,'业务总量' cateName " +
                "from queue_detail t1 join appries_dept t2 on t1.deptid=t2.id " +
                "where tickettime>='" + startDate + "' and tickettime<='" + endDate + "' ";
        if(StringUtils.isNotBlank(ids))
            sql += "and deptId in(" + ids + ") ";
        sql += "group by t2.id,t2.name " +
                "union " +
                "select t2.id seriesId,t2.name seriesName,convert(nvarchar(50),sum(case when t1.status=-1 then 1 else 0 end)) num,'弃号量' cateName " +
                "from queue_detail t1 join appries_dept t2 on t1.deptid=t2.id " +
                "where tickettime>='" + startDate + "' and tickettime<='" + endDate + "' ";
        if(StringUtils.isNotBlank(ids))
            sql += "and deptId in(" + ids + ") ";
        sql += "group by t2.id,t2.name " +
                "union " +
                "select t2.id seriesId,t2.name seriesName,dbo.FN_SecondToString(sum(waittime)/count(*)) num,'平均等候时长' cateName " +
                "from queue_detail t1 join appries_dept t2 on t1.deptid=t2.id " +
                "where tickettime>='" + startDate + "' and tickettime<='" + endDate + "' ";
        if(StringUtils.isNotBlank(ids))
            sql += "and deptId in(" + ids + ") ";
        sql += "group by t2.id,t2.name " +
                "union " +
                "select t2.id seriesId,t2.name seriesName,dbo.FN_SecondToString(sum(servicetime)/count(*)) num,'平均办理时长' cateName " +
                "from queue_detail t1 join appries_dept t2 on t1.deptid=t2.id " +
                "where tickettime>='" + startDate + "' and tickettime<='" + endDate + "' ";
        if(StringUtils.isNotBlank(ids))
            sql += "and deptId in(" + ids + ") ";
        sql += "group by t2.id,t2.name ";
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }*/
    }

    public List AnalyseContent(final String startDate, final String endDate, final String contentId, final String analyseType, final String deptIds) {
        String sql = "select deptid seriesId, deptname seriesName,count(*) as num, ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + "CONVERT(varchar(30),tickettime,23)";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + "left(CONVERT(varchar(30),tickettime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + "left(CONVERT(varchar(30),tickettime,23),4)";
        }
        sql +=" as serviceDate from v_queuedetail where 1=1 ";
        if(StringUtils.isNotBlank(contentId))
            sql += "and appriseresult in (" + contentId + ") ";
        sql += "and tickettime>='" + startDate + "' and tickettime<='" + endDate + "' " ;
        if(StringUtils.isNotBlank(deptIds))
            sql += "and deptId in(" + deptIds + ") ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by deptid, deptname, CONVERT(varchar(30),tickettime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by deptid, deptname, left(CONVERT(varchar(30),tickettime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by deptid, deptname, left(CONVERT(varchar(30),tickettime,23),4)";
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
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

    public List AnalyseDeptTotal(final String deptId, final String deptId2, final String startDate, final String endDate, final String allId, final String analyseType) {
        String sql = "select hjcounter seriesId, count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),tickettime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),4) as serviceDate ";
        }
        sql += ",100.0*sum(t2.ext1)/count(*)/10.0 d ";
       /* sql += "from appries_appries t1 left join appries_dept t2 on t1.deptid=t2.id " +
                "left join appries_keytype t3 on t3.keyno=t1.keyno " +
                "where DeptId in (" + deptId + ") and JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "' and t1.keyno in (" + allId + ")  ";*/
        sql +="from v_queuedetail t1 left join appries_keytype t2 on t1.appriseresult=t2.keyno where hjcounter is not null " +
                "and hjcounterdeptid in (" + deptId + ") " +
                "and tickettime>='" + startDate + "' " +
                "and tickettime<='" + endDate + "' ";
                //"and t1.appriseresult in (" + allId + ") ";

        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by t1.hjcounter, CONVERT(varchar(30),tickettime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by t1.hjcounter, left(CONVERT(varchar(30),tickettime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by t1.hjcounter, left(CONVERT(varchar(30),tickettime,23),4)";
        }
        sql = "select t.*,t2.countername seriesName from ("+sql+") t left join sys_queuecounter t2 on t.seriesId=t2.counterno where 1=1 ";
        sql += "order by seriesId";
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List AnalyseDeptTotalRpt(final String deptId, final String deptId2, final String startDate, final String endDate, final String allId, final String analyseType, final String numtype) {
        String sql = "select t1.counterno seriesId, t1.countername seriesName, ";
        if (StringUtils.isNotBlank(numtype)){
            if (numtype.equals("totalwaittime") || numtype.equals("totalservicetime"))
                sql += "FLOOR(sum("+numtype+")/nullif(sum(servicecount), 0)) as num, ";
            if (numtype.equals("totalmyd"))
                sql += "FLOOR(sum("+numtype+"*10.0)/nullif(sum(servicecount), 0)) as num, ";
            else
                sql += "sum("+numtype+") as num, ";
        }
        else
            sql += "sum(servicecount) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),servicedate,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),servicedate,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),servicedate,23),4) as serviceDate ";
        }
        sql +="from rpt_counterdata t1 where servicedate>='" + startDate + "' " +
                "and servicedate<='" + endDate + "' ";
        if(StringUtils.isNotBlank(deptId))
            sql += "and deptId in(" + deptId + ") ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by t1.counterno,t1.countername, CONVERT(varchar(30),servicedate,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by t1.counterno,t1.countername, left(CONVERT(varchar(30),servicedate,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by t1.counterno,t1.countername, left(CONVERT(varchar(30),servicedate,23),4)";
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List AnalyseDeptContent(final String deptId, final String startDate, final String endDate, final String contentId, final String analyseType) {
        String sql = "select hjcounter seriesId, count(*) as num,";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),tickettime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),4) as serviceDate ";
        }
        sql +="from v_queuedetail t1 where t1.appriseresult in (select keyno from appries_keytype where isjoy='on') " +
                "and hjcounterdeptid in (" + deptId + ") " +
                "and tickettime>='" + startDate + "' " +
                "and tickettime<='" + endDate + "' ";
        /*sql += "from appries_appries t1 left join appries_dept t2 on t1.deptid=t2.id left join appries_keytype t3 on t3.keyno=t1.keyno " +
                "where DeptId in (" + deptId + ") and t1.keyno in (select keyno from appries_keytype where isjoy='on') and JieshouTime>='" + startDate + "' and JieshouTime<='" + endDate +
                "' and t1.keyno in (" + contentId + ")  ";*/
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by t1.hjcounter,CONVERT(varchar(30),tickettime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by t1.hjcounter,left(CONVERT(varchar(30),tickettime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by t1.hjcounter,left(CONVERT(varchar(30),tickettime,23),4)";
        }
        sql = "select t.*,t2.countername seriesName from ("+sql+") t left join sys_queuecounter t2 on t.seriesId=t2.counterno where 1=1 ";
        sql += "order by seriesId";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
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

    public List AnalyseEmployeeTotalRpt(final String deptIds, final String employeeId, final String startDate, final String endDate, final String allId, final String analyseType, final String numtype) {
        String sql = "select eno seriesId,ename seriesName, ";
        if (StringUtils.isNotBlank(numtype)){
            if (numtype.equals("totalmyd") || numtype.equals("totalwaittime") || numtype.equals("totalservicetime"))
                sql += "FLOOR(sum("+numtype+")/nullif(sum(servicecount), 0)) as num, ";
            else
                sql += "sum("+numtype+") as num, ";
        }
        else
            sql += "sum(servicecount) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),servicedate,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),servicedate,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),servicedate,23),4) as serviceDate ";
        }
        sql += "from rpt_employeedata t1 where 1=1 ";
        if(employeeId!=null && !"".equals(employeeId))
            sql += "and t1.eno in (select cardnum from appries_employee where id in(" + employeeId + ")) ";
        if(deptIds!=null && !"".equals(deptIds))
            sql += "and t1.deptid in (" + deptIds + ") ";
        sql += "and servicedate >='" + startDate + "' and servicedate<='" + endDate + "' ";
        //if(StringUtils.isNotBlank(allId))
        //    sql += "and t1.appriseresult in (" + allId + ") ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by t1.eno,t1.ename, CONVERT(varchar(30),servicedate,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by t1.eno,t1.ename, left(CONVERT(varchar(30),servicedate,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by t1.eno,t1.ename, left(CONVERT(varchar(30),servicedate,23),4)";
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List AnalyseEmployeeTotal(final String deptIds, final String employeeId, final String startDate, final String endDate, final String allId, final String analyseType) {
        String sql = "select staffno,count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),tickettime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),4) as serviceDate ";
        }
        sql += ",100.0*sum(t3.ext1)/count(*)/10.0 d ";
        sql += "from v_queuedetail t1 left join appries_keytype t3 on t3.keyno=t1.appriseresult where 1=1 ";
        if(employeeId!=null && !"".equals(employeeId))
            sql += "and t1.staffno in (select cardnum from appries_employee where id in(" + employeeId + ")) ";
        if(deptIds!=null && !"".equals(deptIds))
            sql += "and t1.hjcounterdeptid in (" + deptIds + ") ";
        sql += "and tickettime >='" + startDate + "' and tickettime<='" + endDate + "' ";
        //if(StringUtils.isNotBlank(allId))
        //    sql += "and t1.appriseresult in (" + allId + ") ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by t1.staffno, CONVERT(varchar(30),tickettime,23) ";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by t1.staffno, left(CONVERT(varchar(30),tickettime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by t1.staffno, left(CONVERT(varchar(30),tickettime,23),4)";
        }
        try {
            sql = "select t1.id seriesId, t1.name seriesName, t2.num,t2.serviceDate, ISNULL(t2.d,0) as d from appries_employee t1 left join ("
            + sql + ") t2 on t1.cardnum=t2.staffno where 1=1";
            if(employeeId!=null && !"".equals(employeeId))
                sql += "and t1.id in (" + employeeId + ") ";
            sql += " order by t1.id";
            logger.debug(sql);
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
        sql += "from appries_appries where 1=1 ";
        if (null!=employeeId && !employeeId.equals(""))
            sql += "and EmployeeId in (" + employeeId + ") ";
        sql += "and keyno in (0,1) and JieshouTime >='" + startDate + "' and JieshouTime<='" + endDate + "'  and  keyno in (" + contentId + ")  ";
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

    public List AnalyseEmployeeContent2(final String deptIds, final String employeeId, final String startDate, final String endDate, final String contentId, final String analyseType) {
        String sql = "select staffno, count(*) as num, ";
        if (analyseType.equals("day")) {
            sql += "CONVERT(varchar(30),tickettime,23) as serviceDate ";
        }
        else if (analyseType.equals("month")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),7) as serviceDate ";
        }
        else if (analyseType.equals("year")) {
            sql += "left(CONVERT(varchar(30),tickettime,23),4) as serviceDate ";
        }
        sql += "from v_queuedetail where 1=1 ";
        if (null!=employeeId && !employeeId.equals(""))
            sql += "and staffno in (select cardnum from appries_employee where id in (" + employeeId + ")) ";
        if (null!=deptIds && !deptIds.equals(""))
            sql += "and hjcounterdeptid in (" + deptIds + ") ";
        sql += "and tickettime>='" + startDate + "' and tickettime<='" + endDate + "' ";
        sql += "and appriseresult in (" + contentId + ") ";
        if (analyseType.equals("day")) {
            sql = String.valueOf(sql) + " group by staffno, CONVERT(varchar(30),tickettime,23)";
        }
        else if (analyseType.equals("month")) {
            sql = String.valueOf(sql) + " group by staffno, left(CONVERT(varchar(30),tickettime,23),7)";
        }
        else if (analyseType.equals("year")) {
            sql = String.valueOf(sql) + " group by staffno, left(CONVERT(varchar(30),tickettime,23),4)";
        }
        try {
            sql = "select t1.id seriesId, t1.name seriesName, ISNULL(t2.num,0) num,t2.serviceDate from appries_employee t1 left join ("+
                    sql + ") t2 on t1.cardnum=t2.staffno where 1=1 ";
            if(employeeId!=null && !"".equals(employeeId))
                sql += "and t1.id in (" + employeeId + ") ";
            sql += " order by t1.id";
            logger.debug(sql);
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
