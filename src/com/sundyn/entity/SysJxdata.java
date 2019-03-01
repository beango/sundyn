package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author huangding
 * @since 2019-02-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_jxdata")
public class SysJxdata extends Model<SysJxdata> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getEno() {
        return eno;
    }

    public void setEno(String eno) {
        this.eno = eno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getServicedate() {
        return servicedate;
    }

    public void setServicedate(String servicedate) {
        this.servicedate = servicedate;
    }

    public Float getYkq() {
        return ykq;
    }

    public void setYkq(Float ykq) {
        this.ykq = ykq;
    }

    public Float getQzby() {
        return qzby;
    }

    public void setQzby(Float qzby) {
        this.qzby = qzby;
    }

    public Float getRcxc() {
        return rcxc;
    }

    public void setRcxc(Float rcxc) {
        this.rcxc = rcxc;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Integer getCuser() {
        return cuser;
    }

    public void setCuser(Integer cuser) {
        this.cuser = cuser;
    }
    @NotNull(message="所属部门不能为空")
    private Integer deptid;
    private String deptname;
    @Size(min=1,max=50,message="员工工号不能为空")
    private String eno;
    private String ename;
    @Size(min=1,max=50,message="日期不能为空")
    private String servicedate;
    private Float ykq;
    private Float qzby;
    private Float rcxc;

    public String getYpfjset() {
        return ypfjset;
    }

    public void setYpfjset(String ypfjset) {
        this.ypfjset = ypfjset;
    }
    @TableField(exist = false)
    private String ypfjset;

    public String getFjdesc() {
        return fjdesc;
    }

    public void setFjdesc(String fjdesc) {
        this.fjdesc = fjdesc;
    }

    private String fjdesc;

    public Integer getYpfj() {
        return ypfj;
    }

    public void setYpfj(Integer ypfj) {
        this.ypfj = ypfj;
    }

    private Integer ypfj;
    private Date ctime;
    private Integer cuser;


    public static final String ID = "id";

    public static final String DEPTID = "deptid";

    public static final String DEPTNAME = "deptname";

    public static final String ENO = "eno";

    public static final String ENAME = "ename";

    public static final String SERVICEDATE = "servicedate";

    public static final String YKQ = "ykq";

    public static final String QZBY = "qzby";

    public static final String RCXC = "rcxc";

    public static final String CTIME = "ctime";

    public static final String CUSER = "cuser";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
