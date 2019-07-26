package com.sundyn.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sundyn.dao.OrderDetailDao;
import com.sundyn.entity.OrderDetail;
import com.sundyn.entity.OrderLicensedetail;
import com.sundyn.entity.OrderProductdetail;
import com.sundyn.service.IOrderDetailService;
import com.sundyn.service.IOrderProductdetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangding
 * @since 2019-06-21
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailDao, OrderDetail> implements IOrderDetailService {

}
