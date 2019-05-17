package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.dao.SysProxyDao;
import com.sundyn.entity.SysProxy;
import com.sundyn.service.ISysProxyService;
import com.sundyn.util.CookieUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangding
 * @since 2018-09-14
 */
@Service
public class SysProxyServiceImpl extends ServiceBaseImpl<SysProxyDao, SysProxy> implements ISysProxyService {
    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return page.setRecords(baseMapper.querypagemap(page, wrapper));
    }
}
