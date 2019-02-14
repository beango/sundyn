package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.AppriesPowerfunc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-08-22
 */
public interface IAppriesPowerfuncService extends IServiceBase<AppriesPowerfunc> {
    List<AppriesPowerfunc> selectListEx(@Param("ew") Wrapper<AppriesPowerfunc> wrapper);

    boolean updateAllFunc(String oldCold, String newCold);
}
