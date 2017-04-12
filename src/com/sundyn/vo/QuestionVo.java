package com.sundyn.vo;

import java.io.*;

public class QuestionVo implements Serializable
{
    private int id;
    private String question;
    private int adviceid;
    private int sortid;
    
    public QuestionVo() {
        this.sortid = 0;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getQuestion() {
        return this.question;
    }
    
    public void setQuestion(final String question) {
        this.question = question;
    }
    
    public int getAdviceid() {
        return this.adviceid;
    }
    
    public void setAdviceid(final int adviceid) {
        this.adviceid = adviceid;
    }
    
    public int getSortid() {
        return this.sortid;
    }
    
    public void setSortid(final int sortid) {
        this.sortid = sortid;
    }
}
