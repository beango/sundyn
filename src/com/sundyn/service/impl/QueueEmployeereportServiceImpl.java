package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.QueueEmployeereportDao;
import com.sundyn.entity.QueueDetail;
import com.sundyn.entity.QueueEmployeereport;
import com.sundyn.service.IQueueEmployeereportService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangding
 * @since 2018-09-21
 */
@Service
public class QueueEmployeereportServiceImpl extends ServiceBaseImpl<QueueEmployeereportDao, QueueEmployeereport> implements IQueueEmployeereportService {

    @Override
    public Page<QueueEmployeereport> report_queueemployee(Page<QueueEmployeereport> page, @Param("ew") Wrapper<QueueEmployeereport> wrapper) {
        return page.setRecords(baseMapper.report_queueemployee(page, wrapper));
    }
}
