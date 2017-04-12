package com.sundyn.service;

import com.sundyn.dao.*;

public class FormlistService extends SuperDao
{
    public boolean reset() {
        final String sql = "update appries_formlist set flag=0 ";
        try {
            final int num = this.getJdbcTemplate().update(sql);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
