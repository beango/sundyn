package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.sundyn.entity.AppriesManagerpower;
import com.sundyn.entity.AppriesPowerfunc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author oKong
 * @since 2018-08-23
 */
public interface IAppriesManagerpowerService extends IServiceBase<AppriesManagerpower> {
    List<AppriesManagerpower> selectListEx(@Param("ew") Wrapper<AppriesManagerpower> wrapper);
}
