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
 * @since 2019-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_productdetail")
public class OrderProductdetail extends Model<OrderProductdetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer orderid;
    private Integer productid;
    private Integer num;
    private BigDecimal prdprice;
    private BigDecimal rate;
    private BigDecimal realprice;


    public static final String ID = "id";

    public static final String ORDERID = "orderid";

    public static final String PRODUCTID = "productid";

    public static final String NUM = "num";

    public static final String PRICE = "price";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
