package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.SysProxyorgDao;
import com.sundyn.entity.SysProxyorg;
import com.sundyn.service.ISysProxyorgService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangding
 * @since 2019-01-18
 */
@Service
public class SysProxyorgServiceImpl extends ServiceImpl<SysProxyorgDao, SysProxyorg> implements ISysProxyorgService {
    /*public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return page.setRecords(baseMapper.querypagemap(page, wrapper));
    }*/
}
