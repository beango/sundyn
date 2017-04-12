package com.sundyn.vo;

import java.io.*;

public class CustomerCheckVo implements Serializable
{
    private int id;
    private String answer;
    private int questionid;
    
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
    
    public int getQuestionid() {
        return this.questionid;
    }
    
    public void setQuestionid(final int questionid) {
        this.questionid = questionid;
    }
}
