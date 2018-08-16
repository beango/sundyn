package com.sundyn.dao;

import com.sundyn.entity.AppriesMenu;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SuperDao
{
    JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }
    
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
