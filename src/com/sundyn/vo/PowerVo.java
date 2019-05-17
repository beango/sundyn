package com.sundyn.vo;

public class PowerVo
{
    private String name;
    private Integer id;
    private Integer baseSet;
    private Integer dataManage;
    private Integer deptIdGroup;
    private String powertype;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status;

    public String getPowertype() {
        return powertype;
    }

    public void setPowertype(String powertype) {
        this.powertype = powertype;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Integer getBaseSet() {
        return this.baseSet;
    }
    
    public void setBaseSet(final Integer baseSet) {
        this.baseSet = baseSet;
    }
    
    public Integer getDataManage() {
        return this.dataManage;
    }
    
    public void setDataManage(final Integer dataManage) {
        this.dataManage = dataManage;
    }
    
    public Integer getDeptIdGroup() {
        return this.deptIdGroup;
    }
    
    public void setDeptIdGroup(final Integer deptIdGroup) {
        this.deptIdGroup = deptIdGroup;
    }
}
