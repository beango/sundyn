package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.dao.SysQueuehallDao;
import com.sundyn.entity.SysQueuehall;
import com.sundyn.service.ISysQueuehallService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oKong
 * @since 2018-09-05
 */
@Service
public class SysQueuehallServiceImpl extends ServiceBaseImpl<SysQueuehallDao, SysQueuehall> implements ISysQueuehallService {

    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return null;
    }
}
