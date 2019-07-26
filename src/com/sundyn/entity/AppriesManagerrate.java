package com.sundyn.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangding
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("appries_managerrate")
public class AppriesManagerrate extends Model<AppriesManagerrate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer productid;
    private Integer managerid;
    private Float rate;


    public static final String ID = "id";

    public static final String PRODUCTID = "productid";

    public static final String MANAGERID = "managerid";

    public static final String RATE = "rate";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
