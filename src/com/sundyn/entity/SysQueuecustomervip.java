package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author oKong
 * @since 2018-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_queuecustomervip")
public class SysQueuecustomervip extends Model<SysQueuecustomervip> {

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVipcardno() {
        return vipcardno;
    }

    public void setVipcardno(String vipcardno) {
        this.vipcardno = vipcardno;
    }

    public String getVipname() {
        return vipname;
    }

    public void setVipname(String vipname) {
        this.vipname = vipname;
    }

    public Integer getVipgrade() {
        return vipgrade;
    }

    public void setVipgrade(Integer vipgrade) {
        this.vipgrade = vipgrade;
    }

    public String getDiscrible() {
        return discrible;
    }

    public void setDiscrible(String discrible) {
        this.discrible = discrible;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @Size(min=1,message = "{vip.valid.vipno.notnull}")
    private String vipcardno;
    private String vipname;
    private Integer vipgrade;
    private String discrible;


    public static final String ID = "id";

    public static final String VIPCARDNO = "vipcardno";

    public static final String VIPNAME = "vipname";

    public static final String VIPGRADE = "vipgrade";

    public static final String DISCRIBLE = "discrible";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @TableField(exist = false)
    public boolean FILTERDEPT = false;
}
