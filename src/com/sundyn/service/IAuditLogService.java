package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.sundyn.entity.AuditLog;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangding
 * @since 2019-03-06
 */
public interface IAuditLogService extends IMainService<AuditLog> {
    List<Map> securitylogrpt(Wrapper<Map> ew);

    List<Map> auditlogrpt(Wrapper<Map> ew);

    List<Map> syslogrpt(Wrapper<Map> ew);
}
