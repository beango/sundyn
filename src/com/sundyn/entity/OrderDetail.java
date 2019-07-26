package com.sundyn.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
 * @since 2019-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_detail")
public class OrderDetail extends Model<OrderDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String orderno;
    private Integer managerid;
    private BigDecimal totalfee;
    private Integer ispay;
    private float payfee;
    private Date paytime;
    private Integer status;
    private String comment;
    private Integer audituser;
    private Date audittime;
    private String auditdesc;
    private Integer canceluser;
    private Date canceltime;
    private String canceldesc;
    private Integer closeuser;
    private Date closetime;
    private String closedesc;
    private Date ctime;
    private Integer cuser;


    public static final String ID = "id";

    public static final String ORDERNO = "orderno";

    public static final String MANAGERID = "managerid";

    public static final String TOTALFEE = "totalfee";

    public static final String ISPAY = "ispay";

    public static final String PAYTIME = "paytime";

    public static final String STATUS = "status";

    public static final String COMMENT = "comment";

    public static final String AUDITUSER = "audituser";

    public static final String AUDITTIME = "audittime";

    public static final String AUDITDESC = "auditdesc";

    public static final String CANCELUSER = "canceluser";

    public static final String CANCELTIME = "canceltime";

    public static final String CANCELDESC = "canceldesc";

    public static final String CLOSEUSER = "closeuser";

    public static final String CLOSETIME = "closetime";

    public static final String CLOSEDESC = "closedesc";

    public static final String CTIME = "ctime";

    public static final String CUSER = "cuser";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
