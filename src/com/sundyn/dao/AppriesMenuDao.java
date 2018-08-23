package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sundyn.entity.AppriesMenu;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oKong
 * @since 2018-08-22
 */
public interface AppriesMenuDao extends BaseMapper<AppriesMenu> {
    @Select("selectById")
    AppriesMenu selectById(Integer id);
}
