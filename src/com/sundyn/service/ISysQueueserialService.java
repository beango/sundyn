package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.SysQueueserial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-09-07
 */
public interface ISysQueueserialService extends IServiceBase<SysQueueserial> {
    Page<SysQueueserial> selectListEx(Page<SysQueueserial> page, @Param("ew") Wrapper<SysQueueserial> wrapper);
}
