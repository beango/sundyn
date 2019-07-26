package com.sundyn.service;

import com.sundyn.util.EhCacheHelper;
import com.sundyn.util.impl.util;
import com.sundyn.vo.LoginResultEnum;
import com.sundyn.vo.ManagerVo;
import com.xuan.xutils.utils.DateUtils;
import com.xuan.xutils.utils.LocalizedTextUtil;
import com.xuan.xutils.utils.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.jfree.util.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class ManagerService
{
    @Resource
    private JdbcTemplate jdbcTemplate;

    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public Map findManageBy(final String userName, final String pwd, String[] rst) {
        if(userName==null || userName.equals("")) {
            rst[0] = LoginResultEnum.账号不存在.toString();
            return null;
        }
        final String sql = "select *,(select name from appries_dept where id=appries_manager.deptid) deptname from appries_manager where name=? ";

        final Object[] arg = { userName };
        Map m = null;
        try {
            m = jdbcTemplate.queryForMap(sql, arg);
            if (m == null || m.size()==0){
                if (rst!=null && rst.length==1){
                    rst[0] = LoginResultEnum.账号不存在.toString();
                }
                return null;
            }

            if (m.get("checkdigited")!=null && m.get("checkdigited").toString().equals("1")){
                rst[0] = LoginResultEnum.数据被篡改.toString();
                return null;
            }

            String ppwd = getPwdStr(m.get("id"), pwd, m.get("name"), m.get("idcard"), m.get("realname"), m.get("jyno"));
            System.out.println(userName + "密码应该为：" + ppwd);
            String dbpwd = m.get("password").toString();
            if (!dbpwd.equals(ppwd)){
                if (rst!=null && rst.length==1)
                    rst[0] = LoginResultEnum.密码错误.toString();
                return null;
            }
            return m;
        }
        catch (EmptyResultDataAccessException e) {
            rst[0] = LoginResultEnum.账号不存在.toString();
            return null;
        }
        catch (Exception e) {
            HttpServletRequest req = ServletActionContext.getRequest();
            String systemerror = LocalizedTextUtil.findDefaultText("main.systemerror", (Locale)req.getAttribute("Locale"));
            rst[0] = systemerror;
            return null;
        }
    }

    public Map findManageBy(final String userName, String pwd, final String hallno, String[] rst) throws SQLException {
        if(userName==null || userName.equals(""))
            return null;
        String sql = "select *," +
                "(select t3.hallno+','+t3.hallname from FN_GetDeptAndChild(t1.deptid) t2 join sys_queuehall t3 on t2.id=t3.deptid where t3.hallno=?) hall " +
                "from appries_manager t1 where name=? ";
        final Object[] arg = { hallno, userName };
        try {
            Map m = jdbcTemplate.queryForMap(sql, arg);
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
            Map m = jdbcTemplate.queryForMap(sql, arg);
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

    public boolean manageExist(final String id, final String userName) {
        String sql = "select count(*) from appries_manager where name= ? ";
        if (id!=null)
            sql += " and id!=?";
        List<Object> arg = new ArrayList<>();
        arg.add(userName);
        if (id!=null)
            arg.add(id);
        try {
            return jdbcTemplate.queryForObject(sql, arg.toArray(), java.lang.Integer.class) <= 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Map findManageById(final Integer id) {
        final String sql = "select t1.*, t2.name deptname from appries_manager t1 left join appries_dept t2 on t1.deptid=t2.id where t1.id=?";
        try {
            return jdbcTemplate.queryForMap(sql, new Object[]{id});
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map findManageByName(final String name) {
        final String sql = "select id,name,password,realname,userGroupId,remark,ext1,ext2,deptid from appries_manager where name=?";
        try {
            return jdbcTemplate.queryForMap(sql, new Object[]{name});
        }
        catch (Exception e) {
            return null;
        }
    }

    public int AddManager(ManagerVo form) throws SQLException {
        String pwd1 = form.getPassword();
        genCheckDigit(form, null);
        String sql = "set NOCOUNT ON; insert into appries_manager (name,realname,password,skinid,userGroupId,remark,ext1,ext2,deptid,localuser,idcard,jyflag,jyno,uexpired, pwdexpired, " +
                "status, checkdigit, accesstime1, accesstime2, needchangepwd, accessip, userid, ctime, cuser, checkdigited,telphone,contact) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); select ident_current('appries_manager');";
        final Object[] arg = { form.getName(), form.getRealname(), form.getPassword(), form.getSkinid(), form.getUserGroupId(), form.getRemark(), form.getExt1(), form.getExt2(),
                form.getDeptid(), form.getLocaluser(), form.getIdcard(), form.getJyflag(), form.getJyno(), form.getUexpired(), form.getPwdexpried(), form.getStatus(),
                form.getCheckdigit(), form.getAccesstime1(), form.getAccesstime2(), form.getNeedchangepwd(), form.getAccessip(),
                form.getUserid(), form.getCtime(), form.getCuser(), form.getCheckdigited(), form.getTelphone(), form.getContact()};
        try {
            JdbcTemplate jdbc = jdbcTemplate;
            int id = jdbc.queryForObject(sql, arg, Integer.class);
            String ppwd = getPwdStr(String.valueOf(id), form.getPassword(), form.getName(), form.getIdcard(), form.getRealname(), form.getJyno());
            form.setPassword(ppwd);
            genCheckDigit(form, null);
            String usql = "update appries_manager set password=?, checkdigit=?, ext1=? where id=?";
            jdbc.update(usql, new Object[]{form.getPassword(), form.getCheckdigit(), pwd1, id});

            sql = "if not exists(select 1 from appries_managerext where userid=?) " +
                    "insert into appries_managerext(userid, orgname, cuser, ctime) " +
                    "values (?,?,?,?)";
            jdbc.update(sql, new Object[]{id, id, form.getOrgname(),id, new Date()});

            return id;
        }
        catch (Exception e) {
            e.printStackTrace();
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

    private String genCheckDigit(Object userid,  Object password, Object name, Object realname, Object status, Object idcard, Object jyno){
        StringBuilder digitStr = new StringBuilder();
        digitStr.append(userid);
        digitStr.append("|");
        digitStr.append(password);
        digitStr.append("|");
        digitStr.append(name);
        digitStr.append("|");

        digitStr.append(realname);
        digitStr.append("|");

        digitStr.append(status);
        digitStr.append("|");

        digitStr.append(idcard);
        digitStr.append("|");

        digitStr.append(jyno);
        digitStr.append("|");

        return util.md5(digitStr.toString());
    }

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
            Map m = jdbcTemplate.queryForMap(sql, new Object[]{form.getId()});
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
            return jdbcTemplate.update(sql, arg) > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delManage(final Integer id) throws SQLException {
        final String sql = "delete from appries_manager where id =" + id;
        try {
            return jdbcTemplate.update(sql) > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    /*

     */
    public boolean reSetManage(final Integer id, String pwd) {
        String ext1 = pwd;
        Map map = this.findManageById(id);
        if (null!=map){
            String ppwd = getPwdStr(map.get("id"), ext1, map.get("name"), map.get("idcard"), map.get("realname"), map.get("jyno"));
            String checkdigitstr = genCheckDigit(map.get("userid"), ppwd, map.get("name")
                    , map.get("realname"), map.get("status"), map.get("idcard"), map.get("jyno"));
            final String sql = "update appries_manager set ext1=?, password=?, checkdigit=? where id=?";
            try {
                return jdbcTemplate.update(sql, new Object[]{ext1, ppwd, checkdigitstr, id}) > 0;
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
            return jdbcTemplate.update(sql) > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean UpdatePower(final ManagerVo form) throws SQLException {
        final String sql = "update appries_manager set userGroupId=? where id =?";
        final Object[] arg = { form.getUserGroupId(), form.getId() };
        try {
            final int num = jdbcTemplate.update(sql, arg);
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
            String checkdigitstr = genCheckDigit(map.get("userid").toString(), ppwd, map.get("name").toString()
                    , map.get("realname").toString(), map.get("status").toString(), map.get("idcard").toString(), map.get("jyno").toString());
            String sql = "update appries_manager set password=?, ext1=?, checkdigit=? where id =?";
            if (type==1){
                sql = "update appries_manager set password=?, ext1=?, needchangepwd=0, checkdigit=? where id =?";
            }
            final Object[] arg = {ppwd, pass, checkdigitstr, id};
            try {
                return jdbcTemplate.update(sql, arg) >0;
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public List findAll() {
        final String sql = "select * from appries_manager ";
        try {
            return jdbcTemplate.queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Map findByName(final String name) {
        final String sql = "select top 1 * from appries_manager where name =?";
        try {
            return jdbcTemplate.queryForMap(sql,  new Object[] { name });
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
            return jdbcTemplate.queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int countByName(final String name) {
        final String sql = "select count(*) from appries_manager where name like '%" + name + "%' ";
        try {
            return jdbcTemplate.queryForObject(sql,null, java.lang.Integer.class);
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
            rowNum[0] = jdbcTemplate.queryForObject(countSql,null, java.lang.Integer.class);
        }
        sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        logger.info(sql);
        try {
            return jdbcTemplate.queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List findRemoteManagerByName(final String name, final String deptgroup, final int start, final int num, int[] rowNum) {
        String sql = "select row_number() over(order by t1.id desc) as rows, t1.*, t2.name deptname, datediff(d,getdate(), uexpired) uexpiredday, " +
                "datediff(d, getdate(), pwdexpired) pwdexpiredday, t3.orgname " +
                "from appries_manager t1 left join appries_dept t2 on t1.deptid=t2.id " +
                "left join appries_managerext t3 on t1.id=t3.userid " +
                "where t1.localuser=0 ";
        if(StringUtils.isNotBlank(name))
            sql += "and t1.name like '%" + name + "%' ";
        if(StringUtils.isNotBlank(deptgroup))
            sql += "and t1.deptid in(" + deptgroup + ")";
        if (rowNum!=null && rowNum.length==1){
            String countSql = "select count(*) from ("+sql+") t";
            rowNum[0] = jdbcTemplate.queryForObject(countSql,null, java.lang.Integer.class);
        }
        sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
        logger.info(sql);
        try {
            return jdbcTemplate.queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean isUseUserGroupId(final Integer id) {
        final String sql = "select count(*) from appries_managerpower where powerid="+id;
        try {
            return jdbcTemplate.queryForObject(sql,null, java.lang.Integer.class) > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List getuserHost() {
        final String sql = "select ext2 from appries_manager where remark in(2,3)";
        try {
            return jdbcTemplate.queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List getuserMobile() {
        final String sql = "select ext1 from appries_manager where remark in(1,3)";
        try {
            return jdbcTemplate.queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onlineMac(final String mac) {
        final String sql = "call onlinemac(?)";
        try {
            jdbcTemplate.update(sql, new Object[] { mac });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onlineMacDel(final String mac) {
        final String sql = "call onlinemacDel(?)";
        try {
            jdbcTemplate.update(sql, new Object[] { mac });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List onlineMacs(final String mac) {
        final String sql = "select mac from appries_onlinemac";
        try {
            return jdbcTemplate.queryForList(sql);
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
            return jdbcTemplate.queryForMap(sql);
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
            return jdbcTemplate.queryForObject(sql, new Object[]{name, jyno}, Integer.class);
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
            return jdbcTemplate.update(sql, arg)>0;
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
        String sql = "select * from appries_manager where len(password)!=32";

        List maplist = jdbcTemplate.queryForList(sql);
        if (maplist!=null && maplist.size()>0){
            for(Object item : maplist){
                Map m = (Map)item;
                String pwd = getPwdStr(m.get("id"), m.get("ext1"), m.get("name"),m.get("idcard"),m.get("realname"), m.get("jyno"));
                sql= "update appries_manager set password=? where id=?";
                jdbcTemplate.update(sql, new Object[]{pwd, m.get("id").toString()});
            }
        }
    }

    public void updateManagerExt(Integer userID, String password, String realname, String orgname, String contact, String telphone, String email) {
        Map map = this.findManageById(userID);
        if (null!=map) {
            String ppwd = getPwdStr(map.get("id"), map.get("ext1"), map.get("name"), map.get("idcard"), realname, map.get("jyno"));
            String checkdigitstr = genCheckDigit(map.get("userid"), ppwd, map.get("name")
                    , map.get("realname"), map.get("status"), map.get("idcard"), map.get("jyno"));
            String sql = "update appries_manager set realname=?, contact=?, telphone=?, email=?, password=?, checkdigit=? where id=?;" +
                    "if exists(select 1 from appries_managerext where userid=?) " +
                    "update appries_managerext set orgname=? where userid=? " +
                    "else " +
                    "insert into appries_managerext(userid, orgname, cuser, ctime) " +
                    "values (?,?,?,?)";

            int r = jdbcTemplate.update(sql, realname, contact, telphone, email, ppwd, checkdigitstr
                    , userID, userID, orgname, userID, userID, orgname, userID, new Date());
            if(StringUtils.isNotBlank(password))
                reSetManage(userID, util.md5(password));
        }
    }

    public int addOrder(Map orders, List<Map> orderprods) {
        if (orderprods == null || orders == null)
            return -1;
        String sql = "set nocount on; " +
                "insert into order_detail(orderno, managerid, totalfee, status, ispay, comment, cuser, ctime) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?); select ident_current('order_detail');";
        int orderid = jdbcTemplate.queryForObject(sql, new Object[]{orders.get("orderno"), orders.get("managerid"), orders.get("totalfee")
                , orders.get("status"), 0, orders.get("comment"), orders.get("cuser"), new Date()}, Integer.class);

        sql = "insert into order_productdetail(productid, orderid, num, prdprice, rate, realprice) " +
                "values(?, ?, ?, ?, ?, ?);";
        for (Map map : orderprods){
            jdbcTemplate.update(sql, new Object[]{map.get("productid"), orderid, map.get("num"), map.get("prdprice"), map.get("rate"), map.get("realprice")});
        }
        return 0;
    }

    public List getOrders(Integer userID) {
        final String sql = "select * from order_detail where managerid=? order by ctime desc";
        try {
            return jdbcTemplate.queryForList(sql, new Object[]{userID});
        }
        catch (Exception e) {
            return null;
        }
    }

    public List getOrdersProduct(Integer userID) {
        final String sql = "select t1.*, t2.productname from order_productdetail t1 join order_product t2 on t1.productid=t2.id where t1.orderid in(select id from order_detail where managerid=?)";
        try {
            return jdbcTemplate.queryForList(sql, new Object[]{userID});
        }
        catch (Exception e) {
            return null;
        }
    }

    public int cancelOrder(int orderid, String canceldesc, int userID) {
        final String sql = "update order_detail set status=-1, canceldesc=?, canceltime=?, canceluser=? where managerid=? and id=? and status=0";
        try {
            return jdbcTemplate.update(sql, new Object[]{canceldesc, new Date(), userID, userID, orderid});
        }
        catch (Exception e) {
            return -1;
        }
    }

    public int orderClose(int orderid, String closedesc, int userID) {
        final String sql = "update order_detail set status=-2, closedesc=?, closeuser=?, closetime=? where id=? and status=0";
        try {
            return jdbcTemplate.update(sql, new Object[]{closedesc, userID, new Date(),  orderid});
        }
        catch (Exception e) {
            return -1;
        }
    }

    public Object[] orderAudit(int orderid, float payfee, String auditdesc, Integer userID) {
        final String sql = "{call order_audit(?,?,?,?,?)}";
        final Object[] args = { orderid, payfee, auditdesc, userID, new Date() };
        Object[] rest = {true, ""};
        try {
            jdbcTemplate.update(sql, args);
            rest[0] = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            rest[0] = false;
            rest[1] = e.getMessage();
        }
        return rest;
    }

    public List orderlicense(Integer userid, String key, int[] totalrecords, final int start, final int num) {
        String sql = "select row_number() over(order by t1.ctime desc) as rows, t1.id, t1.name, t1.ctime, t1.contact,t2.orgname " +
                ",sum(case when t3.id is not null then 1 else 0 end) ordertotal, sum(case when t3.status=1 then 1 else 0 end) ordercomplete " +
                ",sum(totalfee) ordertotalfee " +
                ",sum(case when t3.status=1 then totalfee else 0 end) ordercompletefee" +
                ",sum(case when t3.status=1 then payfee else 0 end) totalpayfee " +
                ",isnull((select sum(tt1.num) from order_licensedetail tt1 where tt1.type=1 and tt1.managerid=t1.id), 0) ordertotallicense " +
                ",isnull((select count(tt1.num) from order_licensedetail tt1 where tt1.type=2 and tt1.managerid=t1.id), 0) ordertusedlicense " +
                ",isnull((select sum(tt1.usednums) from order_licensedetail tt1 where tt1.type=1 and tt1.managerid=t1.id), 0) usednums " +
                "from appries_manager t1 join appries_managerext t2 on t1.id=t2.userid " +
                "left join order_detail t3 on t1.id=t3.managerid " +
                "where t1.localuser=0 ";
        if (StringUtils.isNotBlank(key))
            sql += "and ((t1.name like '%"+key+"%') or (t2.orgname like '%"+key+"%')) ";
        if(userid!=null)
            sql += "and t1.id=" + userid + " ";
        sql += "group by t1.id, t1.name, t1.ctime, t1.contact,t2.orgname ";

        logger.info(sql);
        ArrayList ary = new ArrayList();

        try {
            totalrecords[0] = jdbcTemplate.queryForObject("select count(*) from (" + sql+ ") t", Integer.class);

            sql = "select * from ("+sql+") t where t.rows>? and t.rows<=?";
            return jdbcTemplate.queryForList(sql, new Object[]{start, num+start});
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List orderlicensedetail(List orderidAy) {
        if (orderidAy == null || orderidAy.size()==0)
            return null;

        String sql = "select t1.*,t2.orderno,t3.productname, (select count(*) from order_licensedetail t4 where t4.type=2 and t4.managerid=t1.managerid and t4.orderid=t1.orderid and t4.productid=t1.productid) usednum " +
                "from order_licensedetail t1 join order_detail t2 on t1.orderid=t2.id " +
                "join order_product t3 on t3.id=t1.productid " +
                "where t1.type=1 and t1.managerid in(";

        String s = "";
        for (Object o: orderidAy)
            s += "," + o;
        if (s != "")
            s = s.substring(1);
        sql += s + ")";

        return jdbcTemplate.queryForList(sql);
    }

    public List prdlicensedetail(List orderidAy) {
        String sql = "select t3.productname, t3.id, t1.managerid, " +
                "sum(case when t1.type=1 then num else 0 end) totalnum, sum(case when t1.type=2 then 1 else 0 end) usednum " +
                "from order_licensedetail t1 " +
                "join order_product t3 on t3.id=t1.productid " +
                "where 1=1 ";

        String s = "";
        if (null!=orderidAy){
            for (Object o: orderidAy)
                s += "," + o;
            if (s != "") {
                s = s.substring(1);
                sql += "and t1.managerid in(" + s + ") ";
            }
        }

        sql += "group by t3.productname,t3.id, t1.managerid";
        return jdbcTemplate.queryForList(sql);
    }

    public List getListOfproductdetail(List orderidAy) {
        if (orderidAy == null || orderidAy.size()==0)
            return null;

        String sql = "select t1.*, t2.productname " +
                "from Order_Productdetail t1 join order_product t2 on t1.productid=t2.id " +
                "where t1.orderid in(";
        String s = "";
        for (Object o: orderidAy)
            s += "," + o;
        if (s != "")
            s = s.substring(1);
        sql += s + ")";

        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> findLstCanLicense(Integer userID, Integer productid) {
        String sql = "select top 1 * from order_licensedetail where managerid=? and productid=? " +
                "and type=1 and num>0 and (usetime is null or usetime<num) order by ctime asc;";
        return jdbcTemplate.queryForMap(sql, new Object[]{userID, productid});
    }

    public Object[] order_license(final Integer userID, final String mac, final Integer productid) {
        final Object[] rest = {true, ""};
        try {
            String param2Value = (String) jdbcTemplate.execute(
                    new CallableStatementCreator() {
                        public CallableStatement createCallableStatement(Connection con) throws SQLException {
                            String storedProc = "{call order_license(?,?,?,?,?,?)}";// 调用的sql
                            CallableStatement cs = con.prepareCall(storedProc);
                            cs.setInt(1, userID);// 设置输入参数的值
                            cs.setInt(2, productid);// 设置输入参数的值
                            cs.setString(3, mac);// 设置输入参数的值
                            cs.setString(4, DateUtils.date2String(new Date()));// 设置输入参数的值
                            cs.registerOutParameter(5, Types.NVARCHAR);// 注册输出参数的类型
                            cs.registerOutParameter(6, Types.INTEGER);// 注册输出参数的类型
                            return cs;
                        }
                    }, new CallableStatementCallback() {
                        public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                            cs.execute();
                            rest[0] = cs.getInt(6) == 1;
                            rest[1] = cs.getString(5);
                            return rest[0].toString() + rest[1].toString();
                        }
                    });
            //rest[0] = true;
            //rest[1] = param2Value;
        }
        catch (Exception e) {
            e.printStackTrace();
            rest[0] = false;
            rest[1] = e.getMessage();
        }
        return rest;
    }

    public List findOrderDetail(Integer status, Integer ispay, Date startDate, Date endDate, int[] totalrecords, int start, int num) {
        String sql = "select row_number() over(order by t1.ctime desc) as rows, t1.*, t2.orgname, t3.name, t4.name auditusername " +
                "from order_detail t1 join appries_managerext t2 on t1.managerid=t2.userid " +
                "join appries_manager t3 on t3.id=t1.managerid " +
                "left join appries_manager t4 on t4.id=t1.audituser ";
        if (null!=status)
            sql += "and t1.status= " + status + " ";
        if(ispay!=null)
            sql += "and t1.ispay=" + ispay + " ";
        if (startDate!=null)
            sql += "and t1.ctime>='"+ DateUtils.date2String(startDate)+"' ";
        if (endDate!=null)
            sql += "and t1.ctime<='"+DateUtils.date2String(endDate)+"' ";

        logger.info(sql);
        ArrayList ary = new ArrayList();

        try {
            totalrecords[0] = jdbcTemplate.queryForObject("select count(*) from (" + sql+ ") t", Integer.class);

            sql = "select * from ("+sql+") t where t.rows>? and t.rows<=?";
            return jdbcTemplate.queryForList(sql, new Object[]{start, num+start});
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findManagerRate(int managerid) {
        String sql = "select t1.*, t2.id product_id, t2.productname, t2.price from order_product t2 left join appries_managerrate t1 on t1.productid=t2.id " +
                "and managerid=? where t2.canbuy=1 ";
        return this.jdbcTemplate.queryForList(sql, new Object[]{managerid});
    }
}
