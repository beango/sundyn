package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.dao.AppriesMenuDao;
import com.sundyn.service.IAppriesMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class AppriesMenuServiceImpl extends ServiceImpl<AppriesMenuDao, AppriesMenu> implements IAppriesMenuService {
    public AppriesMenu selectById(Integer id){
        return baseMapper.selectById(id);
    }

    @Override
    public void updateAllFuncCode(String oldFunc, String newFunc) {
        baseMapper.updateAllFuncCode(oldFunc, newFunc);
    }

    @Override
    public AppriesMenu selectByIdEx(int id) {
        return baseMapper.selectByIdEx(id);
    }

    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return null;
    }
}
