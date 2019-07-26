package com.sundyn.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
 * @since 2019-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_licensedetail")
public class OrderLicensedetail extends Model<OrderLicensedetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer type;
    private String batchid;
    private Integer num;
    private Integer usednums;
    private Integer managerid;
    private Integer orderid;
    private Integer productid;
    private String devicemac;
    private Date ctime;
    private Date usetime;
    private Integer totaldowntimes;
    private Integer downedtimes;
    private Date lstdowntime;


    public static final String ID = "id";

    public static final String MANAGERID = "managerid";

    public static final String ORDERID = "orderid";

    public static final String PRODUCTID = "productid";

    public static final String DEVICEMAC = "devicemac";

    public static final String CTIME = "ctime";

    public static final String USETIME = "usetime";

    public static final String TOTALDOWNTIMES = "totaldowntimes";

    public static final String DOWNEDTIMES = "downedtimes";

    public static final String LSTDOWNTIME = "lstdowntime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
