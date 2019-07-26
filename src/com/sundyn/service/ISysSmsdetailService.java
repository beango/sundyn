package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.SysSmsdetail;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangding
 * @since 2019-05-30
 */
public interface ISysSmsdetailService extends IService<SysSmsdetail> {
    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);
}
