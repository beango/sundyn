package com.sundyn.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ManagerVo
{
    private Integer id;
    private String name;
    private String realname;
    private String password;
    private Integer skinid;
    private Integer userGroupId;
    private String remark;
    private String ext1;
    private String ext2;
    private int jyflag;
    private String jyno;
    private Date uexpired;
    private Date pwdexpried;
    private String idcard;
    private String userid;
    @Getter @Setter
    private String telphone;
    private Integer cuser;
    @Getter @Setter
    private String contact;
    @Getter @Setter
    private String orgname;

    public Integer getCuser() {
        return cuser;
    }

    public void setCuser(Integer cuser) {
        this.cuser = cuser;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    private Date ctime;

    public String getAccessip() {
        return accessip;
    }

    public void setAccessip(String accessip) {
        this.accessip = accessip;
    }

    private String accessip;

    public int getNeedchangepwd() {
        return needchangepwd;
    }

    public void setNeedchangepwd(int needchangepwd) {
        this.needchangepwd = needchangepwd;
    }

    private int needchangepwd;

    public String getAccesstime1() {
        return accesstime1;
    }

    public void setAccesstime1(String accesstime1) {
        this.accesstime1 = accesstime1;
    }

    public String getAccesstime2() {
        return accesstime2;
    }

    public void setAccesstime2(String accesstime2) {
        this.accesstime2 = accesstime2;
    }

    private String accesstime1;
    private String accesstime2;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCheckdigit() {
        return checkdigit;
    }

    public void setCheckdigit(String checkdigit) {
        this.checkdigit = checkdigit;
    }

    private String checkdigit;
    private Integer checkdigited;

    public Integer getCheckdigited() {
        return checkdigited;
    }

    public void setCheckdigited(Integer checkdigited) {
        this.checkdigited = checkdigited;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public int getJyflag() {
        return jyflag;
    }

    public void setJyflag(int jyflag) {
        this.jyflag = jyflag;
    }

    public String getJyno() {
        return jyno;
    }

    public void setJyno(String jyno) {
        this.jyno = jyno;
    }

    public Date getUexpired() {
        return uexpired;
    }

    public void setUexpired(Date uexpired) {
        this.uexpired = uexpired;
    }

    public Date getPwdexpried() {
        return pwdexpried;
    }

    public void setPwdexpried(Date pwdexpried) {
        this.pwdexpried = pwdexpried;
    }

    private int deptid;

    public int getLocaluser() {
        return localuser;
    }

    public void setLocaluser(int localuser) {
        this.localuser = localuser;
    }

    private int localuser;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    private String deptName;

    public int getDeptid() {
        return deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
    }

    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getRealname() {
        return this.realname;
    }
    
    public void setRealname(final String realname) {
        this.realname = realname;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public Integer getSkinid() {
        return this.skinid;
    }
    
    public void setSkinid(final Integer skinid) {
        this.skinid = skinid;
    }
    
    public Integer getUserGroupId() {
        return this.userGroupId;
    }
    
    public void setUserGroupId(final Integer userGroupId) {
        this.userGroupId = userGroupId;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
    
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(final String ext1) {
        this.ext1 = ext1;
    }
    
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(final String ext2) {
        this.ext2 = ext2;
    }
}
