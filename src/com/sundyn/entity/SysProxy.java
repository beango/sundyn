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
 * @since 2018-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_proxy")
public class SysProxy extends Model<SysProxy> {

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String idtype;
    private String idcard;
    private String name;
    private Date ctime;
    private String note;
    private int ispause;

    public int getIspause() {
        return ispause;
    }

    public void setIspause(int ispause) {
        this.ispause = ispause;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    private String orgcode;

    public static final String ID = "id";

    public static final String IDTYPE = "idtype";

    public static final String IDCARD = "idcard";

    public static final String NAME = "name";

    public static final String CTIME = "ctime";

    public static final String NOTE = "note";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
