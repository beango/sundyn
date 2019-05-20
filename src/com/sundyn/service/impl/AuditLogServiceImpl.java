package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.AuditLogDao;
import com.sundyn.entity.AuditLog;
import com.sundyn.entity.SysQueuehall;
import com.sundyn.service.IAuditLogService;
import com.sundyn.util.impl.util;
import com.xuan.xutils.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangding
 * @since 2019-03-06
 */
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogDao, AuditLog> implements IAuditLogService {
    @Override
    public boolean insert(AuditLog var1){
        var1.setCheckdigit(genCheckDigit(var1));
        return baseMapper.insert(var1)>0;
    }

    private String genCheckDigit(AuditLog model){
        StringBuilder digitStr = new StringBuilder();

        digitStr.append(model.getIpadd());
        digitStr.append("|");

        digitStr.append(model.getLogrst());
        digitStr.append("|");

        digitStr.append(model.getLogtype());
        digitStr.append("|");

        digitStr.append(DateUtils.date2String(model.getCtime(),"yyyy-MM-dd HH:mm:ss"));
        digitStr.append("|");

        return (util.md5(digitStr.toString()));
    }

    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return page.setRecords(baseMapper.querypagemap(page, wrapper));
    }

    @Override
    public List<Map> querymap(Wrapper wrapper) {
        return baseMapper.querymap(wrapper);
    }

    @Override
    public List<Map> securitylogrpt(Wrapper<Map> ew) {
        return baseMapper.securitylogrpt(ew);
    }

    @Override
    public List<Map> auditlogrpt(Wrapper<Map> ew) {
        return baseMapper.auditlogrpt(ew);
    }

    @Override
    public List<Map> syslogrpt(Wrapper<Map> ew) {
        return baseMapper.syslogrpt(ew);
    }
}
