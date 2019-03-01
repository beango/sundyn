package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.SysQueuecounter;
import com.sundyn.entity.SysQueueserial;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-09-11
 */
public interface ISysQueuecounterService extends IService<SysQueuecounter> {
    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);
    Page<SysQueuecounter> selectListEx(Page<SysQueuecounter> page, @Param("ew") Wrapper<SysQueuecounter> wrapper);
}
