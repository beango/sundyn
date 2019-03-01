package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.AppriesPowerfunc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-08-22
 */
public interface IAppriesPowerfuncService extends IService<AppriesPowerfunc> {
    List<AppriesPowerfunc> selectListEx(@Param("ew") Wrapper<AppriesPowerfunc> wrapper);

    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);

    boolean updateAllFunc(String oldCold, String newCold);
}
