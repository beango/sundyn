package com.sundyn.vo;

public class PlayListVo
{
    private Integer playListId;
    private String playListName;
    private String playListDescription;
    private String playIds;
    
    public Integer getPlayListId() {
        return this.playListId;
    }
    
    public void setPlayListId(final Integer playListId) {
        this.playListId = playListId;
    }
    
    public String getPlayListName() {
        return this.playListName;
    }
    
    public void setPlayListName(final String playListName) {
        this.playListName = playListName;
    }
    
    public String getPlayListDescription() {
        return this.playListDescription;
    }
    
    public void setPlayListDescription(final String playListDescription) {
        this.playListDescription = playListDescription;
    }
    
    public String getPlayIds() {
        return this.playIds;
    }
    
    public void setPlayIds(final String playIds) {
        this.playIds = playIds;
    }
}
