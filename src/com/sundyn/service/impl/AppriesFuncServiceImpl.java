package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.AppriesFuncDao;
import com.sundyn.entity.AppriesFunc;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.entity.AppriesPowerfunc;
import com.sundyn.service.IAppriesFuncService;
import com.sundyn.service.IAppriesMenuService;
import com.sundyn.service.IAppriesPowerfuncService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

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
    @Resource
    private IAppriesPowerfuncService powerFuncService;
    @Resource
    private IAppriesMenuService appriesMenuService;

    public Page<AppriesFunc> selectListExt(Page<AppriesFunc> page) {
        page.setRecords(baseMapper.selectListExt(page));
        return page;
    }

    public AppriesFunc selectById(Integer id){
        return baseMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean updateFuncAndPowerfun(AppriesFunc func) throws Exception {
        AppriesFunc old = selectById(func.getId());
        boolean isupdate = powerFuncService.updateAllFunc(old.getFuncCode(), func.getFuncCode());
        appriesMenuService.updateAllFuncCode(old.getFuncCode(), func.getFuncCode());
        isupdate = updateById(func);
        return false;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean deleteByIdAndPowerfunc(int id) throws Exception {
        AppriesFunc af = super.selectById(id);
        int menuFuncCount = appriesMenuService.selectCount(new EntityWrapper<AppriesMenu>().where("funcCode={0}", af.getFuncCode()));
        if (menuFuncCount>0)
            return false;
        EntityWrapper<AppriesFunc> ew=new EntityWrapper<>();
        ew.where("parentId = {0}",id);
        boolean isdel = delete(ew); //删除所有子级
        if(!isdel)
            throw new Exception("has exception---2!!!");
        super.deleteById(id);//删除自己

        EntityWrapper<AppriesPowerfunc> ew2=new EntityWrapper<>();
        ew2.where("funcCode = {0}", af.getFuncCode());
        isdel = powerFuncService.delete(ew2); //删除角色分配表中所有该权限码的记录

        return true;
    }

    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return null;
    }
}
