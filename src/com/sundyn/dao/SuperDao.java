package com.sundyn.dao;

import org.springframework.jdbc.core.*;

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
