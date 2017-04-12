package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import com.sundyn.vo.*;
import java.sql.*;
import org.jdom.*;
import org.jdom.output.*;
import org.jdom.output.Format;

import java.text.*;
import com.sundyn.util.*;
import java.io.*;
import org.jdom.input.*;
import java.util.*;
import java.util.Date;

public class UpdateDbAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    private InputStream file;
    private String fileName;
    private UpdateDbService updateDbService;
    private String msg;
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public UpdateDbService getUpdateDbService() {
        return this.updateDbService;
    }
    
    public void setUpdateDbService(final UpdateDbService updateDbService) {
        this.updateDbService = updateDbService;
    }
    
    public InputStream getFile() {
        return this.file;
    }
    
    public void setFile(final InputStream file) {
        this.file = file;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }
    
    public String updateDbBackUP() {
        final List tables = this.updateDbService.getAllTable();
        final Element root = new Element("updateDb");
        final Element e_tables = new Element("tables");
        if (tables != null && tables.size() > 0) {
            for (int i = 0; i < tables.size(); ++i) {
                final Map m = (Map) tables.get(i);
                final Element e_table = new Element("table");
                final String tableName = m.get("TABLE_NAME").toString();
                e_table.setAttribute("tableName", tableName);
                final List tableInfo = this.updateDbService.getTableInfo(tableName);
                for (int j = 0; j < tableInfo.size(); ++j) {
                    final Map info = (Map) tableInfo.get(j);
                    final Iterator iterator = info.keySet().iterator();
                    final Element e_columns = new Element("column");
                    while (iterator.hasNext()) {
                        final String key = iterator.next().toString();
                        final Element e_column = new Element(key);
                        if (info.get(key) != null) {
                            e_column.setText(info.get(key).toString());
                        }
                        else {
                            e_column.setText("");
                        }
                        e_columns.addContent((Content)e_column);
                    }
                    e_table.addContent((Content)e_columns);
                }
                e_tables.addContent((Content)e_table);
            }
        }
        root.addContent((Content)e_tables);
        final List viewLs = this.updateDbService.getView();
        final Element e_views = new Element("views");
        if (viewLs != null) {
            for (int k = 0; k < viewLs.size(); ++k) {
                final Element e_view = new Element("view");
                final Map l = (Map) viewLs.get(k);
                e_view.setAttribute("name", l.get("TABLE_NAME").toString());
                String sql = "DROP  VIEW   IF EXISTS    " + l.get("TABLE_NAME").toString() + "; \r\n";
                sql = String.valueOf(sql) + "CREATE  VIEW `" + l.get("TABLE_NAME").toString() + "` AS  \r\n " + l.get("VIEW_DEFINITION").toString();
                sql = String.valueOf(sql) + ";";
                e_view.setText(sql);
                e_views.addContent((Content)e_view);
            }
        }
        root.addContent((Content)e_views);
        final List procLs = this.updateDbService.getProc();
        final Element e_procs = new Element("procs");
        if (procLs != null) {
            for (int i2 = 0; i2 < procLs.size(); ++i2) {
                final Dbtype m2 = (Dbtype) procLs.get(i2);
                String param_list = "";
                String body = "";
                try {
                    final InputStream in = m2.getParam_list().getBinaryStream();
                    long len = m2.getParam_list().length();
                    byte[] data = m2.getParam_list().getBytes(1L, Integer.parseInt(new StringBuilder().append(len).toString()));
                    param_list = new String(data);
                    len = m2.getBody().length();
                    data = m2.getBody().getBytes(1L, Integer.parseInt(new StringBuilder().append(len).toString()));
                    body = new String(data);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                final Element e_proc = new Element("proc");
                e_proc.setAttribute("name", m2.getName());
                String sql2 = "DROP  PROCEDURE   IF EXISTS    " + m2.getName() + " ;";
                sql2 = String.valueOf(sql2) + "CREATE   PROCEDURE `" + m2.getName() + "`  (" + param_list + ")\r\n";
                sql2 = String.valueOf(sql2) + body;
                sql2 = String.valueOf(sql2) + ";";
                e_proc.setText(sql2);
                e_procs.addContent((Content)e_proc);
            }
        }
        root.addContent((Content)e_procs);
        final Element e_execute = new Element("execute");
        final Element e_sql = new Element("sql");
        e_execute.addContent((Content)e_sql);
        root.addContent((Content)e_execute);
        final Document doc = new Document(root);
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        final XMLOutputter XMLOut = new XMLOutputter(format);
        try {
            final ByteArrayOutputStream xml = new ByteArrayOutputStream();
            XMLOut.output(doc, (OutputStream)xml);
            this.file = new ByteArrayInputStream(xml.toByteArray());
            this.fileName = "backup" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ".xml";
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return "success";
    }
    
    public String updateDbUpdate() {
        final File f = new File(String.valueOf(Path.getRootPath()) + "backup.xml");
        final SAXBuilder sb = new SAXBuilder();
        final List oldTables = this.updateDbService.getAllTable();
        final List newTables = new ArrayList();
        final List<String> newView = new ArrayList<String>();
        final List<String> newProc = new ArrayList<String>();
        final List<String> newExecute = new ArrayList<String>();
        final List<String> sqlLs = new ArrayList<String>();
        try {
            final Document doc = sb.build(f);
            this.msg = "\u5f97\u5230\u6570\u636e\u5e93\u6587\u4ef6\uff0c\u5f00\u59cb\u89e3\u6790<br>";
            final Element e_updateDb = doc.getRootElement();
            final Element e_tables = e_updateDb.getChild("tables");
            final Element e_views = e_updateDb.getChild("views");
            final Element e_procs = e_updateDb.getChild("procs");
            final Element e_execute = e_updateDb.getChild("execute");
            final List e_talbels = e_tables.getChildren();
            if (e_talbels != null) {
                for (int i = 0; i < e_talbels.size(); ++i) {
                    final Map<String, Object> table = new HashMap<String, Object>();
                    final Element e_table = (Element) e_talbels.get(i);
                    final String tableName = e_table.getAttributeValue("tableName");
                    final List e_columns = e_table.getChildren();
                    final List<Map<String, String>> columns = new ArrayList<Map<String, String>>();
                    if (e_columns != null) {
                        for (int j = 0; j < e_columns.size(); ++j) {
                            final Map<String, String> column = new HashMap<String, String>();
                            final Element e_column = (Element) e_columns.get(j);
                            column.put("COLUMN_NAME", e_column.getChildText("COLUMN_NAME"));
                            column.put("COLUMN_TYPE", e_column.getChildText("COLUMN_TYPE"));
                            column.put("IS_NULLABLE", e_column.getChildText("IS_NULLABLE"));
                            column.put("COLUMN_KEY", e_column.getChildText("COLUMN_KEY"));
                            column.put("COLUMN_DEFAULT", e_column.getChildText("COLUMN_DEFAULT"));
                            column.put("EXTRA", e_column.getChildText("EXTRA"));
                            columns.add(column);
                        }
                    }
                    table.put("tableName", tableName);
                    table.put("columns", columns);
                    newTables.add(table);
                }
            }
            this.msg = String.valueOf(this.msg) + "\u5f97\u5230\u8868\u7ed3\u6784\u6210\u529f<br>";
            final List views = e_views.getChildren();
            if (views != null) {
                for (int k = 0; k < views.size(); ++k) {
                    newView.add(((Element) views.get(k)).getText());
                }
            }
            this.msg = String.valueOf(this.msg) + "\u5f97\u5230\u89c6\u56fe<br>";
            final List procs = e_procs.getChildren();
            if (procs != null) {
                for (int l = 0; l < procs.size(); ++l) {
                    newProc.add(((Element) procs.get(l)).getText());
                }
            }
            this.msg = String.valueOf(this.msg) + "\u5f97\u5230\u5b58\u50a8\u8fc7\u7a0b<br>";
            final List executes = e_execute.getChildren();
            if (executes != null) {
                for (int m = 0; m < executes.size(); ++m) {
                    newExecute.add(((Element) executes.get(m)).getText());
                }
            }
            this.msg = String.valueOf(this.msg) + "\u5f97\u5230sql\u8bed\u53e5<br/>";
            this.msg = String.valueOf(this.msg) + "\u5f00\u59cb\u5bf9\u6bd4\u6570\u636e\u5e93<br/>";
            for (int m = 0; m < newTables.size(); ++m) {
                final Map newTable = (Map) newTables.get(m);
                final String newTableName = newTable.get("tableName").toString();
                int j;
                for (j = 0; j < oldTables.size(); ++j) {
                    final Map oldTable = (Map) oldTables.get(j);
                    final String oldTableName = oldTable.get("TABLE_NAME").toString();
                    if (oldTableName.equals(newTableName)) {
                        break;
                    }
                }
                if (j == oldTables.size()) {
                    String sql = "CREATE TABLE " + newTable.get("tableName").toString() + " (";
                    String key = "";
                    final List columns2 = (List) newTable.get("columns");
                    for (int k2 = 0; k2 < columns2.size(); ++k2) {
                        final Map column2 = (Map) columns2.get(k2);
                        sql = String.valueOf(sql) + "  `" + column2.get("COLUMN_NAME").toString() + "` " + column2.get("COLUMN_TYPE").toString() + "  " + (column2.get("IS_NULLABLE").toString().equals("NO") ? "NOT NULL" : "");
                        if (column2.get("COLUMN_DEFAULT").toString().equals("NULL")) {
                            sql = String.valueOf(sql) + " default NULL";
                        }
                        else if (column2.get("COLUMN_DEFAULT").toString().equals("")) {
                            sql = new StringBuilder(String.valueOf(sql)).toString();
                        }
                        else {
                            sql = String.valueOf(sql) + " default '" + column2.get("COLUMN_DEFAULT").toString() + "' ";
                        }
                        if (column2.get("EXTRA").toString().equals("auto_increment")) {
                            sql = String.valueOf(sql) + " auto_increment ";
                        }
                        if (column2.get("COLUMN_KEY").toString().equals("PRI")) {
                            key = column2.get("COLUMN_NAME").toString();
                        }
                        sql = String.valueOf(sql) + ",";
                    }
                    if (!key.equals("")) {
                        sql = String.valueOf(sql) + "  PRIMARY KEY  (`" + key + "`)";
                    }
                    if (sql.endsWith(",")) {
                        sql = sql.substring(0, sql.length() - 1);
                    }
                    sql = String.valueOf(sql) + ") ENGINE=InnoDB DEFAULT CHARSET=gbk;";
                    sqlLs.add(sql);
                }
                else {
                    final List ls_oldColums = this.updateDbService.getTableInfo(newTableName);
                    final List newColumns = (List) newTable.get("columns");
                    for (int k3 = 0; k3 < newColumns.size(); ++k3) {
                        final Map newColumn = (Map) newColumns.get(k3);
                        int l2 = 0;
                        while (l2 < ls_oldColums.size()) {
                            final Map oldColum = (Map) ls_oldColums.get(l2);
                            if (oldColum.get("COLUMN_NAME").toString().equals(newColumn.get("COLUMN_NAME").toString())) {
                                if (!oldColum.get("COLUMN_TYPE").toString().equals(newColumn.get("COLUMN_TYPE").toString()) || (oldColum.get("COLUMN_DEFAULT") != null && !oldColum.get("COLUMN_DEFAULT").toString().equals(newColumn.get("COLUMN_DEFAULT").toString())) || !oldColum.get("EXTRA").toString().equals(newColumn.get("EXTRA").toString())) {
                                    String sql2 = "ALTER TABLE " + newTableName + " MODIFY  ";
                                    sql2 = String.valueOf(sql2) + "  " + newColumn.get("COLUMN_NAME").toString() + " " + newColumn.get("COLUMN_TYPE").toString() + "  " + (newColumn.get("IS_NULLABLE").toString().equals("NO") ? "NOT NULL" : "");
                                    if (newColumn.get("COLUMN_DEFAULT").toString().equals("NULL")) {
                                        sql2 = String.valueOf(sql2) + " default NULL";
                                    }
                                    else if (newColumn.get("COLUMN_DEFAULT").toString().equals("")) {
                                        sql2 = new StringBuilder(String.valueOf(sql2)).toString();
                                    }
                                    else {
                                        sql2 = String.valueOf(sql2) + " default '" + newColumn.get("COLUMN_DEFAULT").toString() + "' ";
                                    }
                                    sql2 = String.valueOf(sql2) + " " + newColumn.get("EXTRA").toString();
                                    sqlLs.add(sql2);
                                    break;
                                }
                                break;
                            }
                            else {
                                ++l2;
                            }
                        }
                        if (l2 == ls_oldColums.size()) {
                            String sql3 = "ALTER TABLE " + newTableName + " add  ";
                            sql3 = String.valueOf(sql3) + "  `" + newColumn.get("COLUMN_NAME").toString() + "` " + newColumn.get("COLUMN_TYPE").toString() + "  " + (newColumn.get("IS_NULLABLE").toString().equals("NO") ? "NOT NULL" : "");
                            if (newColumn.get("COLUMN_DEFAULT").toString().equals("NULL")) {
                                sql3 = String.valueOf(sql3) + " default NULL";
                            }
                            else if (newColumn.get("COLUMN_DEFAULT").toString().equals("")) {
                                sql3 = new StringBuilder(String.valueOf(sql3)).toString();
                            }
                            else {
                                sql3 = String.valueOf(sql3) + " default '" + newColumn.get("COLUMN_DEFAULT").toString() + "' ";
                            }
                            sqlLs.add(sql3);
                        }
                    }
                }
            }
            this.msg = String.valueOf(this.msg) + "\u5bf9\u6bd4\u6210\u529f\uff0c\u81ea\u52a8\u751f\u6210sql\u8bed\u53e5<br/>";
            this.msg = String.valueOf(this.msg) + "\u5f00\u59cb\u66f4\u65b0\u8868\u7ed3\u6784";
            for (int m = 0; m < sqlLs.size(); ++m) {
                this.updateDbService.executeSql(sqlLs.get(m).toString());
            }
            this.msg = String.valueOf(this.msg) + "\u66f4\u65b0\u8868\u7ed3\u6784\u6210\u529f<br/>";
            this.msg = String.valueOf(this.msg) + "\u5f00\u5982\u66f4\u65b0\u89c6\u56fe<br/>";
            for (int m = 0; m < newView.size(); ++m) {
                final String[] sql4 = newView.get(m).toString().split(";");
                for (int j2 = 0; j2 < sql4.length; ++j2) {
                    this.updateDbService.executeSql(sql4[j2]);
                }
            }
            this.msg = String.valueOf(this.msg) + "\u66f4\u65b0\u89c6\u56fe\u6210\u529f<br/>";
            this.msg = String.valueOf(this.msg) + "\u5f00\u59cb\u66f4\u65b0\u5b58\u50a8\u8fc7\u7a0b<br/>";
            for (int m = 0; m < newProc.size(); ++m) {
                final String temp = newProc.get(m).toString();
                final int index = temp.indexOf(";");
                final String sql5 = temp.substring(0, index);
                final String sql6 = temp.substring(index + 1);
                this.updateDbService.executeSql(sql5);
                this.updateDbService.executeSql(sql6);
            }
            this.msg = String.valueOf(this.msg) + "\u66f4\u65b0\u8868\u5b58\u50a8\u8fc7\u7a0b\u6210\u529f<br/>";
            this.msg = String.valueOf(this.msg) + "\u8fd0\u884csql\u8bed\u53e5<br/>";
            for (int m = 0; m < newExecute.size(); ++m) {
                final String sql7 = newExecute.get(m).toString();
                if (!sql7.equals("")) {
                    this.updateDbService.executeSql(sql7);
                }
            }
            this.msg = String.valueOf(this.msg) + "\u66f4\u65b0sql\u6210\u529f<br/><br/>";
            this.msg = String.valueOf(this.msg) + "\u6570\u636e\u5e93\u5347\u7ea7\u6210\u529f";
        }
        catch (Exception e) {
            this.msg = String.valueOf(this.msg) + "\u62a5\u9519\u4e86<br/><br/><br/>";
            this.msg = String.valueOf(this.msg) + e.getMessage();
            e.printStackTrace();
        }
        return "success";
    }
}
