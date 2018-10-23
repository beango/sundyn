package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.SysQueuecounter;
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
 * @since 2018-09-11
 */
public interface SysQueuecounterDao extends BaseMapper<SysQueuecounter> {
    @Select("selectListEx")
    List<SysQueuecounter> selectListEx(Page<SysQueuecounter> page, @Param("ew") Wrapper<SysQueuecounter> wrapper);
}
