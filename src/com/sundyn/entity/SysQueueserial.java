package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IEnum;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author oKong
 * @since 2018-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_queueserial")
public class SysQueueserial extends Model<SysQueueserial> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @Min(value=1, message="{serial.valid.hall.notnull}")
    private Integer hallid;

    public String getHallname() {
        return hallname;
    }

    public void setHallname(String hallname) {
        this.hallname = hallname;
    }

    @TableField(exist=false)
    private String hallname;
    @Size(min=1,max=50,message="{serial.valid.serialid.notnull}")
    private String bizid;
    private String bizlsh;
    private String bizotherid;
    private Integer ticketmachineno;
    private String bizname;
    private int deptid;
    private String deptname;
    private Integer biztype;

    public String getBiztypename() {
        if(this.biztype==null)
            return null;
        BizTypeEnum e = BizTypeEnum.codeOf(this.biztype);
        if (e!=null)
            return e.msg;
        return null;
    }

    public void setBiztypename(String biztypename) {
        this.biztypename = biztypename;
    }

    @TableField(exist = false)
    private String biztypename;
    private int onlinenetticket;
    private String bizdiscrible;
    private String bizappointnote;
    private Integer alimitnum1;
    private Integer alimitnum2;
    private Integer alimitnum3;
    private Integer alimitnum4;
    private Integer alimitnum5;
    private Integer alimitnum6;
    private Integer alimitnum7;
    private Integer plimitnum1;
    private Integer plimitnum2;
    private Integer plimitnum3;
    private Integer plimitnum4;
    private Integer plimitnum5;
    private Integer plimitnum6;
    private Integer plimitnum7;
    private String xcqueuenumberprefix;
    private String xcsnumber;
    private String xcenumber;
    private String yyqueuenumberprefix;
    private String ysnumber;
    private String yenumber;
    private int ispauseticket;
    private String transferbizid;
    private Integer expectedservicetime;
    private Integer serviceouttime;
    private Integer waitouttime;
    private int isverfifyticket;
    private Integer smssendpernumbers;
    private int isinputcustomerinfo;
    private String servicescounter;
    private String wk1ticketworktimeam1;
    private String wk1ticketworktimeam2;
    private String wk2ticketworktimeam1;
    private String wk2ticketworktimeam2;
    private String wk3ticketworktimeam1;
    private String wk3ticketworktimeam2;
    private String wk4ticketworktimeam1;
    private String wk4ticketworktimeam2;
    private String wk5ticketworktimeam1;
    private String wk5ticketworktimeam2;
    private String wk6ticketworktimeam1;
    private String wk6ticketworktimeam2;
    private String wk7ticketworktimeam1;
    private String wk7ticketworktimeam2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHallid() {
        return hallid;
    }

    public void setHallid(Integer hallid) {
        this.hallid = hallid;
    }

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getBizlsh() {
        return bizlsh;
    }

    public void setBizlsh(String bizlsh) {
        this.bizlsh = bizlsh;
    }

    public String getBizotherid() {
        return bizotherid;
    }

    public void setBizotherid(String bizotherid) {
        this.bizotherid = bizotherid;
    }

    public Integer getTicketmachineno() {
        return ticketmachineno;
    }

    public void setTicketmachineno(Integer ticketmachineno) {
        this.ticketmachineno = ticketmachineno;
    }

    public String getBizname() {
        return bizname;
    }

    public void setBizname(String bizname) {
        this.bizname = bizname;
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

    public Integer getBiztype() {
        return biztype;
    }

    public void setBiztype(Integer biztype) {
        this.biztype = biztype;
    }

    public int getOnlinenetticket() {
        return onlinenetticket;
    }

    public void setOnlinenetticket(int onlinenetticket) {
        this.onlinenetticket = onlinenetticket;
    }

    public String getBizdiscrible() {
        return bizdiscrible;
    }

    public void setBizdiscrible(String bizdiscrible) {
        this.bizdiscrible = bizdiscrible;
    }

    public String getBizappointnote() {
        return bizappointnote;
    }

    public void setBizappointnote(String bizappointnote) {
        this.bizappointnote = bizappointnote;
    }

    public Integer getAlimitnum1() {
        return alimitnum1;
    }

    public void setAlimitnum1(Integer alimitnum1) {
        this.alimitnum1 = alimitnum1;
    }

    public Integer getAlimitnum2() {
        return alimitnum2;
    }

    public void setAlimitnum2(Integer alimitnum2) {
        this.alimitnum2 = alimitnum2;
    }

    public Integer getAlimitnum3() {
        return alimitnum3;
    }

    public void setAlimitnum3(Integer alimitnum3) {
        this.alimitnum3 = alimitnum3;
    }

    public Integer getAlimitnum4() {
        return alimitnum4;
    }

    public void setAlimitnum4(Integer alimitnum4) {
        this.alimitnum4 = alimitnum4;
    }

    public Integer getAlimitnum5() {
        return alimitnum5;
    }

    public void setAlimitnum5(Integer alimitnum5) {
        this.alimitnum5 = alimitnum5;
    }

    public Integer getAlimitnum6() {
        return alimitnum6;
    }

    public void setAlimitnum6(Integer alimitnum6) {
        this.alimitnum6 = alimitnum6;
    }

    public Integer getAlimitnum7() {
        return alimitnum7;
    }

    public void setAlimitnum7(Integer alimitnum7) {
        this.alimitnum7 = alimitnum7;
    }

    public Integer getPlimitnum1() {
        return plimitnum1;
    }

    public void setPlimitnum1(Integer plimitnum1) {
        this.plimitnum1 = plimitnum1;
    }

    public Integer getPlimitnum2() {
        return plimitnum2;
    }

    public void setPlimitnum2(Integer plimitnum2) {
        this.plimitnum2 = plimitnum2;
    }

    public Integer getPlimitnum3() {
        return plimitnum3;
    }

    public void setPlimitnum3(Integer plimitnum3) {
        this.plimitnum3 = plimitnum3;
    }

    public Integer getPlimitnum4() {
        return plimitnum4;
    }

    public void setPlimitnum4(Integer plimitnum4) {
        this.plimitnum4 = plimitnum4;
    }

    public Integer getPlimitnum5() {
        return plimitnum5;
    }

    public void setPlimitnum5(Integer plimitnum5) {
        this.plimitnum5 = plimitnum5;
    }

    public Integer getPlimitnum6() {
        return plimitnum6;
    }

    public void setPlimitnum6(Integer plimitnum6) {
        this.plimitnum6 = plimitnum6;
    }

    public Integer getPlimitnum7() {
        return plimitnum7;
    }

    public void setPlimitnum7(Integer plimitnum7) {
        this.plimitnum7 = plimitnum7;
    }

    public String getXcqueuenumberprefix() {
        return xcqueuenumberprefix;
    }

    public void setXcqueuenumberprefix(String xcqueuenumberprefix) {
        this.xcqueuenumberprefix = xcqueuenumberprefix;
    }

    public String getXcsnumber() {
        return xcsnumber;
    }

    public void setXcsnumber(String xcsnumber) {
        this.xcsnumber = xcsnumber;
    }

    public String getXcenumber() {
        return xcenumber;
    }

    public void setXcenumber(String xcenumber) {
        this.xcenumber = xcenumber;
    }

    public String getYyqueuenumberprefix() {
        return yyqueuenumberprefix;
    }

    public void setYyqueuenumberprefix(String yyqueuenumberprefix) {
        this.yyqueuenumberprefix = yyqueuenumberprefix;
    }

    public String getYsnumber() {
        return ysnumber;
    }

    public void setYsnumber(String ysnumber) {
        this.ysnumber = ysnumber;
    }

    public String getYenumber() {
        return yenumber;
    }

    public void setYenumber(String yenumber) {
        this.yenumber = yenumber;
    }

    public int getIspauseticket() {
        return ispauseticket;
    }

    public void setIspauseticket(int ispauseticket) {
        this.ispauseticket = ispauseticket;
    }

    public String getTransferbizid() {
        return transferbizid;
    }

    public void setTransferbizid(String transferbizid) {
        this.transferbizid = transferbizid;
    }

    public Integer getExpectedservicetime() {
        return expectedservicetime;
    }

    public void setExpectedservicetime(Integer expectedservicetime) {
        this.expectedservicetime = expectedservicetime;
    }

    public Integer getServiceouttime() {
        return serviceouttime;
    }

    public void setServiceouttime(Integer serviceouttime) {
        this.serviceouttime = serviceouttime;
    }

    public Integer getWaitouttime() {
        return waitouttime;
    }

    public void setWaitouttime(Integer waitouttime) {
        this.waitouttime = waitouttime;
    }

    public int getIsverfifyticket() {
        return isverfifyticket;
    }

    public void setIsverfifyticket(int isverfifyticket) {
        this.isverfifyticket = isverfifyticket;
    }

    public Integer getSmssendpernumbers() {
        return smssendpernumbers;
    }

    public void setSmssendpernumbers(Integer smssendpernumbers) {
        this.smssendpernumbers = smssendpernumbers;
    }

    public int getIsinputcustomerinfo() {
        return isinputcustomerinfo;
    }

    public void setIsinputcustomerinfo(int isinputcustomerinfo) {
        this.isinputcustomerinfo = isinputcustomerinfo;
    }

    public String getServicescounter() {
        return servicescounter;
    }

    public void setServicescounter(String servicescounter) {
        this.servicescounter = servicescounter;
    }

    public String getWk1ticketworktimeam1() {
        return wk1ticketworktimeam1;
    }

    public void setWk1ticketworktimeam1(String wk1ticketworktimeam1) {
        this.wk1ticketworktimeam1 = wk1ticketworktimeam1;
    }

    public String getWk1ticketworktimeam2() {
        return wk1ticketworktimeam2;
    }

    public void setWk1ticketworktimeam2(String wk1ticketworktimeam2) {
        this.wk1ticketworktimeam2 = wk1ticketworktimeam2;
    }

    public String getWk2ticketworktimeam1() {
        return wk2ticketworktimeam1;
    }

    public void setWk2ticketworktimeam1(String wk2ticketworktimeam1) {
        this.wk2ticketworktimeam1 = wk2ticketworktimeam1;
    }

    public String getWk2ticketworktimeam2() {
        return wk2ticketworktimeam2;
    }

    public void setWk2ticketworktimeam2(String wk2ticketworktimeam2) {
        this.wk2ticketworktimeam2 = wk2ticketworktimeam2;
    }

    public String getWk3ticketworktimeam1() {
        return wk3ticketworktimeam1;
    }

    public void setWk3ticketworktimeam1(String wk3ticketworktimeam1) {
        this.wk3ticketworktimeam1 = wk3ticketworktimeam1;
    }

    public String getWk3ticketworktimeam2() {
        return wk3ticketworktimeam2;
    }

    public void setWk3ticketworktimeam2(String wk3ticketworktimeam2) {
        this.wk3ticketworktimeam2 = wk3ticketworktimeam2;
    }

    public String getWk4ticketworktimeam1() {
        return wk4ticketworktimeam1;
    }

    public void setWk4ticketworktimeam1(String wk4ticketworktimeam1) {
        this.wk4ticketworktimeam1 = wk4ticketworktimeam1;
    }

    public String getWk4ticketworktimeam2() {
        return wk4ticketworktimeam2;
    }

    public void setWk4ticketworktimeam2(String wk4ticketworktimeam2) {
        this.wk4ticketworktimeam2 = wk4ticketworktimeam2;
    }

    public String getWk5ticketworktimeam1() {
        return wk5ticketworktimeam1;
    }

    public void setWk5ticketworktimeam1(String wk5ticketworktimeam1) {
        this.wk5ticketworktimeam1 = wk5ticketworktimeam1;
    }

    public String getWk5ticketworktimeam2() {
        return wk5ticketworktimeam2;
    }

    public void setWk5ticketworktimeam2(String wk5ticketworktimeam2) {
        this.wk5ticketworktimeam2 = wk5ticketworktimeam2;
    }

    public String getWk6ticketworktimeam1() {
        return wk6ticketworktimeam1;
    }

    public void setWk6ticketworktimeam1(String wk6ticketworktimeam1) {
        this.wk6ticketworktimeam1 = wk6ticketworktimeam1;
    }

    public String getWk6ticketworktimeam2() {
        return wk6ticketworktimeam2;
    }

    public void setWk6ticketworktimeam2(String wk6ticketworktimeam2) {
        this.wk6ticketworktimeam2 = wk6ticketworktimeam2;
    }

    public String getWk7ticketworktimeam1() {
        return wk7ticketworktimeam1;
    }

    public void setWk7ticketworktimeam1(String wk7ticketworktimeam1) {
        this.wk7ticketworktimeam1 = wk7ticketworktimeam1;
    }

    public String getWk7ticketworktimeam2() {
        return wk7ticketworktimeam2;
    }

    public void setWk7ticketworktimeam2(String wk7ticketworktimeam2) {
        this.wk7ticketworktimeam2 = wk7ticketworktimeam2;
    }

    public String getWk1ticketworktimepm1() {
        return wk1ticketworktimepm1;
    }

    public void setWk1ticketworktimepm1(String wk1ticketworktimepm1) {
        this.wk1ticketworktimepm1 = wk1ticketworktimepm1;
    }

    public String getWk1ticketworktimepm2() {
        return wk1ticketworktimepm2;
    }

    public void setWk1ticketworktimepm2(String wk1ticketworktimepm2) {
        this.wk1ticketworktimepm2 = wk1ticketworktimepm2;
    }

    public String getWk2ticketworktimepm1() {
        return wk2ticketworktimepm1;
    }

    public void setWk2ticketworktimepm1(String wk2ticketworktimepm1) {
        this.wk2ticketworktimepm1 = wk2ticketworktimepm1;
    }

    public String getWk2ticketworktimepm2() {
        return wk2ticketworktimepm2;
    }

    public void setWk2ticketworktimepm2(String wk2ticketworktimepm2) {
        this.wk2ticketworktimepm2 = wk2ticketworktimepm2;
    }

    public String getWk3ticketworktimepm1() {
        return wk3ticketworktimepm1;
    }

    public void setWk3ticketworktimepm1(String wk3ticketworktimepm1) {
        this.wk3ticketworktimepm1 = wk3ticketworktimepm1;
    }

    public String getWk3ticketworktimepm2() {
        return wk3ticketworktimepm2;
    }

    public void setWk3ticketworktimepm2(String wk3ticketworktimepm2) {
        this.wk3ticketworktimepm2 = wk3ticketworktimepm2;
    }

    public String getWk4ticketworktimepm1() {
        return wk4ticketworktimepm1;
    }

    public void setWk4ticketworktimepm1(String wk4ticketworktimepm1) {
        this.wk4ticketworktimepm1 = wk4ticketworktimepm1;
    }

    public String getWk4ticketworktimepm2() {
        return wk4ticketworktimepm2;
    }

    public void setWk4ticketworktimepm2(String wk4ticketworktimepm2) {
        this.wk4ticketworktimepm2 = wk4ticketworktimepm2;
    }

    public String getWk5ticketworktimepm1() {
        return wk5ticketworktimepm1;
    }

    public void setWk5ticketworktimepm1(String wk5ticketworktimepm1) {
        this.wk5ticketworktimepm1 = wk5ticketworktimepm1;
    }

    public String getWk5ticketworktimepm2() {
        return wk5ticketworktimepm2;
    }

    public void setWk5ticketworktimepm2(String wk5ticketworktimepm2) {
        this.wk5ticketworktimepm2 = wk5ticketworktimepm2;
    }

    public String getWk6ticketworktimepm1() {
        return wk6ticketworktimepm1;
    }

    public void setWk6ticketworktimepm1(String wk6ticketworktimepm1) {
        this.wk6ticketworktimepm1 = wk6ticketworktimepm1;
    }

    public String getWk6ticketworktimepm2() {
        return wk6ticketworktimepm2;
    }

    public void setWk6ticketworktimepm2(String wk6ticketworktimepm2) {
        this.wk6ticketworktimepm2 = wk6ticketworktimepm2;
    }

    public String getWk7ticketworktimepm1() {
        return wk7ticketworktimepm1;
    }

    public void setWk7ticketworktimepm1(String wk7ticketworktimepm1) {
        this.wk7ticketworktimepm1 = wk7ticketworktimepm1;
    }

    public String getWk7ticketworktimepm2() {
        return wk7ticketworktimepm2;
    }

    public void setWk7ticketworktimepm2(String wk7ticketworktimepm2) {
        this.wk7ticketworktimepm2 = wk7ticketworktimepm2;
    }

    public String getLastservingnumber() {
        return lastservingnumber;
    }

    public void setLastservingnumber(String lastservingnumber) {
        this.lastservingnumber = lastservingnumber;
    }

    private String wk1ticketworktimepm1;
    private String wk1ticketworktimepm2;
    private String wk2ticketworktimepm1;
    private String wk2ticketworktimepm2;
    private String wk3ticketworktimepm1;
    private String wk3ticketworktimepm2;
    private String wk4ticketworktimepm1;
    private String wk4ticketworktimepm2;
    private String wk5ticketworktimepm1;
    private String wk5ticketworktimepm2;
    private String wk6ticketworktimepm1;
    private String wk6ticketworktimepm2;
    private String wk7ticketworktimepm1;
    private String wk7ticketworktimepm2;
    private String lastservingnumber;


    public static final String ID = "id";

    public static final String HALLID = "hallid";

    public static final String BIZID = "bizid";

    public static final String BIZLSH = "bizlsh";

    public static final String BIZOTHERID = "bizotherid";

    public static final String TICKETMACHINENO = "ticketmachineno";

    public static final String BIZNAME = "bizname";

    public static final String DEPTID = "deptid";

    public static final String DEPTNAME = "deptname";

    public static final String BIZTYPE = "biztype";

    public static final String ONLINENETTICKET = "onlinenetticket";

    public static final String BIZDISCRIBLE = "bizdiscrible";

    public static final String BIZAPPOINTNOTE = "bizappointnote";

    public static final String ALIMITNUM1 = "alimitnum1";

    public static final String ALIMITNUM2 = "alimitnum2";

    public static final String ALIMITNUM3 = "alimitnum3";

    public static final String ALIMITNUM4 = "alimitnum4";

    public static final String ALIMITNUM5 = "alimitnum5";

    public static final String ALIMITNUM6 = "alimitnum6";

    public static final String ALIMITNUM7 = "alimitnum7";

    public static final String PLIMITNUM1 = "plimitnum1";

    public static final String PLIMITNUM2 = "plimitnum2";

    public static final String PLIMITNUM3 = "plimitnum3";

    public static final String PLIMITNUM4 = "plimitnum4";

    public static final String PLIMITNUM5 = "plimitnum5";

    public static final String PLIMITNUM6 = "plimitnum6";

    public static final String PLIMITNUM7 = "plimitnum7";

    public static final String XCQUEUENUMBERPREFIX = "xcqueuenumberprefix";

    public static final String XCSNUMBER = "xcsnumber";

    public static final String XCENUMBER = "xcenumber";

    public static final String YYQUEUENUMBERPREFIX = "yyqueuenumberprefix";

    public static final String YSNUMBER = "ysnumber";

    public static final String YENUMBER = "yenumber";

    public static final String ISPAUSETICKET = "ispauseticket";

    public static final String TRANSFERBIZID = "transferbizid";

    public static final String EXPECTEDSERVICETIME = "expectedservicetime";

    public static final String SERVICEOUTTIME = "serviceouttime";

    public static final String WAITOUTTIME = "waitouttime";

    public static final String ISVERFIFYTICKET = "isverfifyticket";

    public static final String SMSSENDPERNUMBERS = "smssendpernumbers";

    public static final String ISINPUTCUSTOMERINFO = "isinputcustomerinfo";

    public static final String SERVICESCOUNTER = "servicescounter";

    public static final String WK1TICKETWORKTIMEAM1 = "wk1ticketworktimeam1";

    public static final String WK1TICKETWORKTIMEAM2 = "wk1ticketworktimeam2";

    public static final String WK2TICKETWORKTIMEAM1 = "wk2ticketworktimeam1";

    public static final String WK2TICKETWORKTIMEAM2 = "wk2ticketworktimeam2";

    public static final String WK3TICKETWORKTIMEAM1 = "wk3ticketworktimeam1";

    public static final String WK3TICKETWORKTIMEAM2 = "wk3ticketworktimeam2";

    public static final String WK4TICKETWORKTIMEAM1 = "wk4ticketworktimeam1";

    public static final String WK4TICKETWORKTIMEAM2 = "wk4ticketworktimeam2";

    public static final String WK5TICKETWORKTIMEAM1 = "wk5ticketworktimeam1";

    public static final String WK5TICKETWORKTIMEAM2 = "wk5ticketworktimeam2";

    public static final String WK6TICKETWORKTIMEAM1 = "wk6ticketworktimeam1";

    public static final String WK6TICKETWORKTIMEAM2 = "wk6ticketworktimeam2";

    public static final String WK7TICKETWORKTIMEAM1 = "wk7ticketworktimeam1";

    public static final String WK7TICKETWORKTIMEAM2 = "wk7ticketworktimeam2";

    public static final String WK1TICKETWORKTIMEPM1 = "wk1ticketworktimepm1";

    public static final String WK1TICKETWORKTIMEPM2 = "wk1ticketworktimepm2";

    public static final String WK2TICKETWORKTIMEPM1 = "wk2ticketworktimepm1";

    public static final String WK2TICKETWORKTIMEPM2 = "wk2ticketworktimepm2";

    public static final String WK3TICKETWORKTIMEPM1 = "wk3ticketworktimepm1";

    public static final String WK3TICKETWORKTIMEPM2 = "wk3ticketworktimepm2";

    public static final String WK4TICKETWORKTIMEPM1 = "wk4ticketworktimepm1";

    public static final String WK4TICKETWORKTIMEPM2 = "wk4ticketworktimepm2";

    public static final String WK5TICKETWORKTIMEPM1 = "wk5ticketworktimepm1";

    public static final String WK5TICKETWORKTIMEPM2 = "wk5ticketworktimepm2";

    public static final String WK6TICKETWORKTIMEPM1 = "wk6ticketworktimepm1";

    public static final String WK6TICKETWORKTIMEPM2 = "wk6ticketworktimepm2";

    public static final String WK7TICKETWORKTIMEPM1 = "wk7ticketworktimepm1";

    public static final String WK7TICKETWORKTIMEPM2 = "wk7ticketworktimepm2";

    public static final String LASTSERVINGNUMBER = "lastservingnumber";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public enum BizTypeEnum {

        Y(true, 1, "办件类业务"), N(false, 2, "咨询业务"), S(false, 3, "出件类业务"), T(false, 4, "其他类");

        private boolean value;

        private int code;

        private String msg;

        BizTypeEnum(boolean value, int code, String msg) {
            this.value = value;
            this.code = code;
            this.msg = msg;
        }

        /**
         * 根据code获取到YNEnum,取不到返回null
         *
         * @param code
         * @return
         */
        public static BizTypeEnum codeOf(int code) {
            for (BizTypeEnum ynEnum : BizTypeEnum.values()) {
                if (ynEnum.code == code) {
                    return ynEnum;
                }
            }
            return null;
        }

        public boolean isValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

    }

}
