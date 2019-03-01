package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.SysProxyorg;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangding
 * @since 2019-01-18
 */
public interface ISysProxyorgService extends IService<SysProxyorg> {
    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);
}
