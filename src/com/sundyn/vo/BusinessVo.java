package com.sundyn.vo;

public class BusinessVo
{
    Integer businessId;
    String businessName;
    String businessDescription;
    Integer businessFatherId;
    Integer businessIndex;
    Integer businessLevel;
    Boolean businessIsUse;
    Integer businessIsLeaf;
    
    public Integer getBusinessId() {
        return this.businessId;
    }
    
    public void setBusinessId(final Integer businessId) {
        this.businessId = businessId;
    }
    
    public String getBusinessName() {
        return this.businessName;
    }
    
    public void setBusinessName(final String businessName) {
        this.businessName = businessName;
    }
    
    public String getBusinessDescription() {
        return this.businessDescription;
    }
    
    public void setBusinessDescription(final String businessDescription) {
        this.businessDescription = businessDescription;
    }
    
    public Boolean getBusinessIsUse() {
        return this.businessIsUse;
    }
    
    public void setBusinessIsUse(final Boolean businessIsUse) {
        this.businessIsUse = businessIsUse;
    }
    
    public Integer getBusinessFatherId() {
        return this.businessFatherId;
    }
    
    public void setBusinessFatherId(final Integer businessFatherId) {
        this.businessFatherId = businessFatherId;
    }
    
    public Integer getBusinessIndex() {
        return this.businessIndex;
    }
    
    public void setBusinessIndex(final Integer businessIndex) {
        this.businessIndex = businessIndex;
    }
    
    public Integer getBusinessLevel() {
        return this.businessLevel;
    }
    
    public void setBusinessLevel(final Integer businessLevel) {
        this.businessLevel = businessLevel;
    }
    
    public Integer getBusinessIsLeaf() {
        return this.businessIsLeaf;
    }
    
    public void setBusinessIsLeaf(final Integer businessIsLeaf) {
        this.businessIsLeaf = businessIsLeaf;
    }
}
