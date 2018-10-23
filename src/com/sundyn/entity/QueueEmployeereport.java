package com.sundyn.entity;

import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2018-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("queue_employeereport")
public class QueueEmployeereport extends Model<QueueEmployeereport> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String hallno;
    private String counterno;
    private String eno;
    private String ename;
    private String action;
    private Date starttime;
    private Date endtime;
    private Integer times;
    private Date ctime;
    @TableField(exist = false)
    private String hallname;
    @TableField(exist = false)
    private String countername;

    public static final String ID = "id";

    public static final String HALLNO = "hallno";

    public static final String COUNTERNO = "counterno";

    public static final String ENO = "eno";

    public static final String ENAME = "ename";

    public static final String ACTION = "action";

    public static final String STARTTIME = "starttime";

    public static final String ENDTIME = "endtime";

    public static final String TIMES = "times";

    public static final String CTIME = "ctime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
