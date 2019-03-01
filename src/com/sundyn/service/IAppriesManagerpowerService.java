package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.AppriesManagerpower;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-08-23
 */
public interface IAppriesManagerpowerService extends IService<AppriesManagerpower> {
    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);

    List<AppriesManagerpower> selectListEx(@Param("ew") Wrapper<AppriesManagerpower> wrapper);
}
