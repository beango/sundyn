package com.sundyn.service;

import com.sundyn.entity.SysDictinfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangding
 * @since 2018-10-24
 */
public interface ISysDictinfoService extends IService<SysDictinfo> {

    String getDictInfo(String security_para, String security_pwdcheck);

    List<SysDictinfo> getAllCache();
    List<SysDictinfo> getAllCache(boolean isClear);
}
