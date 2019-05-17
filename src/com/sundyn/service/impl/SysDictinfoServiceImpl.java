package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.SysDictinfoDao;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.entity.SysDictinfo;
import com.sundyn.service.ISysDictinfoService;
import com.sundyn.util.EhCacheHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangding
 * @since 2018-10-24
 */
@Service
public class SysDictinfoServiceImpl extends ServiceImpl<SysDictinfoDao, SysDictinfo> implements ISysDictinfoService {
    @Override
    public String getDictInfo(String group, String key){
        List<SysDictinfo> dictlist = baseMapper.selectList(new EntityWrapper<SysDictinfo>().where("dictgroup={0} and dictkey={1}", group, key));
        if (dictlist == null || dictlist.size()==0)
            return null;
        return dictlist.get(0).getDictvalue();
    }

    public List<SysDictinfo> getAllCache(){
        return getAllCache(false);
    }

    public List<SysDictinfo> getAllCache(boolean isClear){
        Object allData = EhCacheHelper.getCache(EhCacheHelper.CacheKeyEnum.ALLDICTINFO);
        if (null == allData || isClear){
            System.out.println("ALLDICTINFO 缓存为空，获取并添加缓存,是否清空：" + isClear);
            allData = selectList(null);
            EhCacheHelper.putCache(EhCacheHelper.CacheKeyEnum.ALLDICTINFO, allData);
        }
        return (List<SysDictinfo>)allData;
    }
}
