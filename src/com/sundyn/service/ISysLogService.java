package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-09-14
 */
public interface ISysLogService extends IService<SysLog> {
    Page<Map> selectListEx(Page<Map> page, @Param("ew") Wrapper<Map> wrapper);

    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);
}
