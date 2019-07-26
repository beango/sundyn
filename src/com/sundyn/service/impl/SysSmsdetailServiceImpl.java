package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.SysSmsdetailDao;
import com.sundyn.entity.SysSmsdetail;
import com.sundyn.service.ISysSmsdetailService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangding
 * @since 2019-05-30
 */
@Service
public class SysSmsdetailServiceImpl extends ServiceImpl<SysSmsdetailDao, SysSmsdetail> implements ISysSmsdetailService {
    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return page.setRecords(baseMapper.querypagemap(page, wrapper));
    }
}
