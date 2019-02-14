package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.SysQueuecustomervip;
import com.sundyn.dao.SysQueuecustomervipDao;
import com.sundyn.service.ISysQueuecustomervipService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oKong
 * @since 2018-09-11
 */
@Service
public class SysQueuecustomervipServiceImpl extends ServiceImpl<SysQueuecustomervipDao, SysQueuecustomervip> implements ISysQueuecustomervipService {
    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return null;
    }
}
