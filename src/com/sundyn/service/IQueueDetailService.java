package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.QueueDetail;
import com.sundyn.entity.SysQueuecounter;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-09-13
 */
public interface IQueueDetailService extends IService<QueueDetail> {
    void ProcQueueTicket(Map<String, Object> paramMap);

    void ProcQueueEmployee(Map<String, Object> paramMap);

    Page<QueueDetail> query_querydetail(Page<QueueDetail> page, @Param("ew") Wrapper<QueueDetail> wrapper);
}
