package com.sundyn.service;

import com.sundyn.dao.*;
import com.sundyn.vo.*;
import java.util.*;

public class BusinessService extends SuperDao
{
    private List ls;
    
    public BusinessService() {
        this.ls = new ArrayList();
    }
    
    public List findall() {
        final String sql = "select  *  from appries_business";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public List find(final boolean isUse) {
        final String sql = "select  *  from appries_business where businessIsUse=?";
        final Object[] args = { isUse };
        try {
            return this.getJdbcTemplate().queryForList(sql, args);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public boolean update(final BusinessVo vo) {
        final String sql = "update appries_business  set businessName=?,businessDescription=?  where businessId=? ";
        final Object[] args = { vo.getBusinessName(), vo.getBusinessDescription(), vo.getBusinessId() };
        try {
            this.getJdbcTemplate().update(sql, args);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public List findByFatherId(final Integer id) {
        final String sql = "select * from appries_business  where businessFatherId='" + id + "'";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public List findByFatherIdAllTemp(Integer id) {
        final Map m = this.findById(id);
        if (m != null) {
            this.ls.add(m);
        }
        final List ls = this.findByFatherId(id);
        if (ls != null && ls.size() > 0) {
            for (int i = 0; i < ls.size(); ++i) {
                final Map b = (Map) ls.get(i);
                id = Integer.valueOf(b.get("businessId").toString());
                this.findByFatherIdAllTemp(id);
            }
        }
        return this.ls;
    }
    
    public List findByFatherIdAll(final Integer id) {
        final List l = this.findByFatherIdAllTemp(id);
        this.ls = new ArrayList();
        return l;
    }
    
    public Map findById(final Integer id) {
        final String sql = "select * from appries_business where businessId='" + id + "'";
        try {
            return this.getJdbcTemplate().queryForMap(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public String findByFatherIdAllId(final Integer id) {
        String res = "";
        final List ls = this.findByFatherIdAll(id);
        for (int i = 0; i < ls.size(); ++i) {
        	Map map = (Map)ls.get(i);
        	res = res.concat(map.get("businessId").toString()).concat(",");
        }
        if (res.endsWith(",")) {
            res = res.substring(0, res.length() - 1);
        }
        return res;
    }
    
    public boolean add(final BusinessVo business) {
        final String sql = "insert into    appries_business  (businessName,businessDescription,businessFatherId,businessIndex,businessIsUse,businessLevel)values(?,?,?,?,?,?)";
        final String sql2 = "update  appries_business set businessIsLeaf = 0 where businessId =" + business.getBusinessFatherId();
        final Map m = this.findById(business.getBusinessFatherId());
        final Integer businessLevel = Integer.valueOf(m.get("businessLevel").toString()) + 1;
        final Object[] args = { business.getBusinessName(), business.getBusinessDescription(), business.getBusinessFatherId(), business.getBusinessIndex(), business.getBusinessIsUse(), businessLevel };
        try {
            this.getJdbcTemplate().update(sql, args);
            this.getJdbcTemplate().update(sql2);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean del(final String ids) {
        final String sql = "delete from     appries_business where businessId in (" + ids + ")";
        try {
            this.getJdbcTemplate().update(sql);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean del(final Integer id) {
        try {
            final String sqltemp = "select count(*) from appries_business  where businessFatherId in (     select  businessFatherId  from (     select businessFatherId from appries_business where businessId in( " + id + ")    )  as temp)";
            if (this.getJdbcTemplate().queryForObject(sqltemp, null, Integer.class) == 1) {
                final String sql = "update appries_business set businessIsLeaf=1 where businessId in (       select  businessFatherId  from (     select businessFatherId from appries_business where businessId in( " + id + ")    )  as temp)";
                this.getJdbcTemplate().update(sql);
            }
            this.del(this.findByFatherIdAllId(id));
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public List getAllLeafs(final String ids) {
        final String sql = "select businessId,businessName,businessDescription from  appries_business where businessIsLeaf=1 and businessId in(" + ids + ") order by businessFatherId asc,businessId asc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            return null;
        }
    }
}
