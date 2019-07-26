package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
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
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("sys_queuecounter")
public class SysQueuecounter extends Model<SysQueuecounter> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public Integer getHallid() {
        return hallid;
    }

    public void setHallid(Integer hallid) {
        this.hallid = hallid;
    }

    private Integer hallid;

    public String getHallname() {
        return hallname;
    }

    public void setHallname(String hallname) {
        this.hallname = hallname;
    }
    @TableField(exist = false)
    private String hallname;
    public String getCounterno() {
        return counterno;
    }

    public void setCounterno(String counterno) {
        this.counterno = counterno;
    }

    @Size(min=1,message = "{counter.valid.counterno.notnull}")
    private String counterno;
    private String counterid;
    private String busno;
    @Size(min=1,message = "{counter.valid.countername.notnull}")
    private String countername;
    private String pysicalcallpadaddr;
    private String counterledaddr;
    private String compledaddr;
    private String loginstaffno;
    private Date lastlivetime;
    private String palysoundcontent;
    private String serviceseriallist1;
    private String serviceseriallist2;
    private String serviceseriallist3;

    public String getHallno() {
        return hallno;
    }

    public void setHallno(String hallno) {
        this.hallno = hallno;
    }

    private String hallno;

    public String getCountertypename() {
        if(this.countertype==null)
            return null;
        CounterTypeNameEnum e = CounterTypeNameEnum.codeOf(this.countertype);
        if (e!=null)
            return e.msg;
        return null;
    }

    public void setCountertypename(String countertypename) {
        this.countertypename = countertypename;
    }

    @TableField(exist = false)
    private String countertypename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCounterid() {
        return counterid;
    }

    public void setCounterid(String counterid) {
        this.counterid = counterid;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getCountername() {
        return countername;
    }

    public void setCountername(String countername) {
        this.countername = countername;
    }

    public String getPysicalcallpadaddr() {
        return pysicalcallpadaddr;
    }

    public void setPysicalcallpadaddr(String pysicalcallpadaddr) {
        this.pysicalcallpadaddr = pysicalcallpadaddr;
    }

    public String getCounterledaddr() {
        return counterledaddr;
    }

    public void setCounterledaddr(String counterledaddr) {
        this.counterledaddr = counterledaddr;
    }

    public String getCompledaddr() {
        return compledaddr;
    }

    public void setCompledaddr(String compledaddr) {
        this.compledaddr = compledaddr;
    }

    public String getLoginstaffno() {
        return loginstaffno;
    }

    public void setLoginstaffno(String loginstaffno) {
        this.loginstaffno = loginstaffno;
    }

    public Date getLastlivetime() {
        return lastlivetime;
    }

    public void setLastlivetime(Date lastlivetime) {
        this.lastlivetime = lastlivetime;
    }

    public String getPalysoundcontent() {
        return palysoundcontent;
    }

    public void setPalysoundcontent(String palysoundcontent) {
        this.palysoundcontent = palysoundcontent;
    }

    public String getServiceseriallist1() {
        return serviceseriallist1;
    }

    public void setServiceseriallist1(String serviceseriallist1) {
        this.serviceseriallist1 = serviceseriallist1;
    }

    public String getServiceseriallist2() {
        return serviceseriallist2;
    }

    public void setServiceseriallist2(String serviceseriallist2) {
        this.serviceseriallist2 = serviceseriallist2;
    }

    public String getServiceseriallist3() {
        return serviceseriallist3;
    }

    public void setServiceseriallist3(String serviceseriallist3) {
        this.serviceseriallist3 = serviceseriallist3;
    }

    public String getServiceseriallist4() {
        return serviceseriallist4;
    }

    public void setServiceseriallist4(String serviceseriallist4) {
        this.serviceseriallist4 = serviceseriallist4;
    }

    public String getServiceseriallist5() {
        return serviceseriallist5;
    }

    public void setServiceseriallist5(String serviceseriallist5) {
        this.serviceseriallist5 = serviceseriallist5;
    }

    public Integer getCountertype() {
        return countertype;
    }

    public void setCountertype(Integer countertype) {
        this.countertype = countertype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLasttranscodeid() {
        return lasttranscodeid;
    }

    public void setLasttranscodeid(String lasttranscodeid) {
        this.lasttranscodeid = lasttranscodeid;
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

    private String serviceseriallist4;
    private String serviceseriallist5;
    private Integer countertype;
    private Integer status;
    private String lasttranscodeid;
    private Integer deptid;
    private String deptname;

    public static final String ID = "id";

    public static final String HALLID = "hallid";

    public static final String COUNTERNO = "counterno";

    public static final String COUNTERID = "counterid";

    public static final String BUSNO = "busno";

    public static final String COUNTERNAME = "countername";

    public static final String PYSICALCALLPADADDR = "pysicalcallpadaddr";

    public static final String COUNTERLEDADDR = "counterledaddr";

    public static final String COMPLEDADDR = "compledaddr";

    public static final String LOGINSTAFFNO = "loginstaffno";

    public static final String LASTLIVETIME = "lastlivetime";

    public static final String PALYSOUNDCONTENT = "palysoundcontent";

    public static final String SERVICESERIALLIST1 = "serviceseriallist1";

    public static final String SERVICESERIALLIST2 = "serviceseriallist2";

    public static final String SERVICESERIALLIST3 = "serviceseriallist3";

    public static final String SERVICESERIALLIST4 = "serviceseriallist4";

    public static final String SERVICESERIALLIST5 = "serviceseriallist5";

    public static final String COUNTERTYPE = "countertype";

    public static final String STATUS = "status";

    public static final String LASTTRANSCODEID = "lasttranscodeid";

    public static final String DEPTID = "deptid";

    public static final String DEPTNAME = "deptname";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public enum CounterTypeNameEnum {

        Y(true, 1, "咨询窗口"), N(false, 2, "办件窗口"), S(false, 3, "出件窗口"), T(false, 4, "其他");

        private boolean value;

        private int code;

        private String msg;

        CounterTypeNameEnum(boolean value, int code, String msg) {
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
        public static CounterTypeNameEnum codeOf(int code) {
            for (CounterTypeNameEnum ynEnum : CounterTypeNameEnum.values()) {
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
