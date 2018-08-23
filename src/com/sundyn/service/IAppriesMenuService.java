package com.sundyn.service;

import com.sundyn.entity.AppriesMenu;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-08-22
 */
public interface IAppriesMenuService extends IService<AppriesMenu> {
    AppriesMenu selectById(Integer id);
}
