package com.sundyn.vo;

import java.sql.*;

public class Dbtype
{
    private String db;
    private String name;
    private Blob param_list;
    private Blob body;
    
    public String getDb() {
        return this.db;
    }
    
    public void setDb(final String db) {
        this.db = db;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Blob getParam_list() {
        return this.param_list;
    }
    
    public void setParam_list(final Blob param_list) {
        this.param_list = param_list;
    }
    
    public Blob getBody() {
        return this.body;
    }
    
    public void setBody(final Blob body) {
        this.body = body;
    }
}
