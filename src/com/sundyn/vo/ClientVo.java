package com.sundyn.vo;

import java.io.Serializable;
import java.util.Date;

public class ClientVo implements Serializable
{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientNo() {
        return ClientNo;
    }

    public void setClientNo(int clientNo) {
        ClientNo = clientNo;
    }

    public int getClientVer() {
        return ClientVer;
    }

    public void setClientVer(int clientVer) {
        ClientVer = clientVer;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public int getUseCount() {
        return UseCount;
    }

    public void setUseCount(int useCount) {
        UseCount = useCount;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    private int id;
    private int ClientNo;
    private int ClientVer;
    private String FilePath;
    private int UseCount;
    private Date ctime;
    private String comments;

}
