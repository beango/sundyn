package com.sundyn.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.sundyn.entity.AppriesManagerpower;
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
 * @since 2018-08-23
 */
public interface AppriesManagerpowerDao extends BaseMapper<AppriesManagerpower> {
    @Select("selectListEx")
    List<AppriesManagerpower> selectListEx(@Param("ew") Wrapper<AppriesManagerpower> wrapper);
}
