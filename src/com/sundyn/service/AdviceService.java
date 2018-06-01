package com.sundyn.service;

import com.sundyn.dao.*;
import java.util.*;
import com.sundyn.utils.*;
import com.sundyn.vo.*;
import java.sql.*;

public class AdviceService extends SuperDao
{
    public boolean addAdviceVo(final QuestionVo question, final String answers) {
        final String sql1 = "Insert into appries_question (question, sortid) values(?,?)";
        final String sql2 = "select max(id) id from appries_question";
        final Object[] arg = { question.getQuestion(), question.getSortid() };
        try {
            this.getJdbcTemplate().update(sql1, arg);
            final int id = this.getJdbcTemplate().queryForInt(sql2);
            final String[] answer = StringUtils.getAnswer(answers);
            String[] array;
            for (int length = (array = answer).length, i = 0; i < length; ++i) {
                final String s = array[i];
                final String sql3 = "insert into appries_answer(answer,fatherid) values(?,?);";
                this.getJdbcTemplate().update(sql3, new Object[] { s, id });
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<AdviceVo> findAdvice(final Boolean isSort) {
        String sql = "";
        if (isSort) {
            sql = "select * from appries_question order by sortid";
        }
        else {
            sql = "select * from appries_question order by id";
        }
        final List<AdviceVo> advices = new ArrayList<AdviceVo>();
        try {
            final List list = this.getJdbcTemplate().queryForList(sql);
            for (final Object map1 : list) {
            	Map map = (Map)map1;
                final QuestionVo q = new QuestionVo();
                q.setQuestion(map.get("question").toString());
                q.setId((int)map.get("id"));
                q.setSortid((int)map.get("sortid"));
                final List<AnswerVo> answers = this.getAnswerList(q.getId());
                final AdviceVo advice = new AdviceVo();
                advice.setAnswers(answers);
                advice.setQuestion(q);
                advices.add(advice);
            }
            return advices;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<AnswerVo> getAnswerList(final int questionid) {
        final List<AnswerVo> answers = new ArrayList<AnswerVo>();
        final String sql2 = "select * from appries_answer where fatherid=" + questionid;
        List li = new ArrayList();
        try {
            li = this.getJdbcTemplate().queryForList(sql2);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        for (final Object m1 : li) {
        	Map m = (Map)m1;
            final AnswerVo a = new AnswerVo();
            a.setId((int)m.get("id"));
            a.setAnswer((String)m.get("answer"));
            a.setFatherid(questionid);
            answers.add(a);
        }
        return answers;
    }
    
    public List<AdviceVo> findAdvice(final Boolean isSort, final int startrow, final int pageSize) {
        String sql = "";
        if (isSort) {
            sql = "select row_number() over(order by sortid) as rows, * from appries_question";
            sql = "select * from ("+sql+") t2 where t2.rows>" + startrow + " and t2.rows<=" + (startrow+pageSize);
        }
        else {
            sql = "select row_number() over(order by id) as rows, * from appries_question";
            sql = "select * from ("+sql+") t2 where t2.rows>" + startrow + " and t2.rows<=" + (startrow+pageSize);
        }
        final List<AdviceVo> advices = new ArrayList<AdviceVo>();
        try {
            final List list = this.getJdbcTemplate().queryForList(sql);
            for (final Object map1 : list) {
            	Map map = (Map)map1;
                final QuestionVo q = new QuestionVo();
                q.setQuestion((String)map.get("question"));
                q.setId((int)map.get("id"));
                q.setSortid((int)map.get("sortid"));
                final List<AnswerVo> answers = new ArrayList<AnswerVo>();
                final String str2 = "select * from appries_answer where fatherid=" + q.getId();
                final List li = this.getJdbcTemplate().queryForList(str2);
                for (final Object m1 : li) {
                	Map m = (Map)map1;
                    final AnswerVo a = new AnswerVo();
                    a.setId((int)m.get("id"));
                    a.setAnswer((String)m.get("answer"));
                    a.setFatherid(q.getId());
                    answers.add(a);
                }
                final AdviceVo advice = new AdviceVo();
                advice.setAnswers(answers);
                advice.setQuestion(q);
                advices.add(advice);
            }
            return advices;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean adviceDelete(final AdviceVo advice) {
        final String sql1 = "delete from appries_question_answer where questionid=?";
        final String sql2 = "delete from appries_answer where fatherid=?";
        final String sql3 = "delete from appries_question where id=?";
        int num = 0;
        int num2 = 0;
        int num3 = 0;
        final Object[] arg = { advice.getQuestion().getId() };
        try {
            num = this.getJdbcTemplate().update(sql1, arg);
            num2 = this.getJdbcTemplate().update(sql2, arg);
            num3 = this.getJdbcTemplate().update(sql3, arg);
            if (num > 0 && num2 > 0 && num3 > 0) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean adviceUpdate(final AdviceVo advice) {
        int num = 0;
        int num2 = 0;
        final String sql = "update appries_question set question=?,sortid=? where id=?";
        final Object[] args = { advice.getQuestion().getQuestion(), advice.getQuestion().getSortid(), advice.getQuestion().getId() };
        try {
            if (advice.getAnswers() != null) {
                final List<AnswerVo> answers = advice.getAnswers();
                final int size = answers.size();
                final List<Integer> ids = new ArrayList<Integer>();
                final List<String> newAnswers = new ArrayList<String>();
                for (int i = 0; i < size; ++i) {
                    final List map = this.getJdbcTemplate().queryForList("select DISTINCT(answer),id from appries_answer where fatherid=? and answer=?", new Object[] { advice.getQuestion().getId(), answers.get(i).getAnswer() });
                    if (map.size() > 0) {
                        for (final Object m1 : map) {
                        	Map m = (Map)m1;
                            final int id = (int)m.get("id");
                            ids.add(id);
                        }
                        ids.add(answers.get(i).getId());
                    }
                    else {
                        newAnswers.add(answers.get(i).getAnswer());
                    }
                }
                num = this.getJdbcTemplate().update(sql, args);
                String sql2 = "";
                if (ids.size() == 0) {
                    sql2 = "delete from appries_answer where  fatherid=" + advice.getQuestion().getId();
                }
                else {
                    sql2 = "delete from appries_answer where id  not in( " + StringUtils.getInString(ids) + " ) and fatherid=" + advice.getQuestion().getId();
                }
                num2 = this.getJdbcTemplate().update(sql2);
                for (final String s : newAnswers) {
                    final String sql3 = "insert into  appries_answer (answer,fatherid) values(?,?)";
                    this.getJdbcTemplate().update(sql3, new Object[] { s, advice.getQuestion().getId() });
                }
                if (num > 0) {
                    return true;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public AdviceVo findAdviceById(final AdviceVo advice) {
        final AdviceVo ad = new AdviceVo();
        final String sql = "select * from appries_question  where id=?";
        final Object[] arg = { advice.getQuestion().getId() };
        try {
            final Map map = this.getJdbcTemplate().queryForMap(sql, arg);
            final QuestionVo q = new QuestionVo();
            q.setQuestion((String)map.get("question"));
            q.setId((int)map.get("id"));
            q.setSortid((int)map.get("sortid"));
            final List<AnswerVo> answers = new ArrayList<AnswerVo>();
            final String str2 = "select * from appries_answer where fatherid=" + q.getId();
            final List li = this.getJdbcTemplate().queryForList(str2);
            for (final Object m1 : li) {
            	Map m = (Map)m1;
                final AnswerVo a = new AnswerVo();
                a.setId((int)m.get("id"));
                a.setAnswer((String)m.get("answer"));
                a.setFatherid(q.getId());
                answers.add(a);
            }
            advice.setAnswers(answers);
            advice.setQuestion(q);
            return advice;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean updateSortid(final Map<Integer, Integer> sort) {
        int num = 0;
        if (sort != null) {
            try {
                for (final int i : sort.keySet()) {
                    final String sql = "update appries_question set sortid=? where id=?";
                    num += this.getJdbcTemplate().update(sql, new Object[] { i, sort.get("i") });
                }
                if (num == sort.size()) {
                    return true;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    public boolean saveAnswer(final int questionid, final int asnswerid) {
        final String sql1 = "Insert into appries_question_answer (answerid,questionid, date) values(?,?,?)";
        final Object[] arg = { asnswerid, questionid, DateFormat.dateFormat(new java.util.Date()) };
        try {
            this.getJdbcTemplate().update(sql1, arg);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean saveAnswers(final String answerss, final StringHql str) {
        final int[] aids = new int[answerss.length()];
        for (int i = 0; i < answerss.length(); ++i) {
            aids[i] = Integer.parseInt(new StringBuilder(String.valueOf(answerss.charAt(i))).toString());
        }
        final List list = this.getAllQuestions();
        if (list.size() != aids.length) {
            str.setStr("\u6dfb\u52a0\u5931\u8d25");
            return false;
        }
        final Iterator it = list.iterator();
        int j = 0;
        while (it.hasNext()) {
            final Map m = (Map) it.next();
            final int id = (int)m.get("id");
            final QuestionVo q = new QuestionVo();
            final AnswerVo a = new AnswerVo();
            q.setId(id);
            final List<AnswerVo> answerlist = this.getAnswerList(q.getId());
            if (answerlist != null) {
                if (aids[j] > answerlist.size()) {
                    break;
                }
                final AnswerVo an = answerlist.get(aids[j]);
                this.saveAnswer(id, an.getId());
            }
            ++j;
        }
        return true;
    }
    
    public List getAllAnswers() {
        final String sql1 = "select * from appries_answer ";
        try {
            return this.getJdbcTemplate().queryForList(sql1);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List getAllQuestions() {
        final String sql1 = "select * from appries_question ";
        try {
            return this.getJdbcTemplate().queryForList(sql1);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int getCount() {
        final String sql = "select count(id) from appries_question_answer";
        return this.getJdbcTemplate().queryForInt(sql);
    }
    
    public int getCount1() {
        final String sql = "select count(id) from appries_question";
        return this.getJdbcTemplate().queryForInt(sql);
    }
    
    public double getRate(final AnswerVo av) {
        final int a = this.getAnserCountByFatherid(av.getFatherid(), av.getId());
        final int b = this.getAnserCountByFatherid(av.getFatherid());
        if (b == 0) {
            return 0.0;
        }
        return StringUtils.getDoubleRate(a, b);
    }
    
    public int getAnserCountByFatherid(final int questionid, final int answerid) {
        final String sql = "select * from appries_question_answer where questionid=? and answerid=? ";
        final List list = this.getJdbcTemplate().queryForList(sql, new Object[] { questionid, answerid });
        return list.size();
    }
    
    public int getAnserCountByFatherid(final int questionid) {
        final String sql = "select * from appries_question_answer where questionid=?";
        final List list = this.getJdbcTemplate().queryForList(sql, new Object[] { questionid });
        return list.size();
    }
    
    public List<CheckVo> getChecks(final int startrow, final int pageSize) throws SQLException {
        final List<CheckVo> checks = new ArrayList<CheckVo>();
        final String sql = "select * from appries_question_answer order by id limit ?,? ";
        final Object[] arg = { startrow, pageSize };
        try {
            final List list = this.getJdbcTemplate().queryForList(sql, arg);
            for (final Object map1 : list) {
            	Map map = (Map)map1;
                final int questionid = (int)map.get("questionid");
                final int answerid = (int)map.get("answerid");
                final CheckVo check = new CheckVo();
                check.setId((int)map.get("id"));
                check.setQuestion(this.getQuestionById(questionid));
                check.setAnswer(this.getAnswerById(answerid));
                checks.add(check);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return checks;
    }
    
    public CheckVo getCheck(final CheckVo checkVo) {
        final CheckVo check = new CheckVo();
        final String sql = "select * from appries_question_answer where id=?";
        final Object[] arg = { checkVo.getId() };
        try {
            final Map map = this.getJdbcTemplate().queryForMap(sql);
            final int answerid = (int)map.get("answerid");
            final int questionid = (int)map.get("questionid");
            check.setQuestion(this.getQuestionById(questionid));
            check.setAnswer(this.getAnswerById(answerid));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    
    public QuestionVo getQuestionById(final int id) {
        final QuestionVo question = new QuestionVo();
        final String sql = "select * from appries_question where id=" + id;
        try {
            final Map map = this.getJdbcTemplate().queryForMap(sql);
            question.setId(id);
            question.setQuestion((String)map.get("question"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return question;
    }
    
    public AnswerVo getAnswerById(final int id) {
        final AnswerVo answer = new AnswerVo();
        final String sql = "select * from appries_answer where id=" + id;
        try {
            final Map map = this.getJdbcTemplate().queryForMap(sql);
            answer.setId(id);
            answer.setAnswer((String)map.get("answer"));
            answer.setFatherid((int)map.get("fatherid"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }
}
