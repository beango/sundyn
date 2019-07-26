package com.sundyn.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.sundyn.vo.LogTypeEnum;
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
@TableName("audit_log")
public class AuditLog extends Model<AuditLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String logtype;
    private String ipadd;
    private String logrst;
    private String logrstdesc;
    private Date ctime;
    @TableField(exist = false)
    private String ncheckdigit;
    private Integer checkdigited;
    @TableField(exist = false)
    private String logtypei18n;

    public String getLogtypei18n() {
        System.out.println(logtype);
        LogTypeEnum l = LogTypeEnum.codeOf(logtype);
        if (l!=null)
            return l.toString();
        return logtype;
    }

    public void setLogtypei18n(String str) {
        System.out.println(logtype);
        logtypei18n = str;
    }

    public String getCheckdigit() {
        return checkdigit;
    }

    public void setCheckdigit(String checkdigit) {
        this.checkdigit = checkdigit;
    }

    private String checkdigit;

    public String getLogdevice() {
        return logdevice;
    }

    public void setLogdevice(String logdevice) {
        this.logdevice = logdevice;
    }

    private String logdevice;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String LOGTYPE = "logtype";

    public static final String IPADD = "ipadd";

    public static final String LOGRST = "logrst";

    public static final String LOGRSTDESC = "logrstdesc";

    public static final String CTIME = "ctime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}