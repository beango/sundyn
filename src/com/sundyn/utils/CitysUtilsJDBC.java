package com.sundyn.utils;

import com.sundyn.db.*;
import java.sql.*;
import com.sundyn.entity.*;
import java.util.*;

public class CitysUtilsJDBC
{
    public static List<Province> getProvinces() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        final List<Province> provinces = new ArrayList<Province>();
        final String sql = "select * from provinces";
        try {
            conn = DbUtils.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Province p = new Province();
                p = getProvinceWithCitysById(Integer.valueOf(rs.getInt("_id")));
                provinces.add(p);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DbUtils.close(conn, st, rs);
        }
        DbUtils.close(conn, st, rs);
        return provinces;
    }
    
    public static List<Province> getProvincesOnly() throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        final List<Province> provinces = new ArrayList<Province>();
        final String sql = "select * from provinces";
        try {
            conn = DbUtils.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                final Province p = new Province();
                p.setId(rs.getInt("_id"));
                p.setName(rs.getString("name"));
                provinces.add(p);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DbUtils.close(conn, st, rs);
        }
        DbUtils.close(conn, st, rs);
        return provinces;
    }
    
    public static Province getProvinceWithCitysById(final int id) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        final Province p = new Province();
        final String sql = "select * from provinces where _id=" + id;
        try {
            conn = DbUtils.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                p.setId(rs.getInt("_id"));
                p.setName(rs.getString("name"));
                p.setCitys((List)getCitys(p.getId()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DbUtils.close(conn, st, rs);
        }
        DbUtils.close(conn, st, rs);
        return p;
    }
    
    public static Province getProvinceById(final int id) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        final Province p = new Province();
        final String sql = "select * from provinces where _id=" + id;
        try {
            conn = DbUtils.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                p.setId(rs.getInt("_id"));
                p.setName(rs.getString("name"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DbUtils.close(conn, st, rs);
        }
        DbUtils.close(conn, st, rs);
        return p;
    }
    
    public static City getCityById(final int id) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        final City c = new City();
        final String sql = "select * from citys where _id=" + id;
        try {
            conn = DbUtils.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                c.setId(rs.getInt("_id"));
                c.setName(rs.getString("name"));
                c.setCitynum(rs.getString("city_num"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DbUtils.close(conn, st, rs);
        }
        DbUtils.close(conn, st, rs);
        return c;
    }
    
    private static City getCity(final int id, final int provinceid) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        final City c = new City();
        final String sql = "select * from citys where _id=" + id + " and province_id=" + provinceid;
        try {
            conn = DbUtils.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                c.setId(rs.getInt("_id"));
                c.setName(rs.getString("name"));
                c.setCitynum(rs.getString("city_num"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DbUtils.close(conn, st, rs);
        }
        DbUtils.close(conn, st, rs);
        return c;
    }
    
    public static List<City> getCitys(final int provinceid) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        final List<City> citys = new ArrayList<City>();
        final String sql = "select * from citys where  province_id=" + provinceid;
        try {
            conn = DbUtils.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                final City c = new City();
                c.setId(rs.getInt("_id"));
                c.setName(rs.getString("name"));
                c.setCitynum(rs.getString("city_num"));
                citys.add(c);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DbUtils.close(conn, st, rs);
        }
        DbUtils.close(conn, st, rs);
        return citys;
    }
    
    public static void main(final String[] args) throws SQLException {
        final List<Province> list = getProvinces();
        System.out.println(list.size());
        for (final Province p : list) {
            System.out.println(p.getName());
            for (final City c : p.getCitys()) {
                System.out.println(String.valueOf(c.getName()) + "**" + c.getCitynum());
            }
        }
    }
}
