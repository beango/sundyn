package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.QueueEmployeereport;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangding
 * @since 2018-09-21
 */
public interface QueueEmployeereportDao extends MainDao<QueueEmployeereport> {

    List<QueueEmployeereport> report_queueemployee(Page<QueueEmployeereport> page, @Param("ew") Wrapper<QueueEmployeereport> wrapper);
}
