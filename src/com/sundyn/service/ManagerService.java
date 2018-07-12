package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.sundyn.vo.ManagerVo;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerService extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public Map findManageBy(final String userName, final String pwd) throws SQLException {
        if(userName==null || userName.equals(""))
            return null;
        final String sql = "select * from appries_manager where name= ? and password= ?";

        final Object[] arg = { userName, pwd };
        try {
            return this.getJdbcTemplate().queryForMap(sql, arg);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean manageExist(final String id, final String userName) throws SQLException {
        String sql = "select count(*) from appries_manager where name= ? ";
        if (id!=null)
            sql += " and id!=?";
        List<Object> arg = new ArrayList<>();
        arg.add(userName);
        if (id!=null)
            arg.add(id);
        try {
            final int num = this.getJdbcTemplate().queryForInt(sql, arg.toArray());
            return num <= 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Map findManageById(final Integer id) {
        final String sql = "select id,name,password,realname,userGroupId,remark,ext1,ext2 from appries_manager where id=" + id;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean AddManager(final ManagerVo form) throws SQLException {
        final String sql = "insert into appries_manager (name,realname,password,skinid,userGroupId,remark,ext1,ext2) values(?,?,?,?,?,?,?,?)";
        final Object[] arg = { form.getName(), form.getRealname(), form.getPassword(), form.getSkinid(), form.getUserGroupId(), form.getRemark(), form.getExt1(), form.getExt2() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean UpdateManage(final ManagerVo form) throws SQLException {
        final String sql = "update appries_manager set  name=?,realname=?,skinid=?,userGroupId=?,remark=?,ext1=?,ext2=? where id =?";
        final Object[] arg = { form.getName(), form.getRealname(), form.getSkinid(), form.getUserGroupId(), form.getRemark(), form.getExt1(), form.getExt2(), form.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean delManage(final Integer id) throws SQLException {
        final String sql = "delete from appries_manager where id =" + id;
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reSetManage(final Integer id) throws SQLException {
        final String sql = "update  appries_manager set password='123456'  where id =" + id;
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdatePower(final ManagerVo form) throws SQLException {
        final String sql = "update appries_manager set userGroupId=? where id =?";
        final Object[] arg = { form.getUserGroupId(), form.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean UpdateManage(final Integer id, final String pass) throws SQLException {
        final String sql = "update appries_manager set password=? where id =?";
        final Object[] arg = { pass, id };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List findAll() {
        final String sql = "select * from appries_manager ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List findByName(final String name, final int start, final int num) {
        final String sql = "select * from appries_manager where name like '%" + name + "%' order by id desc limit " + start + "," + num;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countByName(final String name) {
        final String sql = "select count(*) from appries_manager where name like '%" + name + "%' ";
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List findLowerManagerByName(final String name, final String deptgroup, final int start, final int num) {
        String sql = "select row_number() over(order by id desc) as rows, * from appries_manager where name like '%" + name + "%' " +
                "and userGroupId in(" +
                "select id from appries_power " +
                "where deptIdGroup in (" + deptgroup + "))";
        sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        logger.debug("findLowerManagerByName.sql=" + sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countLowerManagerByName(final String name, final String deptgroup) {
        final String sql = "select count(*) from appries_manager where  name like '%" + name + "%' and userGroupId in (select id from appries_power where deptIdGroup in (" + deptgroup + "))";
        System.out.println("sql1=" + sql);
        try {
            return this.getJdbcTemplate().queryForInt(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean isUseUserGroupId(final Integer id) {
        final String sql = "select count(*) from appries_manager where userGroupId=" + id;
        try {
            final int num = this.getJdbcTemplate().queryForInt(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List getuserHost() {
        final String sql = "select ext2 from appries_manager where remark in(2,3)";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List getuserMobile() {
        final String sql = "select ext1 from appries_manager where remark in(1,3)";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onlineMac(final String mac) {
        final String sql = "call onlinemac(?)";
        try {
            this.getJdbcTemplate().update(sql, new Object[] { mac });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onlineMacDel(final String mac) {
        final String sql = "call onlinemacDel(?)";
        try {
            this.getJdbcTemplate().update(sql, new Object[] { mac });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List onlineMacs(final String mac) {
        final String sql = "select mac from appries_onlinemac";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
