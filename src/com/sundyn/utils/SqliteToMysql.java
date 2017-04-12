package com.sundyn.utils;

import java.sql.*;

public class SqliteToMysql
{
    public static void SqliteToMysqlProvinces() throws ClassNotFoundException, SQLException {
        Statement stat = null;
        Connection conn = null;
        Connection con = null;
        Statement st = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:/h:/city.db");
        stat = conn.createStatement();
        final ResultSet rs = stat.executeQuery("SELECT * FROM 'provinces';");
        while (rs.next()) {
            final int id = rs.getInt("_id");
            final String name = rs.getString("name");
            con = getConn();
            st = con.createStatement();
            final String sql = "insert into provinces (id,name) values(" + id + ",'" + name + "')";
            st.executeUpdate(sql);
        }
    }
    
    public static void SqliteToMysqlCitys() throws ClassNotFoundException, SQLException {
        Statement stat = null;
        Connection conn = null;
        Connection con = null;
        Statement st = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:/h:/city.db");
        stat = conn.createStatement();
        final ResultSet rs = stat.executeQuery("SELECT * FROM 'citys';");
        while (rs.next()) {
            final int id = rs.getInt("_id");
            final int province_id = rs.getInt("province_id");
            final String name = rs.getString("name");
            final String city_num = rs.getString("city_num");
            con = getConn();
            st = con.createStatement();
            final String sql = "insert into citys (_id,province_id,name,city_num) values(" + id + ",'" + province_id + "','" + name + "','" + city_num + "')";
            st.executeUpdate(sql);
        }
    }
    
    public static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/appries", "root", "root");
    }
    
    public static void main(final String[] arg) throws ClassNotFoundException, SQLException {
    }
}
