package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.sundyn.vo.ClientVo;
import com.sundyn.vo.PlayListVo;

import java.util.List;

public class ClientService extends SuperDao {
    public List getNewest() {
        String sql = "select top 1 * from appries_client order by clientno desc";
        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean clientAdd(final ClientVo clientVo) {
        final String sql = "insert into appries_client (clientno, clientver, filepath, usecount, ctime, comments )values(?,?,?,?,?,?) ";
        final Object[] args = { clientVo.getClientNo(), clientVo.getClientVer(), clientVo.getFilePath(), clientVo.getUseCount(), clientVo.getCtime(), clientVo.getComments() };
        try {
            final int num = this.getJdbcTemplate().update(sql, args);
            return num > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
