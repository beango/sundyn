package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.AppriesMenuDao;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.service.IAppriesMenuService;
import com.sundyn.util.EhCacheHelper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
public class AppriesMenuServiceImpl extends ServiceImpl<AppriesMenuDao, AppriesMenu> implements IAppriesMenuService {
    public HashMap selectByIdEx(Integer id){
        return baseMapper.selectByIdEx(id);
    }

    @Override
    public void updateAllFuncCode(String oldFunc, String newFunc) {
        baseMapper.updateAllFuncCode(oldFunc, newFunc);
    }

    @Override
    public Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper) {
        return null;
    }

    public List<AppriesMenu> getAllCache(){
        return getAllCache(false);
    }

    public List<AppriesMenu> getAllCache(boolean isClear){
        Object allData = EhCacheHelper.getCache(EhCacheHelper.CacheKeyEnum.ALLMENUS);
        if (null == allData || isClear){
            System.out.println("ALLMENUS 缓存为空，获取并添加缓存,是否清空：" + isClear);
            allData = selectList(null);
            EhCacheHelper.putCache(EhCacheHelper.CacheKeyEnum.ALLMENUS, allData);
        }
        return (List<AppriesMenu>)allData;
    }
}
