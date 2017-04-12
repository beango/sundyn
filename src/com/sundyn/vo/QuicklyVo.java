package com.sundyn.vo;

public class QuicklyVo
{
    private Integer id;
    private String name;
    private String excuteSql;
    private String startDate;
    private String endDate;
    private String count;
    private Integer serviceType;
    private String resultType;
    private String deptIDs;
    private Integer employees;
    private String remark;
    private String managerId;
    
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
    
    public String getExcuteSql() {
        return this.excuteSql;
    }
    
    public void setExcuteSql(final String excuteSql) {
        this.excuteSql = excuteSql;
    }
    
    public String getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    
    public String getCount() {
        return this.count;
    }
    
    public void setCount(final String count) {
        this.count = count;
    }
    
    public Integer getServiceType() {
        return this.serviceType;
    }
    
    public void setServiceType(final Integer serviceType) {
        this.serviceType = serviceType;
    }
    
    public String getResultType() {
        return this.resultType;
    }
    
    public void setResultType(final String resultType) {
        this.resultType = resultType;
    }
    
    public String getDeptIDs() {
        return this.deptIDs;
    }
    
    public void setDeptIDs(final String deptIDs) {
        this.deptIDs = deptIDs;
    }
    
    public Integer getEmployees() {
        return this.employees;
    }
    
    public void setEmployees(final Integer employees) {
        this.employees = employees;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
    
    public String getManagerId() {
        return this.managerId;
    }
    
    public void setManagerId(final String managerId) {
        this.managerId = managerId;
    }
}
