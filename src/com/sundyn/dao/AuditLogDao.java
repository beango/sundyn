package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.AuditLog;
import com.sundyn.entity.SysQueuecounter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangding
 * @since 2019-03-06
 */
public interface AuditLogDao extends MainDao<AuditLog> {
    @Select("querypagemap")
    List<Map> querypagemap(Page<Map> page, @Param("ew") Wrapper<Map> wrapper);

    @Select("securitylogrpt")
    public List<Map> securitylogrpt(@Param("ew") Wrapper<Map> ew);

    @Select("auditlogrpt")
    public List<Map> auditlogrpt(@Param("ew") Wrapper<Map> ew);

    @Select("syslogrpt")
    public List<Map> syslogrpt(@Param("ew") Wrapper<Map> ew);
}
