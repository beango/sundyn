package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sundyn.entity.AppriesMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oKong
 * @since 2018-08-22
 */
public interface AppriesMenuDao extends BaseMapper<AppriesMenu> {
    @Select("selectByIdEx")
    HashMap selectByIdEx(Integer id);

    @Select("updateAllFuncCode")
    void updateAllFuncCode(@Param("oldCold") String oldCold,@Param("newCold")  String newCold);
}
