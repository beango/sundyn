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
 * @since 2018-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("appries_func")
public class AppriesFunc extends Model<AppriesFunc> {

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("funcName")
    private String funcName;
    @TableField("funcCode")
    private String funcCode;
    @TableField("parentId")
    private Integer parentId;
    @TableField(exist = false)
    private String parentName;
    @TableField("orderId")
    private Integer orderId;


    public static final String ID = "id";

    public static final String FUNCNAME = "funcName";

    public static final String FUNCCODE = "funcCode";

    public static final String PARENTID = "parentId";

    public static final String PARENTNAME = "parentName";

    public static final String ORDERID = "orderId";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
