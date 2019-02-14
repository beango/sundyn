package com.sundyn.entity;

import com.baomidou.mybatisplus.activerecord.Model;
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
 * @author huangding
 * @since 2019-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("Sys_Proxyorg")
public class SysProxyorg extends Model<SysProxyorg> {

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public Boolean getIsenable() {
        return isenable;
    }

    public void setIsenable(Boolean isenable) {
        this.isenable = isenable;
    }

    public String getMaintel() {
        return maintel;
    }

    public void setMaintel(String maintel) {
        this.maintel = maintel;
    }

    public String getMainname() {
        return mainname;
    }

    public void setMainname(String mainname) {
        this.mainname = mainname;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    private Integer id;
    @Size(min=1,max=50,message="机构名称不能为空")
    private String orgname;
    @Size(min=1,max=50,message="机构识别号码不能为空")
    private String orgcode;
    private Boolean isenable;
    @Size(min=1,max=50,message="联系方式不能为空")
    private String maintel;
    @Size(min=1,max=50,message="联系人不能为空")
    private String mainname;
    private Integer level;
    private Date ctime;
    private int status;
    private int reviewid;
    private Date reviewtime;
    private int cancelid;

    public int getIspause() {
        return ispause;
    }

    public void setIspause(int ispause) {
        this.ispause = ispause;
    }

    private int ispause;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public Date getReviewtime() {
        return reviewtime;
    }

    public void setReviewtime(Date reviewtime) {
        this.reviewtime = reviewtime;
    }

    public int getCancelid() {
        return cancelid;
    }

    public void setCancelid(int cancelid) {
        this.cancelid = cancelid;
    }

    public Date getCanceltime() {
        return canceltime;
    }

    public void setCanceltime(Date canceltime) {
        this.canceltime = canceltime;
    }

    private Date canceltime;

    @TableId(value = "id", type = IdType.AUTO)
    public static final String ID = "id";

    public static final String ORGNAME = "orgname";

    public static final String ORGCODE = "orgcode";

    public static final String ISENABLE = "isenable";

    public static final String MAINTEL = "maintel";

    public static final String MAINNAME = "mainname";

    public static final String LEVEL = "level";

    public static final String CTIME = "ctime";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
