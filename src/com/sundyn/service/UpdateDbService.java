package com.sundyn.service;

import com.sundyn.dao.*;
import java.util.*;
import com.sundyn.util.*;
import org.springframework.jdbc.core.*;

public class UpdateDbService extends SuperDao
{
    public List getAllTable() {
        return this.getJdbcTemplate().queryForList(" select TABLE_NAME from information_schema.tables where TABLE_SCHEMA='appries' and TABLE_TYPE='BASE TABLE'");
    }
    
    public List getTableInfo(final String tableName) {
        return this.getJdbcTemplate().queryForList("desc " + tableName);
    }
    
    public void executeSql(final String sqlTxt) {
        this.getJdbcTemplate().execute(sqlTxt);
    }
    
    public List getProc() {
        return this.getJdbcTemplate().query("select db, name, param_list,body   from mysql.proc where db='appries'", (RowMapper)new DbMapper());
    }
    
    public List getView() {
        return this.getJdbcTemplate().queryForList("select TABLE_NAME,VIEW_DEFINITION from information_schema.views");
    }
}
