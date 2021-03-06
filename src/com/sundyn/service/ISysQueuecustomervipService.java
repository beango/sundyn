package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.SysQueuecustomervip;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-09-11
 */
public interface ISysQueuecustomervipService extends IService<SysQueuecustomervip> {
    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);
}
