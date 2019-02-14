package com.sundyn.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

public interface IServiceBase<T> extends IService<T> {
    Page<Map> querypagemap(Page<Map> page, Wrapper<Map> wrapper);
}
