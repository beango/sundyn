package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.SysJxdataDao;
import com.sundyn.entity.SysJxdata;
import com.sundyn.service.ISysJxdataService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangding
 * @since 2019-02-25
 */
@Service
public class SysJxdataServiceImpl extends ServiceImpl<SysJxdataDao, SysJxdata> implements ISysJxdataService {
    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return page.setRecords(baseMapper.querypagemap(page, wrapper));
    }

    @Override
    public List<Map> querymap(Wrapper wrapper) {
        return baseMapper.querymap(wrapper);
    }
}
