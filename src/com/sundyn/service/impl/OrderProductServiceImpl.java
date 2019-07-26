package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.OrderProductDao;
import com.sundyn.entity.OrderProduct;
import com.sundyn.service.IOrderProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangding
 * @since 2019-06-19
 */
@Service
public class OrderProductServiceImpl extends ServiceBaseImpl<OrderProductDao, OrderProduct> implements IOrderProductService {
    public OrderProductServiceImpl(){
        FILTERDEPT = false;
    }
}
