package com.sundyn.service;

import com.sundyn.cache.Cache;
import com.sundyn.cache.CacheManager;
import com.sundyn.dao.SuperDao;
import com.sundyn.utils.StringUtils;
import com.sundyn.vo.DeptVo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeptService extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();
    private ArrayList temp;

    public DeptService() {
        this.temp = new ArrayList();
    }

    public ArrayList getTemp() {
        return this.temp;
    }

    public void setTemp(final ArrayList temp) {
        this.temp = temp;
    }

    public void ClearCache(){
        for (String item : CacheManager.getCacheAllkey()){
            if (item.startsWith(CacheManager.DeptService_findchild)){
                CacheManager.clearOnly(item);
            }
        }
    }
    public void updateUseVideo(final String ids, final String video) {
        final String sql = "update appries_dept set useVideo ='" + video + "' where id in(" + ids + ")";
        try {
            this.getJdbcTemplate().update(sql);
            ClearCache();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map findDeptById(final Integer id) {
        final String sql = "select *,(select count(*) from appries_dept t1 where t1.fatherid=t2.id) as childs from appries_dept t2 where id=?";
        final Object[] arg = { id };
        logger.debug("findDeptById: " + sql + id);
        try {
            return this.getJdbcTemplate().queryForMap(sql, arg);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void hasAdnAddDeptByMac(final String mac) {
        String sql = "select count(*) c from appries_dept t2 where remark=? and deptType=0";
        Object[] arg = { mac };
        try {
            boolean has = this.getJdbcTemplate().queryForObject(sql, arg, java.lang.Integer.class)>0;
            if  (!has){
                sql = "Insert into appries_dept (name, fatherId, remark, child,lenvel,client_type,product_type,deptType,dept_camera_url,dept_businessId," +
                        "dept_playListId,ext1,ext2,ext3,useVideo,notice,cityid,provinceid,ext5) " +
                        "values(?,isnull((select top 1 id from appries_dept where depttype=1),(select top 1 id from appries_dept)),?,?,?,?,?,?,?,?,?,?,?,?,?,?,(SELECT _id FROM [citys] where name='广州'),(SELECT _id FROM provinces where name='广东'),getdate())";
                arg = new Object[]{mac, mac, "0", "3", "0", "", "0", "", "-1", "1", "", "", "", "", ""};
                this.getJdbcTemplate().update(sql, arg);
                ClearCache();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map findDeptByFatherid(final int id) {
        final String sql = "select * from appries_dept where id=?";
        final Object[] arg = { id };
        try {
            return this.getJdbcTemplate().queryForMap(sql, arg);
        }
        catch (Exception e) {
            return null;
        }
    }

    public Map findDeptByName(final String name) {
        final String sql = "select * from appries_dept where name=?";
        final Object[] arg = { name };
        try {
            return this.getJdbcTemplate().queryForMap(sql, arg);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map findDeptByMac(final String mac) {
        final String sql = "select id,name,lenvel,child,remark,fatherid,client_type,deptType,dept_camera_url ,dept_businessId,dept_playListId,ext1,ext2,ext3 from appries_dept where remark=?";
        final Object[] arg = { mac };
        try {
            return this.getJdbcTemplate().queryForMap(sql, arg);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean addDept(final DeptVo dept, final int fartherId) {
        final String sql = "Insert into appries_dept (name, fatherId, remark, child,lenvel,client_type,product_type,deptType,dept_camera_url,dept_businessId,dept_playListId,ext1,ext2,ext3,useVideo,notice,cityid,provinceid,ext5) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final Object[] arg = { dept.getName()==null?"":dept.getName(), dept.getFatherId(), dept.getRemark()==null?"":dept.getRemark(), new Integer(0),
                dept.getLenvel(), dept.getClient_type(), dept.getProduct_Type()==null?"":dept.getProduct_Type(), dept.getDeptType(),
                dept.getDept_camera_url()==null?"":dept.getDept_camera_url(), dept.getDept_businessId(), dept.getDept_playListId(),
                dept.getDeptPause()==null?"":dept.getDeptPause(), dept.getDeptPic()==null?"":dept.getDeptPic(),
                dept.getDeptLogoPic()==null?"":dept.getDeptLogoPic(), dept.getUseVideo()==null?"":dept.getUseVideo(),
                dept.getNotice()==null?"":dept.getNotice(), dept.getCityid(), dept.getProvinceid(), dept.getExt5()==null?"":dept.getExt5() };
        final String sql2 = "update appries_dept set child = child+1 where id = ?";
        final Object[] arg2 = { dept.getFatherId() };
        try {
            this.getJdbcTemplate().update(sql, arg);
            this.getJdbcTemplate().update(sql2, arg2);
            ClearCache();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDept(final DeptVo dept) {
        final String sql = "Update appries_dept set name=?,remark=?,client_type=?,product_type=?,dept_camera_url=?,dept_businessId=? ,dept_playListId=?,ext1=?,ext2=?,ext3=?,useVideo=?,notice=?   where id=?";
        final Object[] arg = { dept.getName(), dept.getRemark(), dept.getClient_type(), dept.getProduct_Type(), dept.getDept_camera_url(), dept.getDept_businessId(), dept.getDept_playListId(), dept.getDeptPause(), dept.getDeptPic(), dept.getDeptLogoPic(), dept.getUseVideo(), dept.getNotice(), dept.getId() };
        try {
            final int num = this.getJdbcTemplate().update(sql, arg);
            ClearCache();
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDeptNotice(final String deptIds, final String notice) {
        final String sql = "UPDATE `appries_dept` SET notice='" + notice + "' where id IN (" + deptIds + ")";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            ClearCache();
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDeptPlayList(final String ids, final String dept_playListId) {
        final String sql = "Update appries_dept set dept_playListId= " + dept_playListId + "  where id in (" + ids + ")";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            ClearCache();
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDept(final Integer id) throws SQLException {
        final Map dept = this.findDeptById(id);
        final String fatherId = dept.get("fatherId").toString();
        String ids = this.findChildALLStr123(new StringBuilder().append(id).toString());
        if ("".equals(ids)) {
            ids = new StringBuilder().append(id).toString();
        }
        try {
            final String sql1 = "delete from appries_employee where deptid in (" + ids + ")";
            final String sql2 = "delete from appries_appries where deptid in (" + ids + ")";
            final String sql3 = "update appries_dept set child = child - 1 where id  ='" + fatherId + "'";
            final String sql4 = "delete from appries_dept where id in (" + ids + ")";
            this.getJdbcTemplate().update(sql1);
            this.getJdbcTemplate().update(sql2);
            this.getJdbcTemplate().update(sql3);
            this.getJdbcTemplate().update(sql4);
            ClearCache();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List findAll(final String ids) {
        String sql = "select *,(select count(*) from appries_dept t1 where t1.fatherid=t2.id) as childs from appries_dept t2 where 1=1 ";
        if(null!=ids && !ids.equals(""))
            sql += "and id in (" + ids + ")";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findAllForSearch(final String keyword, final int start, final int num) throws SQLException {
        final String sql = "select * from appries_dept where child=0 limit " + start + "," + num;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findAllNo(final String cc) throws SQLException {
        final String sql = "select * from appries_dept where child>0";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findAllForSearchByName(final String name, final int start, final int num) throws SQLException {
        final String sql = "select * from appries_dept where child=0 and name like '%" + name + "%' limit " + start + "," + num;
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findchild(final Integer id) {
        return this.findchild(id, true);
    }

    public List findchild(final Integer id, boolean isCountChild) {
        String Key = CacheManager.DeptService_findchild + id + "_" + isCountChild;

        Cache data = CacheManager.getCacheInfo(Key);
        if(data!=null){
            return (List)data.getValue();
        }
        String sql = "select * ";
        if(isCountChild)
            sql += ",(select count(*) from appries_dept t1 where t1.fatherid=t2.id) as childs ";
        sql += "from appries_dept t2 where fatherId =" + id;
        try {
            List l = this.getJdbcTemplate().queryForList(sql);
            Cache c = new Cache();
            c.setValue(l);
            CacheManager.AddKey(Key);
            CacheManager.putCache(Key, c);
            return l;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findchild(final Integer id, final Integer deptType) {
        final String sql = "select * from appries_dept where fatherId = ? and deptType=? ";
        final Object[] args = { id, deptType };
        try {
            return this.getJdbcTemplate().queryForList(sql, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String findchildstr(final Integer id) throws SQLException {
        final List temp = this.findChildALL(id, 999);
        String rtemp = "";
        if (temp == null || temp.size() == 0) {
            return "";
        }
        for (int i = 0; i < temp.size(); ++i) {
        	Map map = (Map)temp.get(i);
        	rtemp = String.valueOf(rtemp) + map.get("id").toString();
            //rtemp = String.valueOf(rtemp) + temp.get(i).get("id").toString();
        }
        return rtemp;
    }

    public List findChildALL(final Integer id, int deep) throws SQLException {
        deep -= 1;
        final List tem = this.findchild(id,deep>0);
        if (tem != null && tem.size() > 0) {
            this.temp.addAll(tem);
            for (final Object o : tem) {
                final Map m = (Map)o;
                final Integer idtemp = Integer.valueOf(m.get("id").toString());
                if(deep>0)
                    this.findChildALL(idtemp, deep);
            }
        }
        return this.temp;
    }

    public List findAllWindow() throws SQLException {
        final String sql = "select * from appries_dept where deptType=0 limit 10 ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List findChildALL(final String ids) throws SQLException {
        return this.findChildALL(ids,999);
    }
    public List findChildALL(final String ids, int deep) throws SQLException {
        if(ids == null)
            return null;
        final List temp = new ArrayList();
        List idAll = findAll(ids);
        if(null!=idAll)
            temp.addAll(idAll);
        final String[] idarray = ids.split(",");
        for (int i = 0; i < idarray.length; ++i) {
            if(idarray[i].equals(""))
                continue;
            temp.addAll(this.findChildALL(Integer.valueOf(idarray[i]),deep-1));
            this.setTemp(new ArrayList());
        }
        return temp;
    }

    public String findChildALLStr123(final String ids) throws SQLException {
        final List temp = this.findChildALL(ids);
        if(temp == null)
            return null;
        String resl = "";
        for (int i = 0; i < temp.size(); ++i) {
            final Map m = (Map) temp.get(i);
            if (m != null) {
                final Object obj = m.get("id");
                if (obj != null) {
                    final String id = obj.toString();
                    resl = String.valueOf(resl) + id + ",";
                }
            }
        }
        if (resl.endsWith(",")) {
            resl = resl.substring(0, resl.length() - 1);
        }
        return resl;
    }

    public List findHisNoChild(final DeptVo fo) throws SQLException {
        final String sql1 = "select id,child,lenvel,fatherId,name from appries_dept  where child>0 and fatherId =" + fo.getId();
        try {
            return this.getJdbcTemplate().queryForList(sql1);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List toTree(final List list, final int id, final String name) {
        return null;
    }

    public int getPaiHangInfo(final Integer id) throws SQLException {
        return this.findChildALL(id, 999).size();
    }

    public List getPaiHangInfo(final String id, final String starDate, final String endDate, final int startRow, final int endRow, final Map sortValueMap) {
        return null;
    }

    public List getPaiHangInfoByEmployee(final String id, final String starDate, final String endDate, final int startRow, final int endRow, final Map sortValueMap) {
        return null;
    }

    public int getEmployees(final String id) {
        final String sql = "select count(*) from appries_employee where deptid in (" + id + ")";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public int getDetpLevel() {
        final String sql = "select max(lenvel) as maxvalue from appries_dept";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public boolean isLastestTwo(final Integer deptId) throws Exception {
        final List childList = this.findchild(deptId);
        if (childList == null || childList.size() == 0) {
            return false;
        }
        final Integer num = childList.size() + 1;
        final Integer num2 = this.findChildALL(new StringBuilder().append(deptId).toString()).size();
        return num.equals(num2);
    }

    public boolean isLeafage(final Integer id) throws Exception {
        final String sql = "select count(*) from appries_dept  where  fatherId =" + id;
        try {
            final int num = this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
            return num <= 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public List findDeptByType(final String ids, final Integer DeptTypeId) {
        final String sql = "select * from appries_dept where id in(" + ids + ") and deptType=" + DeptTypeId;
        try {
            System.out.println(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDanweiName() {
        final String sql = "select name from appries_dept where fatherId=-1";
        try {
            return this.getJdbcTemplate().queryForObject(sql, (Class)String.class).toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDeptByMac(final String mac) {
        final String sql = "select dept_camera_url from appries_dept where remark=?";
        final Object[] args = { mac };
        try {
            return this.getJdbcTemplate().queryForObject(sql, args, (Class)String.class).toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    public Map findByMac(final String mac) {
        final String sql = "select * from appries_dept where remark=?";
        final Map tempMap = null;
        final Object[] args = { mac };
        try {
            return this.getJdbcTemplate().queryForMap(sql, args);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int countByPlayListId(final String playListId) {
        final String sql = "select count(*) from appries_dept where dept_playListId=?  ";
        final Object[] args = { playListId };
        try {
            return this.getJdbcTemplate().queryForObject(sql, args, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getMaxId() {
        final String sql = "select max(id) from appries_dept   ";
        try {
            return this.getJdbcTemplate().queryForObject(sql,null, java.lang.Integer.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean updateFormList(final int dept) {
        final String sql = "Update appries_formlist set flag = 1 where formNum =" + dept;
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertFormList(final int dept) {
        final String sql = "insert appries_formlist (formNum,flag) values (" + dept + ",1)";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List getPlayListIds(final String deptIds) {
        final String sql = "select dept_playListId from appries_dept where id in (" + deptIds + ")   group by dept_playListId";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean update(final Integer id, final String name) {
        try {
            final String sql = "update appries_dept set name=? where id=? ";
            final Object[] args = { name, id };
            this.getJdbcTemplate().update(sql, args);
            ClearCache();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(final Integer id, final String name, final String remark) {
        try {
            final String sql = "update appries_dept set name=?,remark=? where id=?";
            final Object[] args = { name, remark, id };
            this.getJdbcTemplate().update(sql, args);
            ClearCache();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(final Integer id, final String name, final String remark, final String client_type) {
        try {
            final String sql = "update appries_dept set name=?,remark=? ,client_type=? where id=?";
            final Object[] args = { name, remark, client_type, id };
            this.getJdbcTemplate().update(sql, args);
            ClearCache();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List getAllleafes() {
        final String sql = "select * from appries_dept where deptType=0";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List getAllleafes(final String ids) {
        final String sql = "select * from appries_dept where deptType=0 and id in(" + ids + ")";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List getAlllDating() {
        final String sql = "select * from appries_dept where deptType=1";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List getAllSection(final String ids) {
        final String sql = "select * from appries_dept where deptType=0 and id in(" + ids + ") group by name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findOnline(final String mac, final int start, final int num) {
        final String sql = "select * from  (  (select name,remark ,'\u5728\u7ebf' as online from appries_dept where remark in(" + mac + ")  and depttype=0 order by fatherId ,name ) union   (select name,remark ,'\u4e0d\u5728\u7ebf' as online from appries_dept where      remark not in(" + mac + ")  and depttype=0  order by fatherId ,name)) as t1  limit " + start + "," + num + "   ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findOnlineMacNotNull(final String mac, final int start, final int num) {
        final String sql1 = "select * from  (  (select name,remark ,'\u5728\u7ebf' as online from appries_dept where remark in(" + mac + ")  and depttype=0 order by fatherId ,name ) union   (select name,remark ,'\u4e0d\u5728\u7ebf' as online from appries_dept where      remark not in(" + mac + ")  and depttype=0  order by fatherId ,name)) as t1  limit " + start + "," + num + "   ";
        final String sql2 = "select * from  (  (select name,remark ,'\u5728\u7ebf' as online from appries_dept where remark in(" + mac + ")  and depttype=0 and remark <>'' order by fatherId ,name )" + " union   (select name,remark ,'\u4e0d\u5728\u7ebf' as online from appries_dept where remark not in(" + mac + ")  and depttype=0  order by fatherId ,name) union" + "(select name,remark ,'\u4e0d\u5728\u7ebf' as online from appries_dept where remark in(" + mac + ")  and depttype=0 and remark='' order by fatherId ,name )" + ") as t1  limit " + start + "," + num + "   ";
        try {
            return this.getJdbcTemplate().queryForList(sql2);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findOnlineMacNotNull2(final String mac, final int start, final int num) {
        final String sql1 = "select * from  (  (select name,t2.name as fathername,'\u5728\u7ebf' as online from appries_dept where remark in(" + mac + ")  and depttype=0 order by fatherId ,name ) union   (select name,remark ,'\u4e0d\u5728\u7ebf' as online from appries_dept where      remark not in(" + mac + ")  and depttype=0  order by fatherId ,name)) as t1  limit " + start + "," + num + "   ";
        final String sql2 = "select * from  (  (select t1.name,t2.name as fatherName,t1.remark ,'\u5728\u7ebf' as online from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.remark in(" + mac + ")  and t1.depttype=0 and t1.remark <>'' order by t1.fatherId ,t1.name )" + " union   (select t1.name,t2.name as fatherName,t1.remark ,'\u4e0d\u5728\u7ebf' as online from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.remark not in(" + mac + ")  and t1.depttype=0  order by t1.fatherId ,t1.name) union" + "(select t1.name,t2.name as fatherName,t1.remark ,'\u4e0d\u5728\u7ebf' as online from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.remark in(" + mac + ")  and t1.depttype=0 and t1.remark='' order by t1.fatherId ,t1.name )" + ") as t3  limit " + start + "," + num + "   ";
        try {
            return this.getJdbcTemplate().queryForList(sql2);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List findAllOnline(final String mac) {
        final String sql = "select * from  (  (select t1.name,t2.name as fatherName,t1.remark ,'\u5728\u7ebf' as online from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.remark in(" + mac + ")  and t1.depttype=0 and t1.remark <>'' order by t1.fatherId ,t1.name )" + " union   (select t1.name,t2.name as fatherName,t1.remark ,'\u4e0d\u5728\u7ebf' as online from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.remark not in(" + mac + ")  and t1.depttype=0  order by t1.fatherId ,t1.name) union" + "(select t1.name,t2.name as fatherName,t1.remark ,'\u4e0d\u5728\u7ebf' as online from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.remark in(" + mac + ")  and t1.depttype=0 and t1.remark='' order by t1.fatherId ,t1.name )" + ") as t3 " + "   ";
        try {
            final List l = this.getJdbcTemplate().queryForList(sql);
            return l;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateCitys(final int id, final int cityid, final int provinceid) {
        final List<Integer> list = new ArrayList<Integer>();
        String sql = "";
        this.getChildIds(id, list);
        if (list.size() > 0) {
            sql = "update appries_dept  set cityid=" + cityid + "  ,provinceid=" + provinceid + " where id in ( " + StringUtils.getInString(list) + "," + id + " )";
        }
        else {
            sql = "update appries_dept  set cityid=" + cityid + "  ,provinceid=" + provinceid + " where id =" + id;
        }
        try {
            final int num = this.getJdbcTemplate().update(sql);
            ClearCache();
            if (num > 0) {
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }

    public List<Integer> getChildIds(int id, final List<Integer> ids) {
        final String sql = "select * from appries_dept  where  fatherId =" + id;
        List list = new ArrayList();
        try {
            list = this.getJdbcTemplate().queryForList(sql);
            for (final Object map1 : list) {
            	Map map = (Map)map1;
                id = (int)map.get("id");
                ids.add(id);
                this.getChildIds(id, ids);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ids;
    }

    public boolean updateDeptMacTime(final String mac, final String dt, final String clientVer) {
        String sql = "";
        if (!clientVer.equals(null) && !clientVer.equals("")) {
            sql = "UPDATE appries_dept SET ext5='" + dt + "',ext3='" + clientVer + "' where remark ='" + mac + "'";
        }
        else {
            sql = "UPDATE appries_dept SET ext5='" + dt + "' where remark ='" + mac + "'";
        }
        try {
            final int num = this.getJdbcTemplate().update(sql);
            ClearCache();
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateIpAdd(String mac, String ipadd) {
        final String sql = "update appries_dept set ipadd='" + ipadd + "' where remark ='" + mac + "'";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            if (num > 0) {
                return true;
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean addErrorMac(final String mac) {
        final String sql = "insert into appries_onlinemac (mac) values(?)";
        final Map tempMap = null;
        final Object[] args = { mac };
        try {
            final int i = this.getJdbcTemplate().update(sql, args);
            return i > 0;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List getAllleafesAll3(final String deptIds, final String dt) {
        final String sql = "select * from ((select t1.id,t1.ext5,t1.name,t2.name as fatherName,t1.remark,t1.ext3 ,'\u5728\u7ebf' as online from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.depttype=0  and t1.ext5 >= '" + dt + "') union (select t1.id,t1.ext5,t1.name,t2.name as fatherName,t1.remark,t1.ext3 ,'\u4e0d\u5728\u7ebf' as online from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.ext5<'" + dt + "' and t1.depttype=0) union(select t1.id,t1.ext5,t1.name,t2.name as fatherName,t1.remark ,t1.ext3,'不在线' as online from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.ext5<'" + dt + "' and t1.depttype=0)) as t3 where t3.id in(" + deptIds + ") order by name";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List findOnlineMacNotNull3(final String dt, final int start, final int num, final String deptIds) {
        String sql = "select row_number() over(order by ext5 desc,fatherId ,name) as rows, * from  ((select b.name as fatherNames , a.* from(select t1.id,t1.ext5, t2.fatherId as fatherId, t1.name,t2.name as fatherName,t1.remark,t1.ext3 ,'在线' as online,t1.ipadd from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.ext5 >= '" + dt + "'  and t1.depttype=0) as a left join appries_dept as b on a.fatherId = b.id)" + " union   (select b.name as fatherNames , a.* from( select t1.id,t1.ext5, t2.fatherId as fatherId,t1.name,t2.name as fatherName,t1.remark,t1.ext3 ,'不在线' as online,t1.ipadd from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.ext5 < '" + dt + "'  and t1.depttype=0) as a left join appries_dept as b on a.fatherId = b.id) union" + "(select b.name as fatherNames , a.* from( select t1.id,t1.ext5, t2.fatherId as fatherId,t1.name,t2.name as fatherName,t1.remark,t1.ext3 ,'不在线' as online,t1.ipadd from appries_dept as t1 left join appries_dept as t2 on t1.fatherId=t2.id where t1.ext5 < '" + dt + "' and t1.depttype=0) as a left join appries_dept as b on a.fatherId = b.id)" + ") as t3 where t3.id in(" + deptIds + ")";
        try {
            sql = "select * from ("+sql+") t where t.rows>" + start + " and t.rows<=" + (num+start);
            logger.info(sql);
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
}
