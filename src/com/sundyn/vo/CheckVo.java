package com.sundyn.vo;

import java.io.*;

public class CheckVo implements Serializable
{
    private int id;
    private QuestionVo question;
    private AnswerVo answer;
    private String date;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public QuestionVo getQuestion() {
        return this.question;
    }
    
    public void setQuestion(final QuestionVo question) {
        this.question = question;
    }
    
    public AnswerVo getAnswer() {
        return this.answer;
    }
    
    public void setAnswer(final AnswerVo answer) {
        this.answer = answer;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public void setDate(final String date) {
        this.date = date;
    }
}
