package com.sundyn.action;

import java.util.*;
import com.sundyn.service.*;
import com.sundyn.vo.*;
import com.sundyn.util.*;
import org.apache.struts2.*;
import java.io.*;
import javax.servlet.http.*;
import com.sundyn.utils.*;
import com.sundyn.statistics.*;
import java.sql.*;

public class AdviceSurveyAction
{
    private static final Integer pageSize;
    private static final Integer pageSize1;
    public List<Map<String, List<Map<String, Map<String, Double>>>>> adviceStatistics;
    public QuestionVo question;
    public AnswerVo answer;
    public AdviceVo advice;
    public AdviceService adviceService;
    public String answerString;
    public List<AdviceVo> advices;
    private String url;
    private InputStream xml;
    private String msg;
    private StringHql sh;
    private List<CheckVo> checks;
    private Pager pager;
    private CitysUtils citysUtils;
    private String addInput;
    
    static {
        pageSize = 6;
        pageSize1 = 1;
    }
    
    public AdviceSurveyAction() {
        this.sh = new StringHql();
    }
    
    public String adviceToAdd() {
        return "adviceToAdd";
    }
    
    public String adviceAdd() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String question = request.getParameter("aq");
        if (this.answerString == null || this.answerString.equals("")) {
            this.answerString = "\u6682\u65f6\u6ca1\u6709\u6dfb\u52a0\u4efb\u4f55\u9009\u9879";
        }
        if (this.advice == null) {
            this.advice = new AdviceVo();
        }
        final String[] as = this.answerString.split(";");
        String ast = "";
        String[] array;
        for (int length = (array = as).length, i = 0; i < length; ++i) {
            final String st = array[i];
            ast = String.valueOf(ast) + st.trim() + ";";
        }
        this.answerString = ast;
        final QuestionVo q = new QuestionVo();
        q.setQuestion(question);
        this.advice.setQuestion(q);
        this.adviceService.addAdviceVo(this.advice.getQuestion(), this.answerString);
        AdviceUtils.getAdviceTables(this.adviceService, "", this.sh);
        final File f = new File("c:/advise.db");
        if (f.exists()) {
            f.delete();
        }
        return "addOk";
    }
    
    public String adviceToUpdate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        if (this.advice == null) {
            this.advice = new AdviceVo();
        }
        final QuestionVo q = new QuestionVo();
        q.setId(Integer.parseInt(id));
        this.advice.setQuestion(q);
        this.advice = this.adviceService.findAdviceById(this.advice);
        return "adviceToUpdate";
    }
    
    public String adviceUpdate() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String question = request.getParameter("aq");
        final String id = request.getParameter("id");
        if (this.answerString == null || this.answerString.equals("")) {
            this.answerString = "\u6682\u65f6\u6ca1\u6709\u6dfb\u52a0\u4efb\u4f55\u9009\u9879";
        }
        if (this.advice == null) {
            this.advice = new AdviceVo();
        }
        final QuestionVo q = new QuestionVo();
        q.setQuestion(question);
        q.setId(Integer.parseInt(id));
        this.advice.setQuestion(q);
        if (this.answerString == null || this.answerString.equals("")) {
            this.answerString = "\u60a8\u6ca1\u6709\u8f93\u5165\u4efb\u4f55\u9009\u9879";
        }
        this.advice.setAnswers(StringUtils.getListAnswers(this.answerString));
        this.adviceService.adviceUpdate(this.advice);
        AdviceUtils.getAdviceTables(this.adviceService, "", this.sh);
        final File f = new File("c:/advise.db");
        if (f.exists()) {
            f.delete();
        }
        return "adviceUpdateOk";
    }
    
    public String adviceDelete() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        if (this.advice == null) {
            this.advice = new AdviceVo();
        }
        final QuestionVo q = new QuestionVo();
        q.setId(Integer.parseInt(id));
        this.advice.setQuestion(q);
        this.adviceService.adviceDelete(this.advice);
        AdviceUtils.getAdviceTables(this.adviceService, "", this.sh);
        final File f = new File("c:/advise.db");
        if (f.exists()) {
            f.delete();
        }
        return "adviceDeleteOk";
    }
    
    public String adviceList() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int rowsCount = this.adviceService.getCount1();
        this.pager = new Pager("currentPage", AdviceSurveyAction.pageSize, rowsCount, request);
        this.advices = this.adviceService.findAdvice(false, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(this.advices);
        return "adviceListOk";
    }
    
    public String advicesaveAnswer() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String answerss = request.getParameter("answer");
        if (answerss != null) {
            if (NumberUtils.isInteger(answerss)) {
                try {
                    this.adviceService.saveAnswers(answerss, this.sh);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                this.msg = "\u6dfb\u52a0\u6210\u529f";
            }
            else {
                this.msg = "\u6dfb\u52a0\u5931\u8d25\uff0c\u6240\u4f20\u53c2\u6570\u4e0d\u662f\u6570\u5b57\u4e32";
            }
            return "advicesaveAnswerOk";
        }
        this.msg = "\u6dfb\u52a0\u5931\u8d25";
        return "input";
    }
    
    public String showAnserTable() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int rowsCount = this.adviceService.getCount1();
        this.pager = new Pager("currentPage", AdviceSurveyAction.pageSize1, rowsCount, request);
        this.adviceStatistics = (List<Map<String, List<Map<String, Map<String, Double>>>>>)AdviceStatistics.adviceStatistics((this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), this.adviceService);
        this.pager.setPageList(this.adviceStatistics);
        return "showAnserTableOk";
    }
    
    public String checkList() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int rowsCount = this.adviceService.getCount();
        this.pager = new Pager("currentPage", AdviceSurveyAction.pageSize, rowsCount, request);
        this.checks = this.adviceService.getChecks((this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(this.checks);
        return "checkListOk";
    }
    
    public String addInputAjax() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int ai = Integer.parseInt(request.getParameter("ai"));
        this.addInput = "";
        for (int i = 1; i <= ai; ++i) {
            if (i % 2 == 0) {
                this.addInput = String.valueOf(this.addInput) + "<input type='text' name='as' value='' /></p>";
            }
            else {
                this.addInput = String.valueOf(this.addInput) + "<input type='text' name='as' value='' />";
            }
        }
        return "addOk";
    }
    
    public String adviceDownload() throws SQLException, Exception {
        return "adviceDownloadOk";
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
    
    public AdviceVo getAdvice() {
        return this.advice;
    }
    
    public void setAdvice(final AdviceVo advice) {
        this.advice = advice;
    }
    
    public AdviceService getAdviceService() {
        return this.adviceService;
    }
    
    public void setAdviceService(final AdviceService adviceService) {
        this.adviceService = adviceService;
    }
    
    public String getAnswerString() {
        return this.answerString;
    }
    
    public void setAnswerString(final String answerString) {
        this.answerString = answerString;
    }
    
    public List<AdviceVo> getAdvices() {
        return this.advices;
    }
    
    public void setAdvices(final List<AdviceVo> advices) {
        this.advices = advices;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public InputStream getXml() {
        final String filename = "advise.db";
        return ServletActionContext.getServletContext().getResourceAsStream("/dbdownload/" + filename);
    }
    
    public void setXml(final InputStream xml) {
        this.xml = xml;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public List<Map<String, List<Map<String, Map<String, Double>>>>> getAdviceStatistics() {
        return this.adviceStatistics;
    }
    
    public void setAdviceStatistics(final List<Map<String, List<Map<String, Map<String, Double>>>>> adviceStatistics) {
        this.adviceStatistics = adviceStatistics;
    }
    
    public StringHql getSh() {
        return this.sh;
    }
    
    public void setSh(final StringHql sh) {
        this.sh = sh;
    }
    
    public List<CheckVo> getChecks() {
        return this.checks;
    }
    
    public void setChecks(final List<CheckVo> checks) {
        this.checks = checks;
    }
    
    public Pager getPager() {
        return this.pager;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public static Integer getPagesize() {
        return AdviceSurveyAction.pageSize;
    }
    
    public static Integer getPagesize1() {
        return AdviceSurveyAction.pageSize1;
    }
    
    public CitysUtils getCitysUtils() {
        return this.citysUtils;
    }
    
    public void setCitysUtils(final CitysUtils citysUtils) {
        this.citysUtils = citysUtils;
    }
    
    public String getAddInput() {
        return this.addInput;
    }
    
    public void setAddInput(final String addInput) {
        this.addInput = addInput;
    }
}
