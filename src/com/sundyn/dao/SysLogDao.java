package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.SysLog;
import com.sundyn.entity.SysQueueserial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oKong
 * @since 2018-09-14
 */
public interface SysLogDao extends MainDao<SysLog> {
    @Select("selectListEx")
    List<Map> selectListEx(Page<Map> page, @Param("ew") Wrapper<Map> wrapper);
}
