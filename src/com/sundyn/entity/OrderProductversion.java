package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@TableName("order_productversion")
public class OrderProductversion extends Model<OrderProductversion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer productid;
    private String apkver;
    private String filename;
    private String filepath;
    private String comment;
    private Date ctime;


    public static final String ID = "id";

    public static final String PRODUCTID = "productid";

    public static final String APKVER = "apkver";

    public static final String FILENAME = "filename";

    public static final String FILEPATH = "filepath";

    public static final String COMMENT = "comment";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
