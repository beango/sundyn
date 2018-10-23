package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
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
 * @author oKong
 * @since 2018-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_queuehisdetail")
public class SysQueuehisdetail extends Model<SysQueuehisdetail> {

    private static final long serialVersionUID = 1L;

    private String id;
    private Integer hallid;
    private String bizid;
    private String bizname;
    private String deptno;
    private String deptname;
    private String queuenumber;
    private String mobile;
    private String cardid;
    @TableField("cardName")
    private String cardName;
    private Date tickettime;
    private Integer numbertype;
    private String appointmentid;
    private Integer ishj;
    private Date hjtime;
    private String hjcounter;
    private String staffno;
    private Date starttime;
    private Date endtime;
    private Integer servicestime;
    private Integer waittime;
    private Integer appriseresult;
    private String appriseresultname;
    private Date apprisetime;
    private Integer isagent;
    private Integer status;


    public static final String ID = "id";

    public static final String HALLID = "hallid";

    public static final String BIZID = "bizid";

    public static final String BIZNAME = "bizname";

    public static final String DEPTNO = "deptno";

    public static final String DEPTNAME = "deptname";

    public static final String QUEUENUMBER = "queuenumber";

    public static final String MOBILE = "mobile";

    public static final String CARDID = "cardid";

    public static final String CARDNAME = "cardName";

    public static final String TICKETTIME = "tickettime";

    public static final String NUMBERTYPE = "numbertype";

    public static final String APPOINTMENTID = "appointmentid";

    public static final String ISHJ = "ishj";

    public static final String HJTIME = "hjtime";

    public static final String HJCOUNTER = "hjcounter";

    public static final String STAFFNO = "staffno";

    public static final String STARTTIME = "starttime";

    public static final String ENDTIME = "endtime";

    public static final String SERVICESTIME = "servicestime";

    public static final String WAITTIME = "waittime";

    public static final String APPRISERESULT = "appriseresult";

    public static final String APPRISERESULTNAME = "appriseresultname";

    public static final String APPRISETIME = "apprisetime";

    public static final String ISAGENT = "isagent";

    public static final String STATUS = "status";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
