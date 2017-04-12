package com.sundyn.service;

import java.util.List;

import com.sundyn.dao.SuperDao;

public class MenuService extends SuperDao{
	public List GetAll() {
        String sql = "select * from appries_menu ";
       
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
