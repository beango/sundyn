package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.sundyn.entity.AppriesManagerpower;
import com.sundyn.entity.OrderProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangding
 * @since 2019-06-19
 */
public interface OrderProductDao extends MainDao<OrderProduct> {

}
