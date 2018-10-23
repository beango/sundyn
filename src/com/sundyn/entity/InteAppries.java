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
 * @author oKong
 * @since 2018-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("inte_appries")
public class InteAppries extends Model<InteAppries> {

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHallno() {
        return hallno;
    }

    public void setHallno(String hallno) {
        this.hallno = hallno;
    }

    public String getQueuenum() {
        return queuenum;
    }

    public void setQueuenum(String queuenum) {
        this.queuenum = queuenum;
    }

    public String getYwlsh() {
        return ywlsh;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    public String getAppriseresult() {
        return appriseresult;
    }

    public void setAppriseresult(String appriesresult) {
        this.appriseresult = appriesresult;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String hallno;
    private String queuenum;
    private String ywlsh;
    private String bizid;
    private String eventtime;
    private String appriseresult;
    private String tt;
    private Date ctime;


    public static final String ID = "id";

    public static final String HALLNO = "hallno";

    public static final String QUEUENUM = "queuenum";

    public static final String YWLSH = "ywlsh";

    public static final String BIZID = "bizid";

    public static final String EVENTTIME = "eventtime";

    public static final String APPRISERESULT = "appriseresult";

    public static final String TT = "tt";

    public static final String CTIME = "ctime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
