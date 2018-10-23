package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.dao.QueueDetailDao;
import com.sundyn.entity.QueueDetail;
import com.sundyn.service.IQueueDetailService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oKong
 * @since 2018-09-13
 */
@Service
public class QueueDetailServiceImpl extends ServiceBaseImpl<QueueDetailDao, QueueDetail> implements IQueueDetailService {

    @Override
    public void ProcQueueTicket(Map<String, Object> paramMap) {
        baseMapper.ProcQueueTicket(paramMap);
    }

    @Override
    public void ProcQueueEmployee(Map<String, Object> paramMap) {
        baseMapper.ProcQueueEmployee(paramMap);
    }

    @Override
    public Page<QueueDetail> query_querydetail(Page<QueueDetail> page, @Param("ew") Wrapper<QueueDetail> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return page.setRecords(baseMapper.query_querydetail(page, wrapper));
    }
}
