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
 * 实时预警表
 * </p>
 *
 * @author huangding
 * @since 2019-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("warn_ontimedetail")
public class WarnOntimedetail extends Model<WarnOntimedetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 预警类型
     */
    private Integer type;
    private Integer objid;
    private String ywlsh;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String describe;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 是否已读
     */
    private Boolean isread;
    private String notifyuser;


    public static final String ID = "id";

    public static final String TYPE = "type";

    public static final String OBJID = "objid";

    public static final String YWLSH = "ywlsh";

    public static final String TITLE = "title";

    public static final String DESCRIBE = "describe";

    public static final String CTIME = "ctime";

    public static final String ISREAD = "isread";

    public static final String NOTIFYUSER = "notifyuser";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
