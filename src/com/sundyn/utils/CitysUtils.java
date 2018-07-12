package com.sundyn.utils;

import com.sundyn.cache.Cache;
import com.sundyn.cache.CacheManager;
import com.sundyn.dao.SuperDao;
import com.sundyn.entity.City;
import com.sundyn.entity.Province;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CitysUtils extends SuperDao
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public List<Province> getProvinces() {
        final List<Province> provinces = new ArrayList<Province>();
        final String sql = "select * from provinces";
        try {
            final List list = this.getJdbcTemplate().queryForList(sql);
            for (final Object m1 : list) {
            	Map m = (Map)m1;
                Province p = new Province();
                p = this.getProvinceWithCitysById((int)m.get("_id"));
                provinces.add(p);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return provinces;
    }
    
    public List<Province> getProvincesOnly() throws SQLException {
        String Key = CacheManager.CitysUtils_getProvincesOnly;
        Cache data = CacheManager.getCacheInfo(Key);
        if(data!=null){
            return (List<Province>)data.getValue();
        }
        final List<Province> provinces = new ArrayList<Province>();
        final String sql = "select * from provinces";
        try {
            final List list = this.getJdbcTemplate().queryForList(sql);
            for (final Object m1 : list) {
                Map m = (Map)m1;
                final Province p = new Province();
                p.setId((int)m.get("_id"));
                p.setName((String)m.get("name"));
                provinces.add(p);
            }
            data = new Cache();
            data.setValue(provinces);
            CacheManager.AddKey(Key);
            CacheManager.putCache(Key, data);
        }
        catch (Exception e) {
            e.printStackTrace();
            return provinces;
        }
        return provinces;
    }
    
    public Province getProvinceWithCitysById(final int id) {
        final Province p = new Province();
        final String sql = "select * from provinces where _id=?";
        try {
            final Map m = this.getJdbcTemplate().queryForMap(sql, new Object[] { id });
            if (m.size() > 0) {
                p.setId((int)m.get("_id"));
                p.setName((String)m.get("name"));
                p.setCitys((List)this.getCitys(p.getId()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return p;
    }

    public Province getProvinceDef() {
        final Province p = new Province();
        final String sql = "select top 1 * from provinces where def=?";
        try {
            final Map m = this.getJdbcTemplate().queryForMap(sql, new Object[] { 1 });
            if (m.size() > 0) {
                p.setId((int)m.get("_id"));
                p.setName((String)m.get("name"));
                p.setCitys((List)this.getCitys(p.getId()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return p;
    }

    private Province getProvinceById(final int id) {
        final Province p = new Province();
        final String sql = "select * from provinces where _id=?";
        try {
            final Map m = this.getJdbcTemplate().queryForMap(sql, new Object[] { id });
            if (m.size() > 0) {
                p.setId((int)m.get("_id"));
                p.setName((String)m.get("name"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return p;
    }
    
    public City getCityById(final int id) {
        final City c = new City();
        final String sql = "select * from citys where _id=?";
        try {
            final Map m = this.getJdbcTemplate().queryForMap(sql, new Object[] { id });
            if (m.size() > 0) {
                c.setId((int)m.get("_id"));
                c.setName((String)m.get("name"));
                c.setCitynum((String)m.get("city_num"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return c;
    }
    
    private City getCity(final int id, final int provinceid) {
        final City c = new City();
        final String sql = "select * from citys where _id=? and province_id=?";
        try {
            final Map m = this.getJdbcTemplate().queryForMap(sql, new Object[] { id, provinceid });
            if (m.size() > 0) {
                c.setId((int)m.get("_id"));
                c.setName((String)m.get("name"));
                c.setCitynum((String)m.get("city_num"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return c;
    }
    
    public List<City> getCitys(final int provinceid) {
        final List<City> citys = new ArrayList<City>();
        final String sql = "select * from citys where  province_id=?";
        try {
            final List list = this.getJdbcTemplate().queryForList(sql, new Object[] { provinceid });
            for (final Object m1 : list) {
            	Map m = (Map)m1;
                final City c = new City();
                c.setId((int)m.get("_id"));
                c.setName((String)m.get("name"));
                c.setCitynum((String)m.get("city_num"));
                citys.add(c);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return citys;
    }
}
