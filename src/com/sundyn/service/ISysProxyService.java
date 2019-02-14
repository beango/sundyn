package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.SysProxy;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangding
 * @since 2018-09-14
 */
public interface ISysProxyService extends IService<SysProxy> {
    Page<Map> querypagemap(Page<Map> mapPage, Wrapper<Map> id_desc);
}
