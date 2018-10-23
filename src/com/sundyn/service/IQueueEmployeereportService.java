package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.QueueDetail;
import com.sundyn.entity.QueueEmployeereport;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangding
 * @since 2018-09-21
 */
public interface IQueueEmployeereportService extends IService<QueueEmployeereport> {

    Page<QueueEmployeereport> report_queueemployee(Page<QueueEmployeereport> queueDetailPage, @Param("ew") Wrapper<QueueEmployeereport> tickettime_desc);
}
