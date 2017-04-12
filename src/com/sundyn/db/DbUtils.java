package com.sundyn.db;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DbUtils
{
    private static Properties prop;
    private static String url;
    private static String username;
    private static String password;
    
    static {
        DbUtils.prop = new Properties();
        try {
            DbUtils.prop.load(DbUtils.class.getClassLoader().getResourceAsStream("db.properties"));
            /*DbUtils.url = ((Hashtable<K, String>)DbUtils.prop).get("url");
            DbUtils.username = ((Hashtable<K, String>)DbUtils.prop).get("userName");
            DbUtils.password = ((Hashtable<K, String>)DbUtils.prop).get("password");
            Class.forName(((Hashtable<K, String>)DbUtils.prop).get("driver"));*/
            
            DbUtils.url = DbUtils.prop.getProperty("url");
            DbUtils.username = DbUtils.prop.getProperty("userName");
            DbUtils.password = DbUtils.prop.getProperty("password");
            Class.forName(DbUtils.prop.getProperty("driver"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DbUtils.url, DbUtils.username, DbUtils.password);
    }
    
    public static void close(final Connection con, final Statement stmt, final ResultSet rs) throws SQLException {
        if (con != null) {
            try {
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
