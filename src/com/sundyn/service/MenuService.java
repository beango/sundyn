package com.sundyn.service;

import com.sundyn.dao.AppriesMenuMapper;
import com.sundyn.dao.SuperDao;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MenuService extends SuperDao implements AppriesMenuMapper {
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

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(AppriesMenu record) {
        return 0;
    }

    @Override
    public int insertSelective(AppriesMenu record) {
        return 0;
    }

    @Override
    public AppriesMenu selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<AppriesMenu> selectList() {
        SqlSession session = MybatisUtil.currentSession();
        AppriesMenuMapper mapper = session.getMapper(AppriesMenuMapper.class);

        List<AppriesMenu> list = mapper.selectList();

        return list;
    }

    @Override
    public int updateByPrimaryKeySelective(AppriesMenu record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(AppriesMenu record) {
        return 0;
    }

    public List<AppriesMenu> selectChildList(Integer pid) {
        SqlSession session = MybatisUtil.currentSession();
        AppriesMenuMapper mapper = session.getMapper(AppriesMenuMapper.class);

        List<AppriesMenu> list = mapper.selectChildList(pid);

        return list;
    }
}
