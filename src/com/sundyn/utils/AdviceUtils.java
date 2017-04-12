package com.sundyn.utils;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class AdviceUtils extends ActionSupport
{
    public static void getAdviceTables(final AdviceService adviceService, final String filepath, final StringHql str) throws SQLException, Exception {
        Statement stat = null;
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        try {
            String path = ServletActionContext.getServletContext().getRealPath("/dbdownload");
            final File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            path = String.valueOf(path.replace("\\", "/")) + "/advise.db";
            str.setStr(path);
            final File pat = new File(path);
            conn = DriverManager.getConnection("jdbc:sqlite:/c:/advise.db");
            stat = conn.createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS 'question';");
            stat.executeUpdate("DROP TABLE IF EXISTS 'answer';");
            stat.executeUpdate("create table question(id integer primekey, content text);");
            final List list = adviceService.getAllQuestions();
            for (final Object map1 : list) {
            	Map map = (Map)map1;
                final String sql = "insert into question(id,content) values(" + map.get("id") + ", '" + map.get("question") + "');";
                stat.execute(sql);
            }
            stat.executeUpdate("create table answer(id integer primekey, content text,fatherid integer);");
            final List list2 = adviceService.getAllAnswers();
            for (final Object map21 : list2) {
            	Map map2 = (Map)map21;
                final String sql2 = "insert into answer(id,content,fatherid) values(" + map2.get("id") + ", '" + map2.get("answer") + "', " + map2.get("fatherid") + ")";
                stat.execute(sql2);
            }
            new FileMove();
            FileMove.copy("c:/advise.db", path);
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                if (stat != null) {
                    stat.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            return;
        }
        finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        try {
            if (stat != null) {
                stat.close();
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
