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
 * @since 2019-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("audit_lock")
public class AuditLock extends Model<AuditLock> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String locktype;
    private Date locktime;
    private Date unlocktime;
    private Integer status;
    private String lockdesc;
    private Date ctime;
    private Date realunlocktime;
    private Integer unlockuser;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String LOCKTYPE = "locktype";

    public static final String LOCKTIME = "locktime";

    public static final String UNLOCKTIME = "unlocktime";

    public static final String STATUS = "status";

    public static final String LOCKDESC = "lockdesc";

    public static final String CTIME = "ctime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
