package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MainDao<T> extends BaseMapper<T> {
    @Select("querypagemap")
    List<Map> querypagemap(@Param("ew") Wrapper<Map> wrapper);

    @Select("querypagemap")
    List<Map> querypagemap(Page<Map> page, @Param("ew") Wrapper<Map> wrapper);

    @Select("querymap")
    List<Map> querymap(@Param("ew") Wrapper<Map> wrapper);
}
