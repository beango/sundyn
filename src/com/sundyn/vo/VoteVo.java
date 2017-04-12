package com.sundyn.vo;

public class VoteVo
{
    Integer voteId;
    String voteTitle;
    String voteDescription;
    Integer voteNum;
    String voteSelect;
    Integer voteHit;
    Integer voteIsUse;
    Integer voteParentId;
    
    public Integer getVoteId() {
        return this.voteId;
    }
    
    public void setVoteId(final Integer voteId) {
        this.voteId = voteId;
    }
    
    public String getVoteTitle() {
        return this.voteTitle;
    }
    
    public void setVoteTitle(final String voteTitle) {
        this.voteTitle = voteTitle;
    }
    
    public String getVoteDescription() {
        return this.voteDescription;
    }
    
    public void setVoteDescription(final String voteDescription) {
        this.voteDescription = voteDescription;
    }
    
    public Integer getVoteNum() {
        return this.voteNum;
    }
    
    public void setVoteNum(final Integer voteNum) {
        this.voteNum = voteNum;
    }
    
    public String getVoteSelect() {
        return this.voteSelect;
    }
    
    public void setVoteSelect(final String voteSelect) {
        this.voteSelect = voteSelect;
    }
    
    public Integer getVoteHit() {
        return this.voteHit;
    }
    
    public void setVoteHit(final Integer voteHit) {
        this.voteHit = voteHit;
    }
    
    public Integer getVoteIsUse() {
        return this.voteIsUse;
    }
    
    public void setVoteIsUse(final Integer voteIsUse) {
        this.voteIsUse = voteIsUse;
    }
    
    public Integer getVoteParentId() {
        return this.voteParentId;
    }
    
    public void setVoteParentId(final Integer voteParentId) {
        this.voteParentId = voteParentId;
    }
}
