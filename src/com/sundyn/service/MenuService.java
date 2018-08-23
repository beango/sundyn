package com.sundyn.service;

import com.sundyn.dao.SuperDao;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MenuService extends SuperDao {
	public List GetAll() {
        String sql = "select id,menuName as text ,nav url ,parentId, iconCls from appries_menu where isshow=1 order by menuorder asc";

        try {
            return this.getJdbcTemplate().queryForList(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
