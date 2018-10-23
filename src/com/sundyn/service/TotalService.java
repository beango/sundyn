package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.xuan.xutils.utils.StringUtils;
import org.jfree.util.Log;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TotalService extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public Map totalDept(final String ids, final String startDate, final String endDate){
        return totalDept(null, ids, startDate, endDate, null);
    }

    public Map totalDept(final String filterDeptIds, final String ids, final String startDate, final String endDate, final String allKeyInUse) {
        List<Map> listMap = new ArrayList<>();
        String sql ="select sum(key0) as key0, sum(key1) as key1, sum(key2) as key2, sum(key3) as key3, sum(key4) as key4, sum(key5) as key5, sum(key6) as key6," +
                "sum(case when status=2 then 1 else 0 end) servercount,sum(case when status!=2 then 1 else 0 end) cancelcount" +
                ",count(*) ticketcount,sum(unkey) unkey, sum(case when status=2 then waittime else 0 end) waittime," +
                "sum(case when status=2 then servicetime else 0 end) servicetime, " +
                "dbo.FN_SecondToString(sum(case when status=2 then waittime else 0 end)/sum(case when status=2 then 1 else 0 end)) as waittimeavg, " +
                "dbo.FN_SecondToString(sum(case when status=2 then servicetime else 0 end)/sum(case when status=2 then 1 else 0 end)) as servicetimeavg " +
                "from (select waittime, servicetime," +
                "(case when appriseresult=0 and ishj=1 and status=2 THEN 1 else 0 end ) as key0," +
                "(case when appriseresult=1 and ishj=1 and status=2 THEN 1 else 0 end ) as key1," +
                "(case when appriseresult=2 and ishj=1 and status=2 THEN 1 else 0 end ) as key2," +
                "(case when appriseresult=3 and ishj=1 and status=2 THEN 1 else 0 end ) as key3," +
                "(case when appriseresult=4 and ishj=1 and status=2 THEN 1 else 0 end ) as key4," +
                "(case when appriseresult=5 and ishj=1 and status=2 THEN 1 else 0 end ) as key5," +
                "(case when appriseresult=6 THEN 1 else 0 end ) as key6,(case status WHEN -1 THEN 1 else 0 end ) as unkey,status," +
                "1 as g from v_queuedetail where 1=1 ";
        //if(allKeyInUse!=null && allKeyInUse!="")
        //    sql += "and appriseresult in (" + allKeyInUse + ")";
        if(ids !=null && ids.length()>0)
            sql += "and deptId in (" + ids + ")";
        if(filterDeptIds !=null && filterDeptIds.length()>0)
            sql += "and deptId in (" + filterDeptIds + ")";
        if(null!=startDate && !"".equals(startDate)) {
        	sql = String.valueOf(sql) + "  and tickettime>='" + startDate + "'";
        }
        if(null!=endDate && !"".equals(endDate))
        	sql = String.valueOf(sql) + " and tickettime <='" + endDate + "'";
        sql = String.valueOf(sql) + ") as temp  group by g";
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Map totalDeptRpt(final String filterDeptIds, final String ids, final String startDate, final String endDate, final String allKeyInUse) {
        List<Map> listMap = new ArrayList<>();
        String sql ="select sum(ISNULL(totalkey0, 0)) as key0";
        sql = String.valueOf(sql) + ", sum(ISNULL(totalkey1, 0)) as key1";
        sql = String.valueOf(sql) + ", sum(ISNULL(totalkey2, 0)) as key2";
        sql = String.valueOf(sql) + ", sum(ISNULL(totalkey3, 0)) as key3";
        sql = String.valueOf(sql) + ", sum(ISNULL(totalkey4, 0)) as key4 ";
        sql = String.valueOf(sql) + ", sum(ISNULL(totalkey5, 0)) as key5";
        sql = String.valueOf(sql) + ", sum(ISNULL(totalkey6, 0)) as key6," +
                "sum(ISNULL(servicecount,0)) servercount,sum(ISNULL(cancelcount, 0)) cancelcount, " +
                "sum(totalwaittime)/nullif(sum(servicecount),0) waittime, sum(totalservicetime)/nullif(sum(servicecount),0) servicetime, " +
                "dbo.FN_SecondToString(sum(totalwaittime)/nullif(sum(servicecount),0)) as waittimeavg, " +
                "dbo.FN_SecondToString(sum(totalservicetime)/nullif(sum(servicecount),0)) as servicetimeavg," +
                "sum(totalkey) totalkey,SUM(totalunkey) totalunkey,SUM(totalkeymy) totalkeymy,SUM(totalkeybmy) totalkeybmy,SUM(ticketcount) ticketcount ";
        sql = String.valueOf(sql) + " from rpt_deptdata where 1=1 ";
        if(ids !=null && ids.length()>0)
            sql += "and deptId in (" + ids + ")";
        if(filterDeptIds !=null && filterDeptIds.length()>0)
            sql += "and deptId in (" + filterDeptIds + ")";
        if(null!=startDate && !"".equals(startDate)) {
            sql = String.valueOf(sql) + "  and servicedate>='" + startDate + "'";
        }
        if(null!=endDate && !"".equals(endDate))
            sql = String.valueOf(sql) + " and servicedate <='" + endDate + "'";
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public String totalDeptPauseTime(final String ids, final String startDate, final String endDate) {
        List<Map> listMap = new ArrayList<>();
        String sql = "select dbo.FN_SecondToString(sum(times)) pausetimes from queue_employeereport where action='pause' " +
                "and hallno in(select hallno from sys_queuehall where 1=1 ";
        if(ids !=null && ids.length()>0)
            sql += "and deptId in (" + ids + ")";
        sql +=") ";
        if(null!=startDate && !"".equals(startDate)) {
            sql = String.valueOf(sql) + "and starttime>='" + startDate + "' ";
        }
        if(null!=endDate && !"".equals(endDate))
            sql = String.valueOf(sql) + "and starttime <='" + endDate + "' ";
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForObject(sql, String.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public Map totalEmployee(final String ids, final String employeeid, final String startDate, final String endDate) {
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
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List totalDating(final String filterDeptIds, final String ids, final String dating, final String startDate, final String endDate, final Integer start, final Integer num, Integer[] total) {
        String sql = "select row_number() over(order by deptid) as rows,hallno,deptid,sum(key0) as key0, sum(key1) as key1, sum(key2) as key2, sum(key3) as key3, sum(key4) as key4, sum(key5) as key5, " +
                "sum(key6) as key6, sum(case when status=2 then 1 else 0 end) servercount,sum(cancelcount) cancelcount," +
                "dbo.FN_SecondToString(sum(case when status=2 then waittime else 0 end)/sum(case when status=2 then 1 else 0 end)) as waittimeavg, " +
                "dbo.FN_SecondToString(sum(case when status=2 then servicetime else 0 end)/sum(case when status=2 then 1 else 0 end)) as servicetimeavg from(" +
                "select hallno,deptid," +
                "(case when appriseresult=0 and ishj=1 and status=2 THEN 1 else 0 end) as key0," +
                "(case when appriseresult=1 and ishj=1 and status=2 THEN 1 else 0 end) as key1," +
                "(case when appriseresult=2 and ishj=1 and status=2 THEN 1 else 0 end ) as  key2," +
                "(case when appriseresult=3 and ishj=1 and status=2 THEN 1 else 0 end ) as key3," +
                "(case when appriseresult=4 and ishj=1 and status=2 THEN 1 else 0 end ) as key4," +
                "(case when appriseresult=5 and ishj=1 and status=2 THEN 1 else 0 end ) as key5," +
                "(case appriseresult WHEN 6 THEN 1 else 0 end ) as key6," +
                "(case status WHEN -1 THEN 1 else 0 end ) as cancelcount,waittime,servicetime,status " +
                "from v_queuedetail where 1=1 ";
        if(null!=startDate)
            sql += " and tickettime>='" + startDate + "' ";
        if(null!=endDate)
            sql += " and tickettime<='" + endDate + "' " ;
        if(null!=ids && !"".equals(ids))
            sql += "and deptid in( " + ids + ")";
        if(null!=dating && !"".equals(dating))
            sql += "and deptid in( " + dating + ")";
        if(null!=filterDeptIds && !"".equals(filterDeptIds))
            sql += "and deptid in( " + filterDeptIds + ")";
        sql += ") t1 group by deptid,hallno";

        if(total != null){
            String totalsql = "select count(*) from ("+sql+") t";
            total[0] = this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);
        }
        String nsql = sql;
        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
            nsql = "select t1.*, t2.name datingname,(select dbo.FN_SecondToString(sum(times)) from queue_employeereport t3 " +
                    "where action='pause' ";
            if(null!=startDate)
                nsql += " and starttime>='" + startDate + "' ";
            if(null!=endDate)
                nsql += " and starttime<='" + endDate + "' " ;
            nsql += "and t3.hallno=t1.hallno) pausetime from("+sql+") t1 left join appries_dept t2 on t1.deptid=t2.id";
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(nsql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List totalDatingRpt(final String filterDeptIds, final String ids, final String dating, final String startDate, final String endDate,
                               final Integer start, final Integer num, String sort, Integer[] total) {
        String sql = "select row_number() over(";
        if (StringUtils.isNotBlank(sort)){
            if(sort.startsWith("servicecount,") || sort.startsWith("cancelcount,") || sort.startsWith("totalunkey,") || sort.startsWith("totalkey,") || sort.startsWith("ticketcount,"))
                sql += "order by sum(" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("key0,")|| sort.startsWith("key1,")|| sort.startsWith("key2,")|| sort.startsWith("key3,")
                    || sort.startsWith("key4,")|| sort.startsWith("key5,") || sort.startsWith("key6,")
                    || sort.startsWith("keymy,") || sort.startsWith("keybmy,"))
                sql += "order by sum(total" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("waittime,") || sort.startsWith("servicetime,") || sort.startsWith("pausetime,")
                    || sort.startsWith("myd,"))
                sql += "order by sum(total" + sort.replace(",", ")/nullif(sum(servicecount),0) ");
            else if(sort.startsWith("keymyl,"))
                sql += "order by 1.0*sum(totalkeymy)/nullif(sum(servicecount),0) " + sort.split(",")[1];
            else
                sql += "order by " + sort.replace(",", " ");
        }
        else
            sql += "order by deptid";
        sql +=") as rows,deptname datingname,deptid," +
                "sum(totalkey0) as key0, sum(totalkey1) as key1, sum(totalkey2) as key2, sum(totalkey3) as key3, sum(totalkey4) as key4, sum(totalkey5) as key5, " +
                "sum(totalkey6) as key6, sum(totalkeymy) msum,sum(totalkeybmy) bmsum,sum(totalunkey) totalunkey,sum(totalkey) totalkey," +
                "sum(servicecount) servercount,sum(cancelcount) cancelcount,sum(ticketcount) ticketcount," +
                "dbo.FN_SecondToString(sum(totalwaittime)/nullif(sum(servicecount),0)) as waittimeavg, " +
                "dbo.FN_SecondToString(sum(totalservicetime)/nullif(sum(servicecount),0)) as servicetimeavg," +
                "dbo.FN_SecondToString(sum(totalpausetime)) as pausetime," +
                "sum(totalkeymy)*1.0/nullif(sum(servicecount),0) myl,sum(totalmyd)/nullif(sum(servicecount),0) myd " +
                "from rpt_deptdata where 1=1 ";
        if(null!=startDate)
            sql += " and servicedate>='" + startDate + "' ";
        if(null!=endDate)
            sql += " and servicedate<='" + endDate + "' " ;
        if(null!=ids && !"".equals(ids))
            sql += "and deptid in( " + ids + ")";
        if(null!=dating && !"".equals(dating))
            sql += "and deptid in( " + dating + ")";
        if(null!=filterDeptIds && !"".equals(filterDeptIds))
            sql += "and deptid in( " + filterDeptIds + ")";
        sql += " group by deptid,deptname";
        logger.debug(sql);
        if(total != null){
            String totalsql = "select count(*) from ("+sql+") t";
            total[0] = this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);
        }
        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int counttotalDating(final String ids, final String startDate, final String endDate) {
        String sql = "select   sum(key0)   as key0 , sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as key3, sum(key4)  as key4, " +
                "sum(key5)   as key5, sum(key6)   as key6 ,datingid, datingname " +
                "from(select temp.id,serviceDate,DeptId,employeeId,key0,key1 ,key2,key3,key4,key5,key6 ,t1.id as windowid ,t1.name as windowname,t2.id as datingid ,t2.name as datingname        " +
                "from(select id,serviceDate,DeptId,employeeId, (case keyno WHEN 0  THEN   1 else 0  end ) as key0, " +
                "(case keyno WHEN 1 THEN 1 else 0 end) as key1," +
                "(case keyno WHEN 2 THEN 1 else 0 end ) as key2, (case keyno WHEN 3 THEN 1 else 0 end) as key3," +
                "(case keyno WHEN 4 THEN 1 else 0 end ) as key4, (case keyno WHEN 5 THEN 1 else 0 end) as key5," +
                "(case keyno WHEN 6 THEN 1 else 0 end ) as key6 " +
                "from appries_appries  where 1=1 ";
        if(startDate!=null)
            sql += "and serviceDate>='" + startDate + "' ";
        if(endDate!=null)
            sql += "  and serviceDate<='" + endDate + "' ";
        sql += ")as temp,appries_dept t1,appries_dept t2" + " where   temp.DeptId=t1.id and  t1.fatherId=t2.id and t2.fatherId in( " + ids + ")" + " ) " + " " +
                "as A group by datingid, datingname  ";
        sql = "select count(*) from (" + sql + " ) as tempcount";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List totalWindow(final String datingId, final String startDate, final String endDate, final Integer start, final Integer num, int[] rowsCount) {
        String sql = "select row_number() over(order by hallno, hjcounter) as rows,hallno,hjcounter,sum(key0) as key0, sum(key1) as key1, sum(key2) as key2, sum(key3) as key3, " +
                "sum(key4) as key4, sum(key5) as key5, " +
                "sum(key6) as key6, sum(case when status=2 then 1 else 0 end) servercount,sum(case when status!=2 then 1 else 0 end) cancelcount," +
                "sum(case when status=2 then waittime else 0 end) as totalwaittime, " +
                "sum(case when status=2 then servicetime else 0 end) as totalservicetime from(" +
                "select hallno, hjcounter," +
                "(case when appriseresult=0 and ishj=1 and status=2 THEN 1 else 0  end) as key0," +
                "(case when appriseresult=1 and ishj=1 and status=2 THEN 1 else 0 end) as key1," +
                "(case when appriseresult=2 and ishj=1 and status=2 THEN 1 else 0 end ) as key2," +
                "(case when appriseresult=3 and ishj=1 and status=2 THEN 1 else 0 end ) as key3, " +
                "(case when appriseresult=4 and ishj=1 and status=2 THEN 1 else 0 end ) as key4," +
                "(case when appriseresult=5 and ishj=1 and status=2 THEN 1 else 0 end ) as key5," +
                "(case when appriseresult=6 THEN 1 else 0 end ) as key6, " +
                "(case status WHEN -1 THEN 1 else 0 end ) as unkey,waittime,servicetime,status " +
                "from v_queuedetail where hjcounter is not null ";
        if(null!=startDate)
            sql += " and tickettime>='" + startDate + "' ";
        if(null!=endDate)
            sql += " and tickettime<='" + endDate + "' " ;
        if(null!=datingId && !"".equals(datingId))
            sql += "and hjcounterdeptid in( " + datingId + ")";
        sql += ") t1 group by hjcounter,hallno";

        if(rowsCount != null && rowsCount.length==1){
            String totalsql = "select count(*) from ("+sql+") t";
            rowsCount[0] = this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);
        }
        String nsql = sql;
        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
            nsql = "select t1.*,dbo.FN_SecondToString(case when servercount=0 then 0 else t1.totalwaittime/t1.servercount end) as waittimeavg" +
                    ",dbo.FN_SecondToString((case when servercount=0 then 0 else t1.totalservicetime/servercount end)) as servicetimeavg" +
                    ", t2.countername windowname,t4.name deptname, (select dbo.FN_SecondToString(sum(times)) from queue_employeereport t3 " +
                    "where action='pause' ";
            if(null!=startDate)
                nsql += "and starttime>='" + startDate + "' ";
            if(null!=endDate)
                nsql += "and starttime<='" + endDate + "' " ;
            nsql += "and t3.hallno=t1.hallno and t3.counterno=t1.hjcounter) pausetime from("+sql+") t1 left join sys_queuecounter t2 on t1.hjcounter=t2.counterno " +
                    "left join sys_queuehall t3 on t3.hallno=t2.hallno left join appries_dept t4 on t4.id=t3.deptid";
        }
        try {
            logger.debug(nsql);
            return this.getJdbcTemplate().queryForList(nsql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List totalBiz(final String datingId, String bizname, final String startDate, final String endDate, final Integer start, final Integer num, int[] rowsCount) {
        String sql = "select row_number() over(order by deptid,bizid) as rows,bizid, bizname,deptid," +
                "sum(key0) as key0, sum(key1) as key1, sum(key2) as key2, sum(key3) as key3, " +
                "sum(key4) as key4, sum(key5) as key5, " +
                "sum(key6) as key6, sum(case when status=2 then 1 else 0 end) servercount,sum(cancelcount) cancelcount," +
                "sum(case when status=2 then waittime else 0 end) as totalwaittime, " +
                "sum(case when status=2 then servicetime else 0 end) as totalservicetime from(" +
                "select t1.hallno, t1.hjcounter,t1.bizid,t2.bizname,t2.deptid,t2.deptname," +
                "(case when appriseresult=0 and ishj=1 and status=2 THEN 1 else 0 end) as key0," +
                "(case when appriseresult=1 and ishj=1 and status=2 THEN 1 else 0 end) as key1," +
                "(case when appriseresult=2 and ishj=1 and status=2 THEN 1 else 0 end) as key2," +
                "(case when appriseresult=3 and ishj=1 and status=2 THEN 1 else 0 end) as key3, " +
                "(case when appriseresult=4 and ishj=1 and status=2 THEN 1 else 0 end) as key4," +
                "(case when appriseresult=5 and ishj=1 and status=2 THEN 1 else 0 end) as key5," +
                "(case when appriseresult=6 THEN 1 else 0 end ) as key6," +
                "(case when status=-1 THEN 1 else 0 end) as cancelcount,waittime,servicetime,status " +
                "from v_queuedetail t1 left join sys_queueserial t2 on t1.bizid=t2.bizid and t1.deptid=t2.deptid where 1=1 ";
        if(null!=startDate)
            sql += " and tickettime>='" + startDate + "' ";
        if(null!=endDate)
            sql += " and tickettime<='" + endDate + "' " ;
        if(null!=datingId && !"".equals(datingId))
            sql += "and t1.deptid in( " + datingId + ") ";
        if (StringUtils.isNotBlank(bizname))
            sql += "and t2.bizname like '%"+bizname+"%' ";
        sql += ") t1 group by t1.bizid,t1.bizname,deptid";

        if(rowsCount != null && rowsCount.length==1){
            String totalsql = "select count(*) from ("+sql+") t";
            rowsCount[0] = this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);
        }
        if (start != null && num != null) {
            sql = "select t2.name as deptname,t1.*,dbo.FN_SecondToString(case when servercount=0 then 0 else t1.totalwaittime/t1.servercount end) as waittimeavg" +
                    ",dbo.FN_SecondToString((case when servercount=0 then 0 else t1.totalservicetime/t1.servercount end)) as servicetimeavg from ("
                    +sql+") t1 left join appries_dept t2 on t1.deptid=t2.id where t1.rows>" + start + " and t1.rows<=" + (num+start);
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List totalBizRpt(final String datingId, String sort, String bizname, final String startDate, final String endDate, final Integer start, final Integer num, int[] rowsCount) {
        String sql = "select row_number() over(";
        if (StringUtils.isNotBlank(sort)){
            if(sort.startsWith("servicecount,") || sort.startsWith("cancelcount,") || sort.startsWith("totalunkey,") || sort.startsWith("totalkey,") || sort.startsWith("ticketcount,"))
                sql += "order by sum(" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("key0,")|| sort.startsWith("key1,")|| sort.startsWith("key2,")|| sort.startsWith("key3,")
                    || sort.startsWith("key4,")|| sort.startsWith("key5,") || sort.startsWith("key6,")
                    || sort.startsWith("keymy,") || sort.startsWith("keybmy,"))
                sql += "order by sum(total" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("waittime,") || sort.startsWith("servicetime,") || sort.startsWith("pausetime,")
                    || sort.startsWith("myd,"))
                sql += "order by sum(total" + sort.replace(",", ")/nullif(sum(servicecount),0) ");
            else if(sort.startsWith("keymyl,"))
                sql += "order by 1.0*sum(totalkeymy)/nullif(sum(servicecount),0) " + sort.split(",")[1];
            else
                sql += "order by " + sort.replace(",", " ");
        }
        else
            sql += "order by deptid,bizid";
        sql +=") as rows,deptid,deptname,bizid, bizname," +
                "sum(totalkey0) as key0, sum(totalkey1) as key1, sum(totalkey2) as key2, sum(totalkey3) as key3, sum(totalkey4) as key4, sum(totalkey5) as key5, " +
                "sum(totalkey6) as key6, sum(servicecount) servercount,sum(cancelcount) cancelcount,sum(totalunkey) totalunkey,sum(totalkey) totalkey,sum(ticketcount) ticketcount," +
                "dbo.FN_SecondToString(sum(totalwaittime)/nullif(sum(servicecount),0)) as waittimeavg, " +
                "dbo.FN_SecondToString(sum(totalservicetime)/nullif(sum(servicecount),0)) as servicetimeavg, " +
                "dbo.FN_SecondToString(sum(totalpausetime)) as pausetime," +
                "sum(totalkeymy)*1.0/nullif(sum(servicecount),0) myl,sum(totalmyd)/nullif(sum(servicecount),0) myd " +
                "from rpt_bizdata where 1=1 ";
        if(null!=startDate)
            sql += " and servicedate>='" + startDate + "' ";
        if(null!=endDate)
            sql += " and servicedate<='" + endDate + "' " ;
        if(null!=datingId && !"".equals(datingId))
            sql += "and deptid in( " + datingId + ") ";
        if (StringUtils.isNotBlank(bizname))
            sql += "and bizname like '%"+bizname+"%' ";
        sql += " group by deptid,deptname,bizid, bizname";

        if(rowsCount != null && rowsCount.length==1){
            String totalsql = "select count(*) from ("+sql+") t";
            rowsCount[0] = this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);
        }
        if (start != null && num != null && num > 0) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List totalWindowRpt(final String datingId, final String sort, final String startDate, final String endDate, final Integer start, final Integer num, int[] rowsCount) {
        String sql = "select row_number() over(";
        if (StringUtils.isNotBlank(sort)){
            if(sort.startsWith("servicecount,") || sort.startsWith("cancelcount,") || sort.startsWith("totalunkey,") || sort.startsWith("totalkey,") || sort.startsWith("ticketcount,"))
                sql += "order by sum(" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("key0,")|| sort.startsWith("key1,")|| sort.startsWith("key2,")|| sort.startsWith("key3,")
                    || sort.startsWith("key4,")|| sort.startsWith("key5,") || sort.startsWith("key6,")
                    || sort.startsWith("keymy,") || sort.startsWith("keybmy,"))
                sql += "order by sum(total" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("waittime,") || sort.startsWith("servicetime,") || sort.startsWith("pausetime,")
                    || sort.startsWith("myd,"))
                sql += "order by sum(total" + sort.replace(",", ")/nullif(sum(servicecount),0) ");
            else if(sort.startsWith("keymyl,"))
                sql += "order by 1.0*sum(totalkeymy)/nullif(sum(servicecount),0) " + sort.split(",")[1];
            else
                sql += "order by " + sort.replace(",", " ");
        }
        else
            sql += "order by deptid,counterno";
        sql += ") as rows,deptid,counterno," +
                "sum(totalkey0) as key0, sum(totalkey1) as key1, sum(totalkey2) as key2, sum(totalkey3) as key3, sum(totalkey4) as key4, sum(totalkey5) as key5, " +
                "sum(totalkey6) as key6, sum(ticketcount) ticketcount, sum(servicecount) servercount,sum(cancelcount) cancelcount,sum(totalunkey) totalunkey,sum(totalkey) totalkey," +
                "dbo.FN_SecondToString(sum(totalwaittime)/nullif(sum(servicecount),0)) as waittimeavg, " +
                "dbo.FN_SecondToString(sum(totalservicetime)/nullif(sum(servicecount),0)) as servicetimeavg, " +
                "dbo.FN_SecondToString(sum(totalpausetime)) as pausetime," +
                "sum(totalkeymy)*1.0/nullif(sum(servicecount),0) myl,sum(totalmyd)/nullif(sum(servicecount),0) myd " +
                "from rpt_counterdata where 1=1 ";
        if(null!=startDate)
            sql += " and servicedate>='" + startDate + "' ";
        if(null!=endDate)
            sql += " and servicedate<='" + endDate + "' " ;
        if(null!=datingId && !"".equals(datingId))
            sql += "and deptid in( " + datingId + ")";
        sql += " group by deptid,counterno";

        if(rowsCount != null && rowsCount.length==1){
            String totalsql = "select count(*) from ("+sql+") t";
            rowsCount[0] = this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);
        }
        if (start != null && num != null && num>0) {
            sql = "select t.*,t2.name deptname, t3.countername windowname " +
                    "from ("+sql+") t left join appries_dept t2 on t.deptid=t2.id left join sys_queuecounter t3 on t3.counterno=t.counterno " +
                    "where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        else{
            sql = "select t.*,t2.name deptname, t3.countername windowname " +
                    "from ("+sql+") t left join appries_dept t2 on t.deptid=t2.id left join sys_queuecounter t3 on t3.counterno=t.counterno ";
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
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
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List totalPerson3(final String windowId, final String startDate, final String endDate, final Integer start, final Integer num) {
        String sql = "select t.id,t.serviceDate,t.DeptId, appries_employee.Id as employeeId,IFNULL(t.key0,0) as key0,IFNULL(t.key1,0) as key1,IFNULL(t.key2,0) as key2," +
                "IFNULL(t.key3,0) as key3,IFNULL(t.key4,0) as key4,IFNULL(t.key5,0) as key5,IFNULL(t.key6,0) as key6," +
                "appries_employee.Name as employeeName from (select id,serviceDate,DeptId,employeeId, " +
                "sum(key0)   as key0, sum(key1)   as key1, sum(key2)   as key2, sum(key3)  as  key3, sum(key4)  as  key4 , " +
                "sum(key5)   as key5, sum(key6)   as key6 from ( select id,serviceDate,DeptId,employeeId,(case keyno WHEN 0  THEN   1 else 0  end ) as key0, (case keyno WHEN 1  THEN   1 else 0    end ) as key1 ,  (case keyno WHEN 2  THEN   1 else 0    end ) as  key2  , (case keyno WHEN 3  THEN   1 else 0    end ) as  key3  ,  (case keyno WHEN 4  THEN   1 else 0    end ) as  key4 ,  (case keyno WHEN 5  THEN   1 else 0    end ) as  key5,(case keyno WHEN 6  THEN   1 else 0    end ) as  key6  from appries_appries   where DeptId in (" + windowId + ") and serviceDate>='" + startDate + "' " + " and serviceDate <='" + endDate + "') as temp  group by employeeId )" + " as t right JOIN appries_employee ON t.employeeId=appries_employee.Id  ";
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

    public List totalPerson(final String windowId, final String cardnum, final String startDate, final String endDate, final Integer start, final Integer num, Integer[] totalrows) {
        String sql = "select row_number() over(order by staffno) as rows,hallno,staffno,sum(key0) as key0 , sum(key1) as key1, " +
                "sum(key2) as key2, sum(key3) as key3, sum(key4) as key4, sum(key5) as key5, " +
                "sum(key6) as key6, sum(case when status=2 then 1 else 0 end) servercount,sum(case when status!=2 then 1 else 0 end) cancelcount," +
                "sum(case when status=2 then waittime else 0 end) totalwaittime, sum(case when status=2 then servicetime else 0 end) as totalservicetime from(" +
                "select staffno,hallno," +
                "(case when appriseresult=0 and ishj=1 and status=2 THEN 1 else 0  end) as key0, " +
                "(case when appriseresult=1 and ishj=1 and status=2 THEN 1 else 0 end) as key1, " +
                "(case when appriseresult=2 and ishj=1 and status=2 THEN 1 else 0 end) as key2 ," +
                "(case when appriseresult=3 and ishj=1 and status=2 THEN 1 else 0 end) as key3 , " +
                "(case when appriseresult=4 and ishj=1 and status=2 THEN 1 else 0 end) as key4 ," +
                "(case when appriseresult=5 and ishj=1 and status=2 THEN 1 else 0 end) as key5," +
                "(case appriseresult WHEN 6 THEN 1 else 0 end) as key6, (case status WHEN -1 THEN 1 else 0 end) as unkey,waittime,servicetime,status " +
                "from v_queuedetail where staffno is not null ";
        if(null!=startDate)
            sql += " and tickettime>='" + startDate + "' ";
        if(null!=endDate)
            sql += " and tickettime<='" + endDate + "' " ;
        if(windowId!=null && windowId!="")
            sql += "and deptId in (" + windowId + ") ";
        if(cardnum!=null && cardnum!="")
            sql += "and cardnum in (" + cardnum + ") ";
        sql += ") t1 group by staffno,hallno";

        if(totalrows != null && totalrows.length==1){
            String totalsql = "select count(*) from ("+sql+") t";
            totalrows[0] = this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);
        }
        String nsql = sql;
        if (start != null && num != null) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
            nsql = "select t1.*,dbo.FN_SecondToString(totalwaittime/servercount) as waittimeavg, dbo.FN_SecondToString(totalservicetime/servercount) as servicetimeavg, " +
                    "t2.name employeeName,t3.name deptname ,(select dbo.FN_SecondToString(sum(times)) from queue_employeereport t3 " +
                    "where action='pause' ";
            if(null!=startDate)
                nsql += " and starttime>='" + startDate + "' and endtime>='" + startDate + "' ";
            if(null!=endDate)
                nsql += " and starttime<='" + endDate + "'  and endtime<='" + endDate + "' " ;
            nsql += "and t3.hallno=t1.hallno and t3.eno=t1.staffno) pausetime from("+sql+") t1 left join appries_employee t2 on t1.staffno=t2.cardnum " +
                    "left join appries_dept t3 on t2.deptid=t3.id";
        }
        try {
            logger.debug(nsql);
            return this.getJdbcTemplate().queryForList(nsql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List totalPersonRpt(final String windowId, final String sort, final String cardnum, final String startDate, final String endDate, final Integer start, final Integer num, Integer[] totalrows) {
        String sql = "select row_number() over(";
        if (StringUtils.isNotBlank(sort)){
            if(sort.startsWith("servicecount,") || sort.startsWith("cancelcount,") || sort.startsWith("totalunkey,") || sort.startsWith("totalkey,") || sort.startsWith("ticketcount,"))
                sql += "order by sum(" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("key0,")|| sort.startsWith("key1,")|| sort.startsWith("key2,")|| sort.startsWith("key3,")
                    || sort.startsWith("key4,")|| sort.startsWith("key5,") || sort.startsWith("key6,")
                    || sort.startsWith("keymy,") || sort.startsWith("keybmy,"))
                sql += "order by sum(total" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("waittime,") || sort.startsWith("servicetime,") || sort.startsWith("pausetime,")
                    || sort.startsWith("myd,"))
                sql += "order by sum(total" + sort.replace(",", ")/nullif(sum(servicecount),0) ");
            else if(sort.startsWith("keymyl,"))
                sql += "order by 1.0*sum(totalkeymy)/nullif(sum(servicecount),0) " + sort.split(",")[1];
            else
                sql += "order by " + sort.replace(",", " ");
        }
        else
            sql += "order by deptid,eno";
        sql += ") as rows,deptid,deptname,eno staffno,ename employeeName,sum(totalkey0) as key0, sum(totalkey1) as key1, " +
                "sum(totalkey2) as key2, sum(totalkey3) as key3, sum(totalkey4) as key4, sum(totalkey5) as key5, " +
                "sum(totalkey6) as key6, sum(ticketcount) ticketcount, sum(servicecount) servercount,sum(cancelcount) cancelcount," +
                "sum(totalkeymy) msum,sum(totalkeybmy) bmsum,sum(totalunkey) totalunkey,sum(totalkey) totalkey," +
                "dbo.FN_SecondToString(sum(totalwaittime)/nullif(sum(servicecount),0)) as waittimeavg, " +
                "dbo.FN_SecondToString(sum(totalservicetime)/nullif(sum(servicecount),0)) as servicetimeavg," +
                "dbo.FN_SecondToString(sum(totalpausetime)) pausetime," +
                "sum(totalkeymy)*1.0/nullif(sum(servicecount),0) myl,sum(totalmyd)/nullif(sum(servicecount),0) myd " +
                "from rpt_employeedata where 1=1 ";
        if(null!=startDate)
            sql += " and servicedate>='" + startDate + "' ";
        if(null!=endDate)
            sql += " and servicedate<='" + endDate + "' " ;
        if(windowId!=null && windowId!="")
            sql += "and deptId in (" + windowId + ") ";
        if(cardnum!=null && cardnum!="")
            sql += "and eno in (" + cardnum + ") ";
        sql += " group by deptid,deptname,eno,ename";

        if(totalrows != null && totalrows.length==1){
            String totalsql = "select count(*) from ("+sql+") t";
            totalrows[0] = this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);
        }
        if (start != null && num != null && num > 0) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List totalProxyRpt(final String windowId, final String sort, final String cardname, final String startDate, final String endDate, final Integer start, final Integer num, Integer[] totalrows) {
        String sql = "select row_number() over(";
        if (StringUtils.isNotBlank(sort)){
            if(sort.startsWith("servicecount,") || sort.startsWith("cancelcount,") || sort.startsWith("totalunkey,") || sort.startsWith("totalkey,") || sort.startsWith("ticketcount,"))
                sql += "order by sum(" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("key0,")|| sort.startsWith("key1,")|| sort.startsWith("key2,")|| sort.startsWith("key3,")
                    || sort.startsWith("key4,")|| sort.startsWith("key5,") || sort.startsWith("key6,")
                    || sort.startsWith("keymy,") || sort.startsWith("keybmy,"))
                sql += "order by sum(total" + sort.replace(",", ") ");//order by sum(servicecount) desc
            else if(sort.startsWith("waittime,") || sort.startsWith("servicetime,") || sort.startsWith("pausetime,")
                    || sort.startsWith("myd,"))
                sql += "order by sum(total" + sort.replace(",", ")/nullif(sum(servicecount),0) ");
            else if(sort.startsWith("keymyl,"))
                sql += "order by 1.0*sum(totalkeymy)/nullif(sum(servicecount),0) " + sort.split(",")[1];
            else
                sql += "order by " + sort.replace(",", " ");
        }
        else
            sql += "order by cardtype,cardid,cardname";
        sql += ") as rows,cardtype,cardid,cardname,sum(totalkey0) as key0, sum(totalkey1) as key1, " +
                "sum(totalkey2) as key2, sum(totalkey3) as key3, sum(totalkey4) as key4, sum(totalkey5) as key5, " +
                "sum(totalkey6) as key6, sum(ticketcount) ticketcount, sum(servicecount) servercount,sum(cancelcount) cancelcount," +
                "sum(totalkeymy) msum,sum(totalkeybmy) bmsum,sum(totalunkey) totalunkey,sum(totalkey) totalkey," +
                "dbo.FN_SecondToString(sum(totalwaittime)/nullif(sum(servicecount),0)) as waittimeavg, " +
                "dbo.FN_SecondToString(sum(totalservicetime)/nullif(sum(servicecount),0)) as servicetimeavg," +
                "dbo.FN_SecondToString(sum(totalpausetime)) pausetime," +
                "sum(totalkeymy)*1.0/nullif(sum(servicecount),0) myl,sum(totalmyd)/nullif(sum(servicecount),0) myd " +
                "from rpt_proxydata where 1=1 ";
        if(null!=startDate)
            sql += " and servicedate>='" + startDate + "' ";
        if(null!=endDate)
            sql += " and servicedate<='" + endDate + "' " ;
        if(StringUtils.isNotBlank(cardname))
            sql += "and cardname like '%" + cardname + "%' ";
        sql += " group by cardtype,cardid,cardname";

        if(totalrows != null && totalrows.length==1){
            String totalsql = "select count(*) from ("+sql+") t";
            totalrows[0] = this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);
        }
        if (start != null && num != null && num > 0) {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        }
        try {
            logger.debug(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
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
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
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
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
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
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
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
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
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
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int countEmployeeAll() {
        final String sql = "select count(*) from appries_employee";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
