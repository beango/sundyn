package com.sundyn.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.AppriesFuncDao;
import com.sundyn.entity.AppriesFunc;
import com.sundyn.service.IAppriesFuncService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author oKong
 * @since 2018-08-21
 */
@Service
public class AppriesFuncServiceImpl extends ServiceImpl<AppriesFuncDao, AppriesFunc> implements IAppriesFuncService {
    public Page<AppriesFunc> selectListExt(Page<AppriesFunc> page) {
        page.setRecords(baseMapper.selectListExt(page));
        return page;
    }

    public AppriesFunc selectById(Integer id){
        return baseMapper.selectById(id);
    }
}
