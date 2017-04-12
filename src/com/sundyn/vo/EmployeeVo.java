package com.sundyn.vo;

import java.sql.*;

public class EmployeeVo
{
    private Integer id;
    private String deptName;
    private Integer deptid;
    private String name;
    private String sex;
    private String phone;
    private String cardnum;
    private String remark;
    private String job;
    private Date beginTime;
    private Date birthday;
    private String picture;
    private String ext1;
    private String ext2;
    private String PassWord;
    private String job_desc;
    private String showDeptName;
    private String showWindowName;
    private String companyName;
    private String blong;
    private String detpTel;
    private String ext3;
    private String ext4;
    
    public String getShowDeptName() {
        return this.showDeptName;
    }
    
    public void setShowDeptName(final String showDeptName) {
        this.showDeptName = showDeptName;
    }
    
    public String getShowWindowName() {
        return this.showWindowName;
    }
    
    public void setShowWindowName(final String showWindowName) {
        this.showWindowName = showWindowName;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getDeptName() {
        return this.deptName;
    }
    
    public void setDeptName(final String deptName) {
        this.deptName = deptName;
    }
    
    public Integer getDeptid() {
        return this.deptid;
    }
    
    public void setDeptid(final Integer deptid) {
        this.deptid = deptid;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getSex() {
        return this.sex;
    }
    
    public void setSex(final String sex) {
        this.sex = sex;
    }
    
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(final String phone) {
        this.phone = phone;
    }
    
    public String getCardnum() {
        return this.cardnum;
    }
    
    public void setCardnum(final String cardnum) {
        this.cardnum = cardnum;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
    
    public String getJob() {
        return this.job;
    }
    
    public void setJob(final String job) {
        this.job = job;
    }
    
    public Date getBeginTime() {
        return this.beginTime;
    }
    
    public void setBeginTime(final Date beginTime) {
        this.beginTime = beginTime;
    }
    
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }
    
    public String getPicture() {
        return this.picture;
    }
    
    public void setPicture(final String picture) {
        this.picture = picture;
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
    
    public String getPassWord() {
        return this.PassWord;
    }
    
    public void setPassWord(final String passWord) {
        this.PassWord = passWord;
    }
    
    public String getJob_desc() {
        return this.job_desc;
    }
    
    public void setJob_desc(final String job_desc) {
        this.job_desc = job_desc;
    }
    
    public String getBlong() {
        return this.blong;
    }
    
    public void setBlong(final String blong) {
        this.blong = blong;
    }
    
    public String getDetpTel() {
        return this.detpTel;
    }
    
    public void setDetpTel(final String detpTel) {
        this.detpTel = detpTel;
    }
    
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }
    
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(final String ext3) {
        this.ext3 = ext3;
    }
    
    public String getExt4() {
        return this.ext4;
    }
    
    public void setExt4(final String ext4) {
        this.ext4 = ext4;
    }
}
