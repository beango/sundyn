package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.SysQueueserial;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-09-07
 */
public interface ISysQueueserialService extends IService<SysQueueserial> {
    Page<SysQueueserial> selectListEx(Page<SysQueueserial> page, @Param("ew") Wrapper<SysQueueserial> wrapper);

    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);
}
