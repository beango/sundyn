package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
 * @since 2018-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_queuehall")
public class SysQueuehall extends Model<SysQueuehall> {

    private static final long serialVersionUID = 1L;

    public String getHallno() {
        return hallno;
    }

    public void setHallno(String hallno) {
        this.hallno = hallno;
    }

    public String getHallname() {
        return hallname;
    }

    public void setHallname(String hallname) {
        this.hallname = hallname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHeadtel() {
        return headtel;
    }

    public void setHeadtel(String headtel) {
        this.headtel = headtel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBusitmnote() {
        return busitmnote;
    }

    public void setBusitmnote(String busitmnote) {
        this.busitmnote = busitmnote;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOnlinenetxcticket() {
        return onlinenetxcticket;
    }

    public void setOnlinenetxcticket(int onlinenetxcticket) {
        this.onlinenetxcticket = onlinenetxcticket;
    }

    public int getOnlineappoint() {
        return onlineappoint;
    }

    public void setOnlineappoint(int onlineappoint) {
        this.onlineappoint = onlineappoint;
    }

    public String getAppointnote() {
        return appointnote;
    }

    public void setAppointnote(String appointnote) {
        this.appointnote = appointnote;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getVersiontime() {
        return versiontime;
    }

    public void setVersiontime(Date versiontime) {
        this.versiontime = versiontime;
    }

    public Date getGetverionlasttime() {
        return getverionlasttime;
    }

    public void setGetverionlasttime(Date getverionlasttime) {
        this.getverionlasttime = getverionlasttime;
    }

    public String getWorkversion() {
        return workversion;
    }

    public void setWorkversion(String workversion) {
        this.workversion = workversion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    private Integer deptid;
    @Size(min=1,max=50,message="大厅编码不能为空")
    private String hallno;
    @Size(min=1,max=50,message="大厅名称不能为空")
    private String hallname;
    private String address;
    private String longitude;
    private String dimension;
    private String head;
    private String headtel;
    private String note;
    private String busitmnote;
    private String path;
    private int onlinenetxcticket;
    private int onlineappoint;
    private String appointnote;
    private Integer sort;
    private String version;
    private Date versiontime;
    private Date getverionlasttime;
    private String workversion;

    public Integer getEval6warnvalue() {
        return eval6warnvalue;
    }

    public void setEval6warnvalue(Integer eval6warnvalue) {
        this.eval6warnvalue = eval6warnvalue;
    }

    private Integer eval6warnvalue;

    public Integer getPausewarnvalue() {
        return pausewarnvalue;
    }

    public void setPausewarnvalue(Integer pausewarnvalue) {
        this.pausewarnvalue = pausewarnvalue;
    }

    private Integer pausewarnvalue;

    public Integer getWaitwarnvalue() {
        return waitwarnvalue;
    }

    public void setWaitwarnvalue(Integer waitwarnvalue) {
        this.waitwarnvalue = waitwarnvalue;
    }

    private Integer waitwarnvalue;
    public static final String HALLNO = "hallno";

    public static final String HALLNAME = "hallname";

    public static final String ADDRESS = "address";

    public static final String LONGITUDE = "longitude";

    public static final String DIMENSION = "dimension";

    public static final String HEAD = "head";

    public static final String HEADTEL = "headtel";

    public static final String NOTE = "note";

    public static final String BUSITMNOTE = "busitmnote";

    public static final String PATH = "path";

    public static final String ONLINENETXCTICKET = "onlinenetxcticket";

    public static final String ONLINEAPPOINT = "onlineappoint";

    public static final String APPOINTNOTE = "appointnote";

    public static final String SORT = "sort";

    public static final String VERSION = "version";

    public static final String VERSIONTIME = "versiontime";

    public static final String GETVERIONLASTTIME = "getverionlasttime";

    public static final String WORKVERSION = "workversion";

    public static final String DEPTID = "deptid";

    @Override
    protected Serializable pkVal() {
        return  this.id;
    }

}
