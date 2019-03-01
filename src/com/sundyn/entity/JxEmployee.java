package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
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
 * @since 2019-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jx_employee")
public class JxEmployee extends Model<JxEmployee> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer deptid;
    private String deptname;
    private String eno;
    private String ename;
    private String servicedate;
    private Integer ypfj;
    private Float servicetime;
    private Integer servicecount;
    private Float deptservicetimeavg;
    private Float servicetime3m;
    private Integer servicecount3m;
    private Float serviceavgtime3m;
    private Float fwpjl;
    private Float fwzl;
    private Float employeefwxn;
    private Float deptfwxn;
    private Float ykq;
    private Float qzby;
    private Float rcxc;
    private Date ctime;
    private Integer cuser;
    private String fjdesc;

    public static final String ID = "id";

    public static final String DEPTID = "deptid";

    public static final String DEPTNAME = "deptname";

    public static final String ENO = "eno";

    public static final String ENAME = "ename";

    public static final String SERVICEDATE = "servicedate";

    public static final String YPFJ = "ypfj";

    public static final String SERVICETIME = "servicetime";

    public static final String SERVICECOUNT = "servicecount";

    public static final String DEPTSERVICETIMEAVG = "deptservicetimeavg";

    public static final String SERVICETIME3M = "servicetime3m";

    public static final String SERVICECOUNT3M = "servicecount3m";

    public static final String SERVICEAVGTIME3M = "serviceavgtime3m";

    public static final String FWPJL = "fwpjl";

    public static final String FWZL = "fwzl";

    public static final String EMPLOYEEFWXN = "employeefwxn";

    public static final String DEPTFWXN = "deptfwxn";

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
