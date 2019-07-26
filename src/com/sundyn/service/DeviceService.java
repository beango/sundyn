package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.sundyn.util.DateHelper;
import com.sundyn.vo.AdviceVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DeviceService extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public List findBatch() {
        String sql = "select * from appries_devicebatch order by id desc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findDeviceBatch(final int startrow, final int pageSize, int[] total) {
        String sql = "";
        sql = "select row_number() over(order by id desc) as rows, *,(select count(*) from appries_device where batchid=appries_devicebatch.id) devices from appries_devicebatch";
        sql = "select * from ("+sql+") t2 where t2.rows>" + startrow + " and t2.rows<=" + (startrow+pageSize);
        String totalsql = "select count(*) c from ("+sql+") t2";
        total[0] =this.getJdbcTemplate().queryForObject(totalsql,null, java.lang.Integer.class);

        final List advices = new ArrayList();
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addBatch(String batchid, String batchname, String batchdate, String batchid_deviceclass_1, String batchid_deviceclass_2, String batchid_year, String batchid_month, String batchid_no) {
        final String sql1 = "Insert into appries_devicebatch (batchid, batchname, batchdate,batchid_deviceclass_1,batchid_deviceclass_2,batchid_year,batchid_month,batchid_no) values(?,?,?,?,?,?,?,?)";
        final Object[] arg = { batchid, batchname, batchdate, batchid_deviceclass_1,batchid_deviceclass_2,batchid_year,batchid_month,batchid_no};
        try {
            this.getJdbcTemplate().update(sql1, arg);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean batchDelete(String id) {
        final String sql = "delete from appries_devicebatch where id=?";
        final Object[] arg = { id };
        try {
            return this.getJdbcTemplate().update(sql, arg)>0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Object findBatchById(String id) {
        final AdviceVo ad = new AdviceVo();
        final String sql = "select * from appries_devicebatch  where id=?";
        final Object[] arg = { id };
        try {
            return this.getJdbcTemplate().queryForMap(sql, arg);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean batchUpdate(final String id,final String batchid, final String batchname, final String batchdate,
            final String batchid_deviceclass_1, final String batchid_deviceclass_2, final String batchid_year, final String batchid_month, final String batchid_no) {
        final String sql = "update appries_devicebatch set batchid=?,batchname=?,batchdate=?," +
                "batchid_deviceclass_1=?,batchid_deviceclass_2=?,batchid_year=?,batchid_month=?,batchid_no=? where id=?";
        final Object[] args = { batchid, batchname, batchdate,batchid_deviceclass_1,batchid_deviceclass_2,batchid_year, batchid_month, batchid_no, id};
        try {
            this.getJdbcTemplate().update(sql, args);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List findBatchByBatchId(String batchid, String id) {
        String sql = "select * from appries_devicebatch where batchid=? ";
        if(id!=null && !"".equals(id))
            sql = sql + "and id != " + id;
        final Object[] arg = { batchid };
        try {
            return this.getJdbcTemplate().queryForList(sql, arg);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Map findByMac(final String mac) {
        final String sql = "select * from appries_device where mac=?";
        final Map tempMap = null;
        final Object[] args = { mac };
        try {
            return this.getJdbcTemplate().queryForMap(sql, args);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean addDevice(final String id, String batchid, String mac, String ver, String ctime, String prdcode, String androidVer) {
        final String sql = "{call deviceAdd(?,?,?,?,?,?)}";
        final Object[] args = {id, batchid, ctime, mac, prdcode, androidVer};
        try {
            return this.getJdbcTemplate().update(sql, args)>0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void findAndAddByMac(String mac, String prdcode, String androidVer) {
        Map deviceMap = findByMac(mac);
        addDevice(deviceMap==null?"":deviceMap.get("id").toString(), "", mac, "", DateHelper.getInstance().getDataString_1(null), prdcode, androidVer);
        String sql = "update appries_device set lastonlinetime=? where mac=?";
        final Object[] arg = {DateHelper.getInstance().getNow(), mac};
        try {
            this.getJdbcTemplate().update(sql, arg);
        }
        catch (Exception e) {
            logger.error(e);
        }
    }

    public List findDevice(final String namagername, final String batchno, final String mac, final String startDate, final String endDate, final int startrow, final int pageSize, int[] total) {
        String sql = "";
        sql = "select row_number() over(order by t1.ctime desc) as rows, t1.*,t2.batchid batchid2,t2.batchname,t3.name managername, " +
                "t4.productname, t4.productcode,t5.orgname, t6.totaldowntimes, t6.downedtimes, t6.lstdowntime " +
                "from appries_device t1 left join appries_devicebatch t2 on t1.batchid=t2.id " +
                "left join appries_manager t3 on t3.id=t1.managerid " +
                "left join order_product t4 on t4.id=t1.productid left join appries_managerext t5 on t3.id=t5.userid " +
                "left join order_licensedetail t6 on t6.devicemac=t1.mac where 1=1 ";
        if (startDate!=null && !startDate.equals(""))
            sql += "and ctime>='" + startDate+"' ";
        if (endDate!=null && !endDate.equals(""))
            sql += "and ctime<'" + endDate+"' ";
        if (batchno!=null && !batchno.equals(""))
            sql += "and t2.batchid='" + batchno+"' ";
        if (mac!=null && !mac.equals(""))
            sql += "and t1.mac like '%" + mac+"%' ";
        if (namagername!=null && !namagername.equals(""))
            sql += "and (t3.name like '%" + namagername+"%' or t5.orgname like '%"+namagername+"%')";

        String totalsql = "select count(*) c from ("+sql+") t2";
        total[0] =this.getJdbcTemplate().queryForObject(totalsql, null, Integer.class);

        sql = "select * from ("+sql+") t2 where t2.rows>" + startrow + " and t2.rows<=" + (startrow+pageSize);

        final List advices = new ArrayList();
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Object findDeviceById(String id) {
        final AdviceVo ad = new AdviceVo();
        final String sql = "select * from appries_device where id=?";
        final Object[] arg = { id };
        try {
            final Map map = this.getJdbcTemplate().queryForMap(sql, arg);
            return map;
        }
        catch (Exception e) {
            return null;
        }
    }

    public List findDeviceByBatchId(String batchid) {
        final AdviceVo ad = new AdviceVo();
        final String sql = "select * from appries_device where batchid=?";
        final Object[] arg = { batchid };
        try {
            return this.getJdbcTemplate().queryForList(sql, arg);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List existDeviceByMAC(String mac, Object id) {
        String sql = "select * from appries_device where mac=?";
        if(id!=null && !"".equals(id))
            sql = sql + " and id != " + id;
        final Object[] arg = { mac };
        try {
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql, arg);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void deviceUpdate(String id, String batchid, String mac, boolean cerFlag, String ver, String ctime) {
        final String sql = "{call deviceAdd(?,?,?,?)}";
        final Object[] args = { id,batchid, ctime, mac};
        try {
            this.getJdbcTemplate().update(sql, args);
        }
        catch (Exception e) {
        }
    }

    public boolean deviceDelete(String id) {
        final String sql = "delete from appries_device where id=?";
        final Object[] arg = { id };
        try {
            return this.getJdbcTemplate().update(sql, arg)>0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateDeviceInfo(String mac, String prdcode, String androidVer) {
        try{
            String sql = "update appries_device set curver=? where mac=? and (installver is not null and installver != '');" +
                    "update appries_device set installver=? where mac=? and (installver is null or installver='')";
            this.getJdbcTemplate().update(sql, new Object[]{androidVer, mac, androidVer, mac});
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
