package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.QueueDetail;
import com.sundyn.entity.SysQueuecounter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oKong
 * @since 2018-09-13
 */
public interface QueueDetailDao extends MainDao<QueueDetail> {
    public void ProcQueueTicket(Map<String, Object> paramMap);

    public void ProcQueueEmployee(Map<String, Object> paramMap);

    public List<QueueDetail> query_querydetail(Page<QueueDetail> page, @Param("ew") Wrapper<QueueDetail> wrapper);
}
