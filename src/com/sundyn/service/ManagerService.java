package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.sundyn.vo.ManagerVo;
import com.xuan.xutils.utils.StringUtils;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerService extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public Map findManageBy(final String userName, final String pwd, String[] rst) throws SQLException {
        if(userName==null || userName.equals(""))
            return null;
        final String sql = "select *,(select name from appries_dept where id=appries_manager.deptid) deptname from appries_manager where name= ?";

        final Object[] arg = { userName };
        try {
            Map m = this.getJdbcTemplate().queryForMap(sql, arg);
            if (m == null || m.size()==0){
                if (rst!=null && rst.length==1)
                    rst[0] = "账号不存在";
                return null;
            }

            String dbpwd = m.get("password").toString();
            if (!dbpwd.equals(pwd)){
                if (rst!=null && rst.length==1)
                    rst[0] = "密码错误";
                return null;
            }
            return m;
        }
        catch (Exception e) {
            e.printStackTrace();
            rst[0] = "系统错误";
            return null;
        }
    }

    public Map findManageBy(final String userName, final String pwd, final String hallno, String[] rst) throws SQLException {
        if(userName==null || userName.equals(""))
            return null;
        final String sql = "select *, CONVERT(VARCHAR(32), HashBytes('MD5', CONVERT(VARCHAR(50),password)), 2) passwordmd5, " +
                "(select t3.hallno+','+t3.hallname from FN_GetDeptAndChild(t1.deptid) t2 join sys_queuehall t3 on t2.id=t3.deptid where t3.hallno=?) hall " +
                "from appries_manager t1 where name= ?";

        final Object[] arg = { hallno, userName };
        try {
            Map m = this.getJdbcTemplate().queryForMap(sql, arg);
            if (m == null || m.size()==0){
                if (rst!=null && rst.length==1)
                    rst[0] = "账号不存在";
                return null;
            }

            String dbpwd = m.get("passwordmd5").toString();
            if (!dbpwd.equalsIgnoreCase(pwd)){
                if (rst!=null && rst.length==1)
                    rst[0] = "密码错误";
                return null;
          }

            Object hall = m.get("hall");
            if (StringUtils.isNotBlank(hallno) && null == hall) {
                rst[0] = "用户不属于该营业厅";
                return null;
            }
          return m;
        }
        catch (Exception e) {
            e.printStackTrace();
            rst[0] = "系统错误";
            return null;
        }
    }

    public Map managerUnLogin(final String userName, final String hallno, String[] rst) throws SQLException {
        if(userName==null || userName.equals(""))
            return null;
        final String sql = "select *, CONVERT(VARCHAR(32), HashBytes('MD5', password), 2) passwordmd5, " +
                "(select t3.hallno+','+t3.hallname from FN_GetDeptAndChild(t1.deptid) t2 join sys_queuehall t3 on t2.id=t3.deptid where t3.hallno=?) hall " +
                "from appries_manager t1 where name= ?";

        final Object[] arg = { hallno, userName };
        try {
            Map m = this.getJdbcTemplate().queryForMap(sql, arg);
            if (m == null || m.size()==0){
                if (rst!=null && rst.length==1)
                    rst[0] = "账号不存在";
                return null;
            }

            Object hall = m.get("hall");
            if (StringUtils.isNotBlank(hallno) && null == hall) {
                rst[0] = "用户不属于该营业厅";
                return null;
            }
            return m;
        }
        catch (Exception e) {
            e.printStackTrace();
            rst[0] = "系统错误";
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
            final int num = this.getJdbcTemplate().queryForObject(sql, arg.toArray(), java.lang.Integer.class);
            return num <= 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Map findManageById(final Integer id) {
        final String sql = "select id,name,password,realname,userGroupId,remark,ext1,ext2,deptid,localuser from appries_manager where id=" + id;
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map findManageByName(final String name) {
        final String sql = "select id,name,password,realname,userGroupId,remark,ext1,ext2,deptid from appries_manager where name='" + name + "'";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean AddManager(final ManagerVo form) throws SQLException {
        final String sql = "insert into appries_manager (name,realname,password,skinid,userGroupId,remark,ext1,ext2,deptid,localuser) values(?,?,?,?,?,?,?,?,?,?)";
        final Object[] arg = { form.getName(), form.getRealname(), form.getPassword(), form.getSkinid(), form.getUserGroupId(), form.getRemark(), form.getExt1(), form.getExt2(), form.getDeptid(), form.getLocaluser() };
        try {
            return this.getJdbcTemplate().update(sql, arg) > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean UpdateManage(final ManagerVo form) throws SQLException {
        final String sql = "update appries_manager set  name=?,realname=?,skinid=?,userGroupId=?,remark=?,deptid=?,ext1=?,ext2=?,localuser=? where id =?";
        final Object[] arg = { form.getName(), form.getRealname(), form.getSkinid(), form.getUserGroupId(), form.getRemark(), form.getDeptid(), form.getExt1(), form.getExt2(), form.getLocaluser(), form.getId() };
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

    public Map findByName(final String name) {
        final String sql = "select top 1 * from appries_manager where name ='" + name + "'";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
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
            return null;
        }
    }

    public int countByName(final String name) {
        final String sql = "select count(*) from appries_manager where name like '%" + name + "%' ";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public List findLowerManagerByName(final String name, final String deptgroup, final int start, final int num, int[] rowNum) {
        String sql = "select row_number() over(order by id desc) as rows, * from appries_manager where 1=1 ";
        if(StringUtils.isNotBlank(name))
            sql += "and name like '%" + name + "%' ";
        if(StringUtils.isNotBlank(deptgroup))
            sql += "and deptid in(" + deptgroup + ")";
        if (rowNum!=null && rowNum.length==1){
            String countSql = "select count(*) from ("+sql+") t";
            rowNum[0] = this.getJdbcTemplate().queryForObject(countSql,null, java.lang.Integer.class);
        }
        sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        logger.debug(sql);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean isUseUserGroupId(final Integer id) {
        final String sql = "select count(*) from appries_managerpower where powerid="+id;
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class) > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List getuserHost() {
        final String sql = "select ext2 from appries_manager where remark in(2,3)";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
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
