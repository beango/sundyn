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
        String totalsql = "select max(rows) c from ("+sql+") t2";
        total[0] =this.getJdbcTemplate().queryForInt(totalsql);

        final List advices = new ArrayList();
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addBatch(String batchid, String batchname, String batchdate) {
        final String sql1 = "Insert into appries_devicebatch (batchid, batchname, batchdate) values(?,?,?)";

        final Object[] arg = { batchid, batchname, batchdate};
        try {
            this.getJdbcTemplate().update(sql1, arg);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
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
            final Map map = this.getJdbcTemplate().queryForMap(sql, arg);
            return map;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean batchUpdate(final String id,final String batchid, final String batchname, final String batchdate) {
        final String sql = "update appries_devicebatch set batchid=?,batchname=?,batchdate=? where id=?";
        final Object[] args = { batchid, batchname, batchdate, id};
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

    public boolean addDevice(String batchid, String mac, boolean verifyFlag, String ver, String ctime) {
        final String sql1 = "Insert into appries_device (batchid, mac, verifyFlag, ver, ctime) values(?,?,?,?,?)";

        final Object[] arg = {batchid, mac, verifyFlag, ver, ctime};
        try {
            this.getJdbcTemplate().update(sql1, arg);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void findAndAddByMac(String mac) {
        if (findByMac(mac)==null){
            addDevice("", mac, false, "", DateHelper.getInstance().getDataString_1(null));
        }
        String sql = "update appries_device set lastonlinetime=? where mac=?";
        final Object[] arg = {DateHelper.getInstance().getNow(), mac};
        try {
            this.getJdbcTemplate().update(sql, arg);
        }
        catch (Exception e) {
        }
    }

    public List findDevice(final int startrow, final int pageSize, int[] total) {
        String sql = "";
        sql = "select row_number() over(order by t1.id desc) as rows, t1.*,t2.batchid batchid2,t2.batchname from appries_device t1 left join appries_devicebatch t2 on t1.batchid=t2.id";
        sql = "select * from ("+sql+") t2 where t2.rows>" + startrow + " and t2.rows<=" + (startrow+pageSize);
        String totalsql = "select max(rows) c from ("+sql+") t2";
        total[0] =this.getJdbcTemplate().queryForInt(totalsql);

        final List advices = new ArrayList();
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }

    public List existDeviceByMAC(String mac, Object id) {
        String sql = "select * from appries_device where mac=?";
        if(id!=null && !"".equals(id))
            sql = sql + " and id != " + id;
        final Object[] arg = { mac };
        try {
            return this.getJdbcTemplate().queryForList(sql, arg);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void deviceUpdate(String id, String batchid, String mac, boolean cerFlag, String ver, String ctime) {
        final String sql = "update appries_device set batchid=?,ctime=? where id=?";
        final Object[] args = {batchid, ctime, id};
        try {
            this.getJdbcTemplate().update(sql, args);
        }
        catch (Exception e) {
            e.printStackTrace();
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
}
