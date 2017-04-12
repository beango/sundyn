package com.sundyn.vo;

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
