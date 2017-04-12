package com.sundyn.utils;

import java.sql.*;

public class DBConnHelper
{
    public static Connection getConnection(final String driver) {
        Connection con = null;
        try {
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
            con = DriverManager.getConnection(driver);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        return con;
    }
    
    public static void closeResource(final PreparedStatement ps, final ResultSet rs, Connection cnn) {
        try {
            if (rs != null) {
                rs.close();
            }
        }
        catch (Exception es) {
            es.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        }
        catch (Exception es) {
            es.printStackTrace();
        }
        try {
            if (cnn != null) {
                cnn.close();
                cnn = null;
            }
        }
        catch (Exception es) {
            es.printStackTrace();
        }
    }
    
    public static boolean emptyTable(final String sql) {
        final Connection con = getConnection("proxool.DbPool");
        Statement st = null;
        try {
            st = con.createStatement();
            st.executeUpdate(sql);
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
