package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.AppriesManagerpower;
import com.sundyn.dao.AppriesManagerpowerDao;
import com.sundyn.entity.AppriesPowerfunc;
import com.sundyn.service.IAppriesManagerpowerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oKong
 * @since 2018-08-23
 */
@Service
public class AppriesManagerpowerServiceImpl extends ServiceImpl<AppriesManagerpowerDao, AppriesManagerpower> implements IAppriesManagerpowerService {

    @Override
    public List<AppriesManagerpower> selectListEx(@Param("ew") Wrapper<AppriesManagerpower> wrapper) {
        return baseMapper.selectListEx(wrapper);
    }

    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return null;
    }
}
