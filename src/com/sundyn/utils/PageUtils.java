package com.sundyn.utils;

import com.sundyn.dao.*;
import com.sundyn.db.DbUtils;

import java.io.*;
import org.hibernate.*;
import java.sql.*;
import java.util.*;

public class PageUtils extends SuperDao implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public static void loadMaxPage(final Pagination p, final String hql, final Session session) throws SQLException {
        final String newHql = "select count(*) " + hql;
        final int count = Integer.parseInt(new StringBuilder().append(session.createQuery(newHql).uniqueResult()).toString());
        p.setCountTotal(count);
        final int maxPage = (count % p.getPageSize() == 0) ? (count / p.getPageSize()) : (count / p.getPageSize() + 1);
        p.setMaxPage(maxPage);
    }
    
    public static void loadMaxPage1(final Pagination p, final String hql, final Session session) throws SQLException {
        final String newHql = "select count(*) " + hql;
        final int count = Integer.parseInt(new StringBuilder().append(session.createQuery(newHql).uniqueResult()).toString());
        p.setCountTotal(count);
        final int maxPage = (count % p.getPageSize() == 0) ? (count / p.getPageSize()) : (count / p.getPageSize() + 1);
        p.setMaxPage(maxPage);
    }
    
    public static void loadMaxPage2(final Pagination p, final String hql) throws SQLException {
        final String newHql = "select count(*) from " + hql;
        final int count = Integer.parseInt(new StringBuilder().append(new PageUtils().getCount(newHql)).toString());
        p.setCountTotal(count);
        final int maxPage = (count % p.getPageSize() == 0) ? (count / p.getPageSize()) : (count / p.getPageSize() + 1);
        p.setMaxPage(maxPage);
    }
    
    public static int totalCountsToPage(final Pagination page) {
        int totalcounts = 0;
        if (page != null) {
            if (page.getPage() < page.getMaxPage()) {
                totalcounts = page.getPage() * page.getPageSize();
            }
            else if (page.getPage() == page.getMaxPage()) {
                totalcounts = page.getCountTotal();
            }
        }
        return totalcounts;
    }
    
    public int getCount(final String sql) {
        int count = 0;
        count = this.getJdbcTemplate().queryForInt(sql);
        return count;
    }
    
    public static int totalCountsToPage1(final Pagination page) {
        int totalcounts = 0;
        if (page != null) {
            if (page.getPage() < page.getMaxPage()) {
                totalcounts = page.getPage() * page.getPageSize();
            }
            else if (page.getPage() == page.getMaxPage()) {
                totalcounts = page.getCountTotal();
            }
        }
        return totalcounts;
    }
    
    public static int sumsToPage(final String tableName, final String shortName, final String column, final int count, final HQLSql h) throws Exception {
        int sum = 0;
        final Properties p = new Properties();
        p.load(PageUtils.class.getClassLoader().getResourceAsStream("/props/db.properties"));
        /*final String DriverName = ((Hashtable<K, String>)p).get("driverClassName");
        final String username = ((Hashtable<K, String>)p).get("username");
        final String password = ((Hashtable<K, String>)p).get("password");
        final String url = ((Hashtable<K, String>)p).get("url");*/
        
        final String url = p.getProperty("url");
        final String username = p.getProperty("username");
        final String password = p.getProperty("password");
        final String DriverName = p.getProperty("driverClassName");
        
        Class.forName(DriverName);
        final Connection conn = DriverManager.getConnection(url, username, password);
        final Statement stat = conn.createStatement();
        final String sql = "select sum(" + column + ") from (select " + column + " " + h.getHql() + " limit 0," + count + ") a;";
        System.out.println(sql);
        final ResultSet rs = stat.executeQuery(sql);
        while (rs.next()) {
            sum = rs.getInt(1);
        }
        rs.close();
        stat.close();
        conn.close();
        return sum;
    }
}
