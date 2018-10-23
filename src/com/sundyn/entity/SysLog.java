package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
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
 * @author oKong
 * @since 2018-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_log")
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String action;
    private Date actiontime;
    private String actionname;
    private String actionparam;
    private Integer managerid;
    private String ipaddress;
    private String note;
    private String actionurl;
    private String actionmethod;

    public static final String ID = "id";

    public static final String ACTION = "action";

    public static final String ACTIONTIME = "actiontime";

    public static final String MANAGERID = "managerid";

    public static final String IPADDRESS = "ipaddress";

    public static final String NOTE = "note";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
