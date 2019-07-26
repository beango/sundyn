package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.AppriesFunc;
import com.sundyn.entity.SysQueueserial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oKong
 * @since 2018-09-07
 */
public interface SysQueueserialDao extends MainDao<SysQueueserial> {
    @Select("selectListEx")
    List<SysQueueserial> selectListEx(Page<SysQueueserial> page, @Param("ew") Wrapper<SysQueueserial> wrapper);
}
