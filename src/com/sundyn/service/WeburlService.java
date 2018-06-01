package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.util.*;

public class WeburlService extends SuperDao
{
    public boolean addWeburl(final WeburlVo weburl) {
        final String sql = "Insert into appries_weburl (name, url) values(?,?)";
        final Object[] arg = { weburl.getName(), weburl.getUrl() };
        try {
            final int id = this.getJdbcTemplate().update(sql, arg);
            System.out.println(id);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List findWeburl() {
        final String sql = "select * from appries_weburl ";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findWeburl(final String key_title, final int startrow, final int pageSize) {
        String sql = "select row_number() over(order by id desc) as rows, * from appries_weburl where 1=1 ";
        if(null!=key_title && !"".equals(key_title))
            sql += "and name like '%"+key_title+"%'";
        sql = "select * from ("+sql+") t where t.rows>" + startrow + " and t.rows<=" + (pageSize+startrow);
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean weburlDelete(final int id) {
        final String sql = "delete from appries_weburl where id=?";
        int num = 0;
        final Object[] arg = { id };
        try {
            num = this.getJdbcTemplate().update(sql, arg);
            if (num > 0) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean weburlUpdate(final WeburlVo weburl) {
        int num = 0;
        final String sql = "update appries_weburl set name=?,url=? where id=?";
        final Object[] args = { weburl.getName(), weburl.getUrl(), weburl.getId() };
        try {
            num = this.getJdbcTemplate().update(sql, args);
            if (num > 0) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Map findWeburlById(final int id) {
        final String sql = "select * from appries_weburl  where id=?";
        final Object[] arg = { id };
        try {
            return this.getJdbcTemplate().queryForMap(sql, arg);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int getCount(String key_title) {
        String sql = "select count(id) from appries_weburl where 1=1 ";
        if(null!=key_title && !"".equals(key_title))
            sql += "and name like '%"+key_title+"%'";
        return this.getJdbcTemplate().queryForInt(sql);
    }
    
    public List findWeburlChoose(final int deptId) {
        final String sql = "select * from appries_weburl where id in(select weburlId from appries_weburl_dept where deptId=" + deptId + ")";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List findWeburlNoChoose(final int deptId) {
        final String sql = "select * from appries_weburl where id not in(select weburlId from appries_weburl_dept where deptId=" + deptId + ")";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List bindedWeburl(final String mac) {
        final String sql = "select * from appries_weburl where id  in( select weburlId from appries_weburl_dept where deptId=( select id from appries_dept where remark='" + mac + "'))";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean deleteWeburlCancelChoose(final String ids, final int deptId) {
        int num = 0;
        final String sql = "delete from appries_weburl_dept  where  deptId=" + deptId;
        try {
            num = this.getJdbcTemplate().update(sql);
            if (num > 0) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateWeburlChoose(final String ids, final int deptId) {
        int num = 0;
        final String hql = "delete from appries_weburl_dept  where  deptId=" + deptId;
        try {
            num = this.getJdbcTemplate().update(hql);
            if (num < 0) {
                return false;
            }
            final String[] idss = ids.split(",");
            String[] array;
            for (int length = (array = idss).length, i = 0; i < length; ++i) {
                final String id = array[i];
                if (!id.equals("")) {
                    if (id != null) {
                        final String sql = "insert into  appries_weburl_dept(weburlId,deptId) values('"+id+"','"+deptId+"')";
                        num = this.getJdbcTemplate().update(sql);
                        if (num < 1) {
                            return false;
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean testAction(final String name) {
        String sql = "";
        sql = "insert into test (name) values(?)";
        final Object[] args = { name };
        final int num = this.getJdbcTemplate().update(sql, args);
        return num > 0;
    }
}
