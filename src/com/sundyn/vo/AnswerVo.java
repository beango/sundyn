package com.sundyn.vo;

import java.io.*;

public class AnswerVo implements Serializable
{
    private int id;
    private String answer;
    private int fatherid;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getAnswer() {
        return this.answer;
    }
    
    public void setAnswer(final String answer) {
        this.answer = answer;
    }
    
    public int getFatherid() {
        return this.fatherid;
    }
    
    public void setFatherid(final int fatherid) {
        this.fatherid = fatherid;
    }
}
