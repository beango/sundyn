package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.sundyn.vo.PowerVo;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PowerService extends SuperDao
{
    private static Logger logger = Logger.getLogger("PowerService");

    public Map getUserGroup(final Integer id) {
        final String sql = "select * from appries_power where id=" + id;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int getDeptIdByManagerId(final int id) throws Exception {
        final String sql = "SELECT deptIdGroup  FROM appries_power where id in(select userGroupId from appries_manager where id= " + id + " )";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public List getLowerManagerByManagerId(final String ids) {
        final String sql = "SELECT * FROM appries_manager where userGroupId in (select id from appries_power where deptIdGroup in\t( " + ids + "))";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List getLowerPowerByDeptId(final String ids) {
        final String sql = "select * from appries_power where deptIdGroup in\t( " + ids + ")";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean addUserGroup(final PowerVo s) throws SQLException {
        final String sql = "insert into appries_power (name, baseSet ,dataManage, deptidGroup) values (?,?,?,? )";
        final Object[] arg = { s.getName(), s.getBaseSet(), s.getDataManage(), s.getDeptIdGroup() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(final PowerVo s) {
        final String sql = "update  appries_power set   name=?, baseSet=? ,dataManage=?, deptidGroup=? where id =?";
        final Object[] arg = { s.getName(), s.getBaseSet(), s.getDataManage(), s.getDeptIdGroup(), s.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delUserGroup(final Integer id) throws SQLException {
        final String sql = "delete from appries_power where id=" + id;
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List getUserGroupList() throws SQLException {
        final String sql = "select * from appries_power";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findByName(final String name, final int start, final int num) {
        final String sql = "select * from appries_power where name like '%" + name + "%' order by id desc limit " + start + "," + num + " ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int countByName(final String name) {
        final String sql = "select count(*) from appries_power where name  like '%" + name + "%'  ";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public List findLowerPowerByName(final String name, final String deptgroup, final int start, final int num) {
        String sql = "select row_number() over(order by t1.id desc) as rows, t1.*,t2.name as deptname from appries_power t1 join appries_dept t2 on t1.deptidgroup = t2.id where t1.name like '%" + name + "%' " +
                "and t1.deptIdGroup in(" + deptgroup + ")";
        sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        logger.debug("sql-findLowerPowerByName=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int countLowerPowerByName(final String name, final String deptgroup) {
        final String sql = "select count(*) from appries_power where  name like '%" + name + "%' and deptIdGroup in (" + deptgroup + ")";
        System.out.println("sql-countLowerPowerByName=" + sql);
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public boolean powerExist(final String id, final String name) {
        String sql = "select count(*) from appries_power where name ='" + name + "' ";
        if (id!=null)
            sql += "and id!=" + id;
        try {
            final int num = this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
