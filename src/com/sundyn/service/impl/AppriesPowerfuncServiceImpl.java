package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.AppriesPowerfuncDao;
import com.sundyn.entity.AppriesPowerfunc;
import com.sundyn.service.IAppriesPowerfuncService;
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
 * @since 2018-08-22
 */
@Service
public class AppriesPowerfuncServiceImpl extends ServiceBaseImpl<AppriesPowerfuncDao, AppriesPowerfunc> implements IAppriesPowerfuncService {
    public AppriesPowerfuncServiceImpl(){
        FILTERDEPT = false;
    }

    public List<AppriesPowerfunc> selectListEx(@Param("ew") Wrapper<AppriesPowerfunc> wrapper){
        return baseMapper.selectListEx(wrapper);
    }

    @Override
    public boolean updateAllFunc(String oldCold, String newCold) {
        return baseMapper.updateAllFunc(oldCold, newCold);
    }

    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return null;
    }
}
