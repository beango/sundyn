package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.AppriesMenu;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-08-22
 */
public interface IAppriesMenuService extends IService<AppriesMenu> {
    HashMap selectByIdEx(Integer id);
    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);
    void updateAllFuncCode(String oldFunc, String newFunc);
}
