package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.sundyn.entity.SysDictinfo;
import com.sundyn.util.EhCacheHelper;
import com.sundyn.util.impl.util;
import com.sundyn.vo.ManagerVo;
import com.xuan.xutils.utils.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ManagerService extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public Map findManageBy(final String userName, final String pwd, String[] rst) {
        if(userName==null || userName.equals(""))
            return null;
        final String sql = "select *,(select name from appries_dept where id=appries_manager.deptid) deptname from appries_manager where name= ?";

        final Object[] arg = { userName };
        Map m = null;
        try {
            m = this.getJdbcTemplate().queryForMap(sql, arg);
            if (m == null || m.size()==0){
                if (rst!=null && rst.length==1)
                    rst[0] = "账号不存在";
                return null;
            }

            if (m.get("checkdigited")!=null && m.get("checkdigited").toString().equals("1")){
                rst[0] = "数据被篡改";
                return null;
            }

            String ppwd = getPwdStr(m.get("id"), pwd, m.get("name"), m.get("idcard"), m.get("realname"), m.get("jyno"));
            String dbpwd = m.get("password").toString();
            System.out.println("计算出的密码：" + ppwd + " , 数据库中密码：" + dbpwd);
            if (!dbpwd.equals(ppwd)){
                if (rst!=null && rst.length==1)
                    rst[0] = "密码错误";
                return null;
            }
            return m;
        }
        catch (EmptyResultDataAccessException e) {
            rst[0] = "账号不存在";
            return null;
        }
        catch (Exception e) {
            rst[0] = "系统错误";
            return null;
        }
    }

    public Map findManageBy(final String userName, String pwd, final String hallno, String[] rst) throws SQLException {
        if(userName==null || userName.equals(""))
            return null;
        final String sql = "select *," +
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
            pwd = getPwdStr(m.get("id"), pwd, m.get("name"),m.get("idcard"),m.get("realname"), m.get("jyno"));
            String dbpwd = m.get("password").toString();

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
        final String sql = "select t1.*, t2.name deptname from appries_manager t1 left join appries_dept t2 on t1.deptid=t2.id where t1.id=?";
        try {
            return this.getJdbcTemplate().queryForMap(sql, new Object[]{id});
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map findManageByName(final String name) {
        final String sql = "select id,name,password,realname,userGroupId,remark,ext1,ext2,deptid from appries_manager where name=?";
        try {
            return this.getJdbcTemplate().queryForMap(sql, new Object[]{name});
        }
        catch (Exception e) {
            return null;
        }
    }

    public int AddManager(ManagerVo form) throws SQLException {
        String pwd1 = form.getPassword();
        genCheckDigit(form, null);
        final String sql = "set NOCOUNT ON; insert into appries_manager (name,realname,password,skinid,userGroupId,remark,ext1,ext2,deptid,localuser,idcard,jyflag,jyno,uexpired, pwdexpired, " +
                "status, checkdigit, accesstime1, accesstime2, needchangepwd, accessip, userid, ctime, cuser, checkdigited) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); select ident_current('appries_manager');";
        final Object[] arg = { form.getName(), form.getRealname(), form.getPassword(), form.getSkinid(), form.getUserGroupId(), form.getRemark(), form.getExt1(), form.getExt2(),
                form.getDeptid(), form.getLocaluser(), form.getIdcard(), form.getJyflag(), form.getJyno(), form.getUexpired(), form.getPwdexpried(), form.getStatus(),
                form.getCheckdigit(), form.getAccesstime1(), form.getAccesstime2(), form.getNeedchangepwd(), form.getAccessip(),
                form.getUserid(), form.getCtime(), form.getCuser(), form.getCheckdigited()};
        try {
            JdbcTemplate jdbc = this.getJdbcTemplate();
            int id = jdbc.queryForObject(sql, arg, Integer.class);
            String ppwd = getPwdStr(String.valueOf(id), form.getPassword(), form.getName(), form.getIdcard(), form.getRealname(), form.getJyno());
            form.setPassword(ppwd);
            genCheckDigit(form, null);
            String usql = "update appries_manager set password=?, checkdigit=?, ext1=? where id=?";
            jdbc.update(usql, new Object[]{form.getPassword(), form.getCheckdigit(), pwd1, id});
            return id;
        }
        catch (Exception e) {
            return -1;
        }
    }

    public String getPwdStr(Object userid, Object pwd, Object name, Object idcard, Object realname, Object jyno){
        if (userid==null)
            return null;
        if (pwd==null)
            return null;
        if (name == null)
            return null;
        if (idcard==null)
            idcard = "";
        if (realname==null)
            realname = "";
        if (jyno == null)
            jyno = "";
        System.out.println( "计算串：" + userid.toString().trim() + pwd.toString().trim() + name.toString().trim() + idcard.toString().trim() + jyno.toString().trim() + realname.toString().trim());
        String rst =  util.md5(userid.toString().trim() + pwd.toString().trim() + name.toString().trim() + idcard.toString().trim() + jyno.toString().trim() + realname.toString().trim());
        return rst;
    }


    /*public String getUIDStr(String userid, String pwd, String name, String idcard, String realname, String jyno){
        if (StringUtils.isNullOrEmpty(userid))
            return null;
        if (StringUtils.isNullOrEmpty(pwd))
            return null;
        if (StringUtils.isNullOrEmpty(name))
            return null;
        if (StringUtils.isNullOrEmpty(idcard))
            idcard = "";
        if (StringUtils.isNullOrEmpty(realname))
            idcard = "";
        if (StringUtils.isNullOrEmpty(jyno))
            idcard = "";
        String rst =  util.md5(userid.trim() + pwd.trim() + name.trim() + idcard.trim() + jyno.trim() + realname.trim());
        return rst;
    }*/


    private void genCheckDigit(ManagerVo model, String password){
        StringBuilder digitStr = new StringBuilder();
        digitStr.append(model.getUserid());
        digitStr.append("|");

        if (StringUtils.isBlank(model.getPassword()))
            digitStr.append(password);
        else
            digitStr.append(model.getPassword());
        digitStr.append("|");

        digitStr.append(model.getName());
        digitStr.append("|");

        digitStr.append(model.getRealname());
        digitStr.append("|");

        digitStr.append(model.getStatus());
        digitStr.append("|");

        digitStr.append(model.getIdcard());
        digitStr.append("|");

        digitStr.append(model.getJyno());
        digitStr.append("|");

        model.setCheckdigit(util.md5(digitStr.toString()));
    }

    public boolean UpdateManage(ManagerVo form) {
        String pwdorg = form.getPassword();
        if (StringUtils.isBlank(form.getPassword())){
            String sql = "select ext1 from appries_manager where id=?";
            Map m = getJdbcTemplate().queryForMap(sql, new Object[]{form.getId()});
            if (m == null)
                return false;
            pwdorg = m.get("ext1").toString();
        }

        String ppwd = getPwdStr(String.valueOf(form.getId()), pwdorg, form.getName(), form.getIdcard(), form.getRealname(), form.getJyno());
        form.setPassword(ppwd);

        genCheckDigit(form, ppwd);
        String sql = "update appries_manager set  name=?,realname=?,skinid=?,userGroupId=?,remark=?,deptid=?,ext2=?,localuser=?,idcard=?,jyflag=?," +
                "jyno=?,status=?,uexpired=?,pwdexpired=?,checkdigit=?,accesstime1=?,accesstime2=?, needchangepwd=?, accessip=?, checkdigited=?,ext1=? ";
        if (StringUtils.isNotBlank(form.getPassword()))
            sql += ",password='"+form.getPassword()+"' ";
        sql += "where id =?";
        Object[] arg = { form.getName(), form.getRealname(), form.getSkinid(), form.getUserGroupId(), form.getRemark(), form.getDeptid(),
                form.getExt2(), form.getLocaluser(), form.getIdcard(), form.getJyflag(), form.getJyno(), form.getStatus(),
                form.getUexpired(), form.getPwdexpried(), form.getCheckdigit(), form.getAccesstime1(), form.getAccesstime2(),
                form.getNeedchangepwd(), form.getAccessip(), form.getCheckdigited(),pwdorg, form.getId() };
        try {
            return this.getJdbcTemplate().update(sql, arg) > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delManage(final Integer id) throws SQLException {
        final String sql = "delete from appries_manager where id =" + id;
        try {
            return this.getJdbcTemplate().update(sql) > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    //String userid, String pwd, String name, String idcard, String realname, String jyno
    public boolean reSetManage(final Integer id) {
        String ext1 = util.md5("123456");
        Map map = this.findManageById(id);
        if (null!=map){
            String ppwd = getPwdStr(map.get("id"), ext1, map.get("name"), map.get("idcard"), map.get("realname"), map.get("jyno"));
            final String sql = "update  appries_manager set ext1=?, password=?  where id=?";
            try {
                return this.getJdbcTemplate().update(sql, new Object[]{ext1, ppwd, id}) > 0;
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean reSetManageStatus(final Integer id) throws SQLException {
        final String sql = "update appries_manager set status=(case when status=1 or status is null then 0 else 1 end) where id =" + id;
        try {
            return this.getJdbcTemplate().update(sql) > 0;
        }
        catch (Exception e) {
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
            return false;
        }
    }

    public boolean UpdateManage(final Integer id, final String pass, int type) {
        Map map = this.findManageById(id);
        if (null!=map){
            String ppwd = getPwdStr(map.get("id"), pass, map.get("name"), map.get("idcard"), map.get("realname"), map.get("jyno"));

            String sql = "update appries_manager set password=?, ext1=? where id =?";
            if (type==1){
                sql = "update appries_manager set password=?, ext1=?, needchangepwd=0 where id =?";
            }
            final Object[] arg = {ppwd, pass, id};
            try {
                final int num = this.getJdbcTemplate().update(sql, arg);
                return num > 0;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
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
        final String sql = "select top 1 * from appries_manager where name =?";
        try {
            return this.getJdbcTemplate().queryForMap(sql,  new Object[] { name });
        }
        catch (Exception e) {
            return null;
        }
    }

    public Map findByNameCache(final String name) {
        Map managermap = null;
        List allDataManager = getAllCache();
        for(Object m : allDataManager){
            Map map = (Map)m;
            if (map.get("name").toString().equalsIgnoreCase(name))
                managermap = map;
        }
        return managermap;
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
        String sql = "select row_number() over(order by t1.id desc) as rows, t1.*, t2.name deptname, datediff(d,getdate(), uexpired) uexpiredday, " +
                "datediff(d, getdate(), pwdexpired) pwdexpiredday " +
                "from appries_manager t1 left join appries_dept t2 on t1.deptid=t2.id " +
                "where 1=1 ";
        if(StringUtils.isNotBlank(name))
            sql += "and t1.name like '%" + name + "%' ";
        if(StringUtils.isNotBlank(deptgroup))
            sql += "and t1.deptid in(" + deptgroup + ")";
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
            return null;
        }
    }

    public Map findByIdCard(String idcard) {
        if (StringUtils.isBlank(idcard))
            return null;
        final String sql = "select * from appries_manager where idcard='" + idcard + "'";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Integer existsByjyno(String name, String jyno) {
        if (StringUtils.isBlank(jyno))
            return null;
        final String sql = "select count(*) from appries_manager where name!=? and jyno=?";
        try {
            return this.getJdbcTemplate().queryForObject(sql, new Object[]{name, jyno}, Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateLoginTime(String name, Date logintime, String loginadd, String logindevice) {
        if (StringUtils.isBlank(name))
            return false;
        final Object[] arg = { loginadd, logintime, logindevice, name };
        String sql = "update appries_manager set loginadd=?,logintime=?, logindevice=? where name=?";
        try {
            return this.getJdbcTemplate().update(sql, arg)>0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List getAllCache(){
        return getAllCache(false);
    }

    public List getAllCache(boolean isClear){
        Object allData = EhCacheHelper.getCache(EhCacheHelper.CacheKeyEnum.ALLMANAGER);
        if (null == allData || isClear){
            System.out.println("ALLMANAGER 缓存为空，获取并添加缓存,是否清空：" + isClear);
            allData = findAll();
            EhCacheHelper.putCache(EhCacheHelper.CacheKeyEnum.ALLMANAGER, allData);
        }
        return (List)allData;
    }

    public void initPwdForCheck(){
        System.out.println("管理员表初始化密码");
        String sql = "select * from appries_manager where len(password)!=32";

        List maplist = this.getJdbcTemplate().queryForList(sql);
        if (maplist!=null && maplist.size()>0){
            for(Object item : maplist){
                Map m = (Map)item;
                String pwd = getPwdStr(m.get("id"), m.get("ext1"), m.get("name"),m.get("idcard"),m.get("realname"), m.get("jyno"));
                System.out.println(m.get("name").toString() + "算得密码：" + pwd);
                sql= "update appries_manager set password=? where id=?";
                this.getJdbcTemplate().update(sql, new Object[]{pwd, m.get("id").toString()});
            }
        }
    }
}
