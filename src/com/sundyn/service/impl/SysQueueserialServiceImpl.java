package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.dao.SysQueueserialDao;
import com.sundyn.entity.SysQueueserial;
import com.sundyn.service.ISysQueueserialService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oKong
 * @since 2018-09-07
 */
@Service
public class SysQueueserialServiceImpl extends ServiceBaseImpl<SysQueueserialDao, SysQueueserial> implements ISysQueueserialService {
    public Page<SysQueueserial> selectListEx(Page<SysQueueserial> page, @Param("ew") Wrapper<SysQueueserial> wrapper){
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return page.setRecords(baseMapper.selectListEx(page, wrapper));
    }

    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return null;
    }
}
