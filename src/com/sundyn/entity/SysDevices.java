package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2019-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_devices")
public class SysDevices extends Model<SysDevices> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String devicemac;
    private String devicename;
    private String devicediscrible;
    private String devicestatus;
    private Date lastonlinetime;
    private Date ctime;
    private String hallno;
    private String deviceip;

    public static final String ID = "id";

    public static final String DEVICEMAC = "devicemac";

    public static final String DEVICENAME = "devicename";

    public static final String DEVICEDISCRIBLE = "devicediscrible";

    public static final String DEVICESTATUS = "devicestatus";

    public static final String LASTONLINETIME = "lastonlinetime";

    public static final String CTIME = "ctime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @TableField(exist = false)
    public boolean FILTERDEPT = false;
}
