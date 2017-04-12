package com.sundyn.utils;

import java.io.*;

public class HQLSql implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String hql;
    
    public HQLSql() {
        this.hql = " ";
    }
    
    public String getHql() {
        return this.hql;
    }
    
    public void setHql(final String hql) {
        System.out.println(hql);
        try {
            this.hql = hql;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
