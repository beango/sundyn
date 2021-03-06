package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.SysSmsdetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangding
 * @since 2019-05-30
 */
public interface SysSmsdetailDao extends BaseMapper<SysSmsdetail> {
    @Select("selectListEx")
    List<Map> selectListEx(Page<Map> page, @Param("ew") Wrapper<Map> wrapper);

    @Select("querypagemap")
    List<Map> querypagemap(Page<Map> page, @Param("ew") Wrapper<Map> wrapper);
}
