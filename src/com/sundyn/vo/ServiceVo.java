package com.sundyn.vo;

public class ServiceVo
{
    private Integer id;
    private String businessName;
    private String remark;
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getBusinessName() {
        return this.businessName;
    }
    
    public void setBusinessName(final String businessName) {
        this.businessName = businessName;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
}
