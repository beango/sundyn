package com.sundyn.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dictinfo")
public class SysDictinfo extends Model<SysDictinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 字典名
     */
    private String dictname;
    private String dictkey;
    /**
     * 字典值
     */
    private String dictvalue;
    /**
     * 说明
     */
    private String note;
    private Boolean isenable;
    private String dictgroup;

    public static final String ID = "id";

    public static final String DICTNAME = "dictname";

    public static final String DICTKEY = "dictkey";

    public static final String DICTVALUE = "dictvalue";

    public static final String NOTE = "note";

    public static final String ISENABLE = "isenable";

    public static final String DICTGROUP = "dictgroup";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
