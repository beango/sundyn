package com.sundyn.vo;

import java.sql.*;

public class ErrorAppriesInfoVo
{
    private Timestamp appriesTime;
    private String keyId;
    private String remark;
    private String m_devid;
    private String cardNum;
    
    public Timestamp getAppriesTime() {
        return this.appriesTime;
    }
    
    public void setAppriesTime(final Timestamp appriesTime) {
        this.appriesTime = appriesTime;
    }
    
    public String getKeyId() {
        return this.keyId;
    }
    
    public void setKeyId(final String keyId) {
        this.keyId = keyId;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
    
    public String getM_devid() {
        return this.m_devid;
    }
    
    public void setM_devid(final String m_devid) {
        this.m_devid = m_devid;
    }
    
    public String getCardNum() {
        return this.cardNum;
    }
    
    public void setCardNum(final String cardNum) {
        this.cardNum = cardNum;
    }
}
