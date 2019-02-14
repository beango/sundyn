package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.service.DeptService;
import com.sundyn.util.CookieUtils;
import com.xuan.xutils.cache.Cache;
import com.xuan.xutils.cache.CacheManager;
import com.xuan.xutils.cache.provider.SimpleCache;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ServiceBaseImpl<dao extends BaseMapper<T>, T> extends ServiceImpl<dao, T> {
    public boolean isFILTERDEPT() {
        return FILTERDEPT;
    }

    public void setFILTERDEPT(boolean FILTERDEPT) {
        this.FILTERDEPT = FILTERDEPT;
    }

    private boolean FILTERDEPT = true;
    @Resource
    private CacheManager dCacheManager;
    @Resource
    private DeptService deptService;

    protected String USERDATA_DEPTIDS() {
        if(!FILTERDEPT)
            return null;
        HttpSession session = ServletActionContext.getRequest().getSession();
        Map user = (Map) session.getAttribute("manager");
        if(user == null){
            HttpServletRequest request = ServletActionContext.getRequest();
            CookieUtils cookieUtils = new CookieUtils();
            if (cookieUtils.getCookie(request)){
                HttpSession csession = request.getSession();
                user = (Map) csession.getAttribute("manager");
            }
        }
        if (null != user){
            return (String)user.get("deptids");
        }
        return "0";
    }

    @Override
    public boolean insert(T entity) {
        return super.insert(entity);
    }

    @Override
    public boolean insertAllColumn(T entity) {
        return super.insertAllColumn(entity);
    }

    @Override
    public boolean insertBatch(List<T> entityList) {
        return super.insertBatch(entityList);
    }

    @Override
    public boolean insertBatch(List<T> entityList, int batchSize) {
        return super.insertBatch(entityList, batchSize);
    }

    @Override
    public boolean insertOrUpdate(T entity) {
        return super.insertOrUpdate(entity);
    }

    @Override
    public boolean insertOrUpdateAllColumn(T entity) {
        return super.insertOrUpdateAllColumn(entity);
    }

    @Override
    public boolean insertOrUpdateBatch(List<T> entityList) {
        return super.insertOrUpdateBatch(entityList);
    }

    @Override
    public boolean insertOrUpdateBatch(List<T> entityList, int batchSize) {
        return super.insertOrUpdateBatch(entityList, batchSize);
    }

    @Override
    public boolean insertOrUpdateAllColumnBatch(List<T> entityList) {
        return super.insertOrUpdateAllColumnBatch(entityList);
    }

    @Override
    public boolean insertOrUpdateAllColumnBatch(List<T> entityList, int batchSize) {
        return super.insertOrUpdateAllColumnBatch(entityList, batchSize);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return super.deleteById(id);
    }

    @Override
    public boolean deleteByMap(Map<String, Object> columnMap) {
        return super.deleteByMap(columnMap);
    }

    @Override
    public boolean delete(Wrapper<T> wrapper) {
        return super.delete(wrapper);
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return super.deleteBatchIds(idList);
    }

    @Override
    public boolean updateById(T entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean updateAllColumnById(T entity) {
        return super.updateAllColumnById(entity);
    }

    @Override
    public boolean update(T entity, Wrapper<T> wrapper) {
        return super.update(entity, wrapper);
    }

    @Override
    public boolean updateForSet(String setStr, Wrapper<T> wrapper) {
        return super.updateForSet(setStr, wrapper);
    }

    @Override
    public boolean updateBatchById(List<T> entityList) {
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean updateBatchById(List<T> entityList, int batchSize) {
        return super.updateBatchById(entityList, batchSize);
    }

    @Override
    public boolean updateAllColumnBatchById(List<T> entityList) {
        return super.updateAllColumnBatchById(entityList);
    }

    @Override
    public boolean updateAllColumnBatchById(List<T> entityList, int batchSize) {
        return super.updateAllColumnBatchById(entityList, batchSize);
    }

    @Override
    public T selectById(Serializable id) {
        return super.selectById(id);
    }

    @Override
    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        return super.selectBatchIds(idList);
    }

    @Override
    public List<T> selectByMap(Map<String, Object> columnMap) {
        return super.selectByMap(columnMap);
    }

    @Override
    public T selectOne(Wrapper<T> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectOne(wrapper);
    }

    @Override
    public Map<String, Object> selectMap(Wrapper<T> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectMap(wrapper);
    }

    @Override
    public Object selectObj(Wrapper<T> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectObj(wrapper);
    }

    @Override
    public int selectCount(Wrapper<T> wrapper) {
        System.out.println("FILTERDEPT:" + this.FILTERDEPT);
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectCount(wrapper);
    }

    @Override
    public List<T> selectList(Wrapper<T> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectList(wrapper);
    }

    @Override
    public Page<T> selectPage(Page<T> page) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            Wrapper<T> wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectPage(page);
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<T> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectMaps(wrapper);
    }

    @Override
    public List<Object> selectObjs(Wrapper<T> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectObjs(wrapper);
    }

    @Override
    public Page<Map<String, Object>> selectMapsPage(Page page, Wrapper<T> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectMapsPage(page, wrapper);
    }

    @Override
    public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        String filterDept = USERDATA_DEPTIDS();
        if (filterDept !=null && !"".equals(filterDept)){
            if (wrapper == null)
                wrapper = new EntityWrapper<>();
            wrapper = wrapper.in("deptid", filterDept.split(","));
        }
        return super.selectPage(page, wrapper);
    }
}
