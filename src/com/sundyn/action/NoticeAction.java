package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.vo.*;
import com.sundyn.service.*;
import com.sundyn.util.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.io.*;
import com.sundyn.utils.*;
import java.util.*;

public class NoticeAction extends ActionSupport
{
    private static final Integer pageSize;
    public NoticeVo notice;
    public NoticeService noticeService;
    public String isDeal;
    public List notices;
    private String url;
    private InputStream xml;
    private String msg;
    private Pager pager;
    
    static {
        pageSize = 6;
    }
    
    public String noticeToAdd() {
        return "noticeToAdd";
    }
    
    public String noticeAdd() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String title = request.getParameter("title");
        final String content = request.getParameter("content");
        if (this.notice == null) {
            this.notice = new NoticeVo();
        }
        this.notice.setTitle(title);
        this.notice.setContent(content);
        if (this.noticeService.noticeAdd(this.notice)) {
            this.creatXml();
            return "addOk";
        }
        this.isDeal = "\u6dfb\u52a0\u5931\u8d25\u8bf7\u91cd\u65b0\u6dfb\u52a0";
        return "inputs";
    }
    
    public String noticeList() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int rowsCount = this.noticeService.getCount();
        this.pager = new Pager("currentPage", NoticeAction.pageSize, rowsCount, request, "noticPage");
        this.notices = this.noticeService.findNotices((this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(this.notices);
        return "noticeListOk";
    }
    
    public String noticeToUpdate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        final Map map = this.noticeService.findNoticeById(Integer.parseInt(id));
        if (this.notice == null) {
            this.notice = new NoticeVo();
        }
        this.notice.setTitle((String) map.get("title"));
        this.notice.setContent((String) map.get("content"));
        this.notice.setId(Integer.parseInt(id));
        return "noticeToUpdate";
    }
    
    public String noticeUpdate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String title = request.getParameter("title");
        final String content = request.getParameter("content");
        final String id = request.getParameter("id");
        if (this.notice == null) {
            this.notice = new NoticeVo();
        }
        this.notice.setTitle(title);
        this.notice.setContent(content);
        this.notice.setId(Integer.parseInt(id));
        this.noticeService.noticeUpdate(this.notice);
        this.creatXml();
        return "noticeUpdateOk";
    }
    
    public String noticeDelete() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        this.noticeService.noticeDelete(Integer.parseInt(id));
        this.creatXml();
        return "noticeDeleteOk";
    }
    
    public String noticeDownload() throws FileNotFoundException, IOException {
        return "noticeDownloadOk";
    }
    
    private boolean creatXml() {
        final List<NoticeVo> noticess = new ArrayList<NoticeVo>();
        try {
            final List list = this.noticeService.findNotices();
            for (final Object map1 : list) {
            	Map map = (Map)map1;
                final NoticeVo notice = new NoticeVo();
                System.out.println(map.get("id").toString());
                notice.setId(Integer.parseInt(map.get("id").toString()));
                notice.setTitle((String) map.get("title"));
                notice.setContent((String) map.get("content"));
                notice.setDate((String) map.get("date"));
                noticess.add(notice);
            }
            JavaXML.downloadNotice(noticess);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public NoticeVo getNotice() {
        return this.notice;
    }
    
    public void setNotice(final NoticeVo notice) {
        this.notice = notice;
    }
    
    public NoticeService getNoticeService() {
        return this.noticeService;
    }
    
    public void setNoticeService(final NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    
    public String getIsDeal() {
        return this.isDeal;
    }
    
    public void setIsDeal(final String isDeal) {
        this.isDeal = isDeal;
    }
    
    public List getNotices() {
        return this.notices;
    }
    
    public void setNotices(final List notices) {
        this.notices = notices;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public InputStream getXml() {
        final String filename = "notice.xml";
        return ServletActionContext.getServletContext().getResourceAsStream("/WEB-INF/source/" + filename);
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
    
    public Pager getPager() {
        return this.pager;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public static Integer getPagesize() {
        return NoticeAction.pageSize;
    }
}
