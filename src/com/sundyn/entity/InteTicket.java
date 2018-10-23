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
@TableName("inte_ticket")
public class InteTicket extends Model<InteTicket> {

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

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getQueuetype() {
        return queuetype;
    }

    public void setQueuetype(String queuetype) {
        this.queuetype = queuetype;
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

    public String getCounterlist() {
        return counterlist;
    }

    public void setCounterlist(String counterlist) {
        this.counterlist = counterlist;
    }

    public String getYyno() {
        return yyno;
    }

    public void setYyno(String yyno) {
        this.yyno = yyno;
    }

    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
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
    private String bizid;
    private String cardtype;
    private String cardid;
    private String queuetype;
    private String queuenum;
    private String ywlsh;
    private String counterlist;
    private String yyno;
    private String eventtime;
    private String tt;
    private Date ctime;

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    private String cardname;

    public String getIsproxy() {
        return isproxy;
    }

    public void setIsproxy(String isproxy) {
        this.isproxy = isproxy;
    }

    private String isproxy;

    public static final String ID = "id";

    public static final String HALLNO = "hallno";

    public static final String BIZID = "bizid";

    public static final String CARDTYPE = "cardtype";

    public static final String CARDID = "cardid";

    public static final String QUEUETYPE = "queuetype";

    public static final String QUEUENUM = "queuenum";

    public static final String YWLSH = "ywlsh";

    public static final String COUNTERLIST = "counterlist";

    public static final String YYNO = "yyno";

    public static final String EVENTTIME = "eventtime";

    public static final String TT = "tt";

    public static final String CTIME = "ctime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
