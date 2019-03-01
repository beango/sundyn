package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.AppriesFunc;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oKong
 * @since 2018-08-21
 */
public interface AppriesFuncDao extends BaseMapper<AppriesFunc> {
    @Select("selectListExt")
    List<AppriesFunc> selectListExt(Page<AppriesFunc> page);

    @Select("selectListExt")
    List<Map> selectListExt(Wrapper<Map> page);

    @Select("selectById")
    AppriesFunc selectById(Integer id);
}
