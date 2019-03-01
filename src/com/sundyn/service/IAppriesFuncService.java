package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.AppriesFunc;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-08-21
 */
public interface IAppriesFuncService extends IService<AppriesFunc> {
    Page<AppriesFunc> selectListExt(Page<AppriesFunc> page);

    List<Map> selectListExt(Wrapper<Map> wrapper);

    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);

    AppriesFunc selectById(Integer id);

    boolean updateFuncAndPowerfun(AppriesFunc func) throws Exception;

    boolean deleteByIdAndPowerfunc(int id) throws Exception;
}
