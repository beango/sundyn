package com.sundyn.vo;

import java.io.*;
import java.util.*;

public class AdviceVo implements Serializable
{
    private int id;
    private List<AnswerVo> answers;
    private QuestionVo question;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public List<AnswerVo> getAnswers() {
        return this.answers;
    }
    
    public void setAnswers(final List<AnswerVo> answers) {
        this.answers = answers;
    }
    
    public QuestionVo getQuestion() {
        return this.question;
    }
    
    public void setQuestion(final QuestionVo question) {
        this.question = question;
    }
}
