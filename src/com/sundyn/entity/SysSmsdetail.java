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
 * @since 2019-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_smsdetail")
public class SysSmsdetail extends Model<SysSmsdetail> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("smId")
    private Integer smId;
    private String mobile;
    private String content;
    private Integer deptid;
    private String deptname;
    private String ywlsh;
    private Integer type;
    private Date sendtime;
    private Integer sendresult;
    private String rptdesc;
    private Date rpttime;
    private Date ctime;
    private Integer cuser;
    private String senddesc;

    public static final String ID = "id";

    public static final String SMID = "smId";

    public static final String MOBILE = "mobile";

    public static final String CONTENT = "content";

    public static final String YWLSH = "ywlsh";

    public static final String TYPE = "type";

    public static final String SENDTIME = "sendtime";

    public static final String SENDRESULT = "sendresult";

    public static final String RPTDESC = "rptdesc";

    public static final String RPTTIME = "rpttime";

    public static final String CTIME = "ctime";

    public static final String CUSER = "cuser";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
