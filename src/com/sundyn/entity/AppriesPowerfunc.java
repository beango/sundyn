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

/**
 * <p>
 * 
 * </p>
 *
 * @author oKong
 * @since 2018-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("appries_powerfunc")
public class AppriesPowerfunc extends Model<AppriesPowerfunc> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPowerid() {
        return powerid;
    }

    public void setPowerid(Integer powerid) {
        this.powerid = powerid;
    }

    public Integer getFuncid() {
        return funcid;
    }

    public void setFuncid(Integer funcid) {
        this.funcid = funcid;
    }

    @TableField("funcid")
    private Integer funcid;
    private Integer powerid;


    public static final String ID = "id";

    public static final String FUNCCODE = "funcCode";

    public static final String POWERID = "powerid";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
