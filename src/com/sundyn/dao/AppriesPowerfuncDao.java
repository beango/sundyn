package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.sundyn.entity.AppriesPowerfunc;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author oKong
 * @since 2018-08-22
 */
public interface AppriesPowerfuncDao extends MainDao<AppriesPowerfunc> {

    @Select("selectListEx")
    List<AppriesPowerfunc> selectListEx(@Param("ew") Wrapper<AppriesPowerfunc> wrapper);

    @Select("updateAllFunc")
    boolean updateAllFunc(@Param("oldCold") String oldCold, @Param("newCold") String newCold);
}
