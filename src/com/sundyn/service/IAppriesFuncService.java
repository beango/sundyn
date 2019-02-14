package com.sundyn.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.AppriesFunc;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-08-21
 */
public interface IAppriesFuncService extends IServiceBase<AppriesFunc> {
    Page<AppriesFunc> selectListExt(Page<AppriesFunc> page);

    AppriesFunc selectById(Integer id);

    boolean updateFuncAndPowerfun(AppriesFunc func) throws Exception;

    boolean deleteByIdAndPowerfunc(int id) throws Exception;
}
