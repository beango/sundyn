package com.sundyn.util;

import com.opensymphony.xwork2.*;
import java.util.*;
import javax.servlet.http.*;

public class Pager extends ActionSupport
{
    private int currentPage;
    private int pageSize;
    private int rowsCount;
    private int previous;
    private int next;
    private int pages;
    private String pageTextCn;
    private String pageTextEn;
    private String pageTextAjax;
    private String pageNum;
    private String url;
    private List pageList;
    
    public int getCurrentPage() {
        return this.currentPage;
    }
    
    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }
    
    public int getPageSize() {
        return this.pageSize;
    }
    
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getRowsCount() {
        return this.rowsCount;
    }
    
    public void setRowsCount(final int rowsCount) {
        this.rowsCount = rowsCount;
    }
    
    public int getPrevious() {
        return this.previous;
    }
    
    public void setPrevious(final int previous) {
        this.previous = previous;
    }
    
    public int getNext() {
        return this.next;
    }
    
    public void setNext(final int next) {
        this.next = next;
    }
    
    public int getPages() {
        return this.pages;
    }
    
    public void setPages(final int pages) {
        this.pages = pages;
    }
    
    public String getPageTextCn() {
        return this.pageTextCn;
    }
    
    public void setPageTextCn(final String pageTextCn) {
        this.pageTextCn = pageTextCn;
    }
    
    public String getPageTextEn() {
        return this.pageTextEn;
    }
    
    public void setPageTextEn(final String pageTextEn) {
        this.pageTextEn = pageTextEn;
    }
    
    public String getPageNum() {
        return this.pageNum;
    }
    
    public void setPageNum(final String pageNum) {
        this.pageNum = pageNum;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String getPageTextAjax() {
        return this.pageTextAjax;
    }
    
    public void setPageTextAjax(final String pageTextAjax) {
        this.pageTextAjax = pageTextAjax;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public List getPageList() {
        return this.pageList;
    }
    
    public void setPageList(final List pageList) {
        this.pageList = pageList;
    }
    
    public Pager(final String currentpage, final int pageSize, final int rowsCount, final HttpServletRequest request) {
        try {
            this.currentPage = Integer.parseInt(request.getParameter(currentpage).toString());
        }
        catch (Exception e) {
            this.currentPage = 1;
        }
        this.pageSize = pageSize;
        this.rowsCount = rowsCount;
        if (rowsCount % pageSize == 0) {
            this.pages = rowsCount / pageSize;
        }
        else {
            this.pages = rowsCount / pageSize + 1;
        }
        if (this.currentPage - 1 > 0) {
            this.previous = this.currentPage - 1;
        }
        else {
            this.previous = 1;
        }
        if (this.currentPage < this.pages) {
            this.next = this.currentPage + 1;
        }
        else {
            this.next = this.pages;
        }
        if (request.getQueryString() != null) {
            this.url = String.valueOf(request.getRequestURI()) + "?" + request.getQueryString();
        }
        else {
            this.url = request.getRequestURI();
        }
        if (this.url.endsWith("&")) {
            this.url = this.url.substring(0, this.url.length() - 1);
        }
        if (this.url.endsWith("?")) {
            this.url = this.url.substring(0, this.url.length() - 1);
        }
        int temp = this.url.indexOf("?");
        int start = this.url.indexOf(currentpage);
        int end = this.url.indexOf("&", start);
        if (start != -1) {
            if (end != -1) {
                this.url = String.valueOf(this.url.substring(0, start - 1)) + this.url.substring(end + 1);
            }
            else {
                this.url = this.url.substring(0, start - 1);
            }
        }
        temp = this.url.indexOf("?");
        if (temp == -1) {
            this.url = String.valueOf(this.url) + "?";
        }
        else if (!this.url.endsWith("&")) {
            this.url = String.valueOf(this.url) + "&";
        }
        this.pageTextCn = "qwwwwwwwwwwwww";
        this.pageTextCn = this.pageTextCn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + 1).concat("\">\u9996\u9875</a>");
        if (this.currentPage == 1) {
            this.pageTextCn = this.pageTextCn.concat("\u4e0a\u4e00\u9875");
        }
        else {
            this.pageTextCn = this.pageTextCn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.previous).concat("\">\u4e0a\u4e00\u9875</a>");
        }
        if (this.currentPage == this.pages) {
            this.pageTextCn = this.pageTextCn.concat("\u4e0b\u4e00\u9875");
        }
        else {
            this.pageTextCn = this.pageTextCn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.next).concat("\">\u4e0b\u4e00\u9875</a>");
        }
        this.pageTextCn = this.pageTextCn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.pages).concat("\">\u5c3e\u9875</a>");
        this.pageTextEn = "";
        this.pageTextEn = this.pageTextEn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + 1).concat("\">First page</a>");
        this.pageTextEn = this.pageTextEn.concat("&nbsp;&nbsp;");
        if (this.currentPage == 1) {
            this.pageTextEn = this.pageTextEn.concat("Previous page");
        }
        else {
            this.pageTextEn = this.pageTextEn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.previous).concat("\">Previous page</a>");
        }
        this.pageTextEn = this.pageTextEn.concat("&nbsp;&nbsp;");
        if (this.currentPage == this.pages) {
            this.pageTextEn = this.pageTextEn.concat("Next page");
        }
        else {
            this.pageTextEn = this.pageTextEn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.next).concat("\">Next page</a>");
        }
        this.pageTextEn = this.pageTextEn.concat("&nbsp;&nbsp;");
        this.pageTextEn = this.pageTextEn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.pages).concat("\">End page</a>");
        this.pageTextAjax = "";
        this.pageTextAjax = this.pageTextAjax.concat("<a href='javascript:page(1)'>" + this.getText("sundyn.homePage") + "</a>");
        this.pageTextAjax = String.valueOf(this.pageTextAjax) + " ";
        if (this.currentPage == 1) {
            this.pageTextAjax = this.pageTextAjax.concat(this.getText("sundyn.previousPage"));
        }
        else {
            this.pageTextAjax = this.pageTextAjax.concat("<a href='javascript:page(" + this.previous + ")'>" + this.getText("sundyn.previousPage") + "</a>");
        }
        this.pageTextAjax = String.valueOf(this.pageTextAjax) + " ";
        if (this.currentPage == this.pages) {
            this.pageTextAjax = this.pageTextAjax.concat(this.getText("sundyn.nextPage"));
        }
        else {
            this.pageTextAjax = this.pageTextAjax.concat("<a href='javascript:page(" + this.next + ")'>" + this.getText("sundyn.nextPage") + "</a>");
        }
        this.pageTextAjax = String.valueOf(this.pageTextAjax) + " ";
        this.pageTextAjax = this.pageTextAjax.concat("<a href='javascript:page(" + this.pages + ")'>" + this.getText("sundyn.lastPage") + "</a>");
        this.pageNum = "";
        final StringBuffer sb = new StringBuffer();
        sb.append("<div class=\"pagerlist\">");
        sb.append("<div>");
        sb.append(this.currentPage);
        sb.append("/");
        sb.append(this.pages);
        sb.append(this.getText("sundyn.system.page"));
        sb.append("&nbsp;&nbsp;&nbsp;");
        sb.append(this.rowsCount);
        sb.append(this.getText("sundyn.system.records"));
        sb.append("</div>");
        sb.append(" <ul>");
        sb.append("<li><a href=\"" + this.url + currentpage + "=" + "1" + "\" class=\"a\"></a></li>");
        sb.append("<li><a href=\"" + this.url + currentpage + "=" + this.previous + "\" class=\"b\"></a></li>");
        start = 0;
        end = 0;
        if (this.currentPage - 3 < 1) {
            start = 1;
        }
        else {
            start = this.currentPage - 3;
        }
        if (this.currentPage + 3 > this.pages) {
            end = this.pages;
        }
        else {
            end = this.currentPage + 3;
        }
        for (int i = start; i <= end; ++i) {
            if (this.currentPage == i) {
                sb.append("<li><a href=\"" + this.url + currentpage + "=" + i + "\" class=\"d\">" + i + "</a></li>");
            }
            else {
                sb.append("<li><a href=\"" + this.url + currentpage + "=" + i + "\" class=\"c\">" + i + "</a></li>");
            }
        }
        sb.append("<li><a href=\"" + this.url + currentpage + "=" + this.next + "\" class=\"e\"></a></li>");
        sb.append("<li><a href=\"" + this.url + currentpage + "=" + this.pages + "\" class=\"f\"></a></li>");
        sb.append("</ul>");
        sb.append("</div>");
        this.pageTextCn = new String(sb);
    }
    
    public Pager(final String currentpage, final int pageSize, final int rowsCount, final HttpServletRequest request, final String ajaxF) {
    	this.rowsCount = rowsCount;
        try {
            this.currentPage = Integer.parseInt(request.getParameter(currentpage).toString());
        }
        catch (Exception e) {
            this.currentPage = 1;
        }
        this.pageSize = pageSize;
        if (rowsCount % pageSize == 0) {
            this.pages = rowsCount / pageSize;
        }
        else {
            this.pages = rowsCount / pageSize + 1;
        }
        if (this.currentPage > 0) {
            this.previous = this.currentPage - 1;
        }
        else {
            this.previous = 1;
        }
        if (this.currentPage < this.pages) {
            this.next = this.currentPage + 1;
        }
        else {
            this.next = this.pages;
        }
        if (request.getQueryString() != null) {
            this.url = String.valueOf(request.getRequestURI()) + "?" + request.getQueryString();
        }
        else {
            this.url = request.getRequestURI();
        }
        if (this.url.endsWith("&")) {
            this.url = this.url.substring(0, this.url.length() - 1);
        }
        if (this.url.endsWith("?")) {
            this.url = this.url.substring(0, this.url.length() - 1);
        }
        int temp = this.url.indexOf("?");
        final int start = this.url.indexOf(currentpage);
        final int end = this.url.indexOf("&", start);
        if (start != -1) {
            if (end != -1) {
                this.url = String.valueOf(this.url.substring(0, start - 1)) + this.url.substring(end + 1);
            }
            else {
                this.url = this.url.substring(0, start - 1);
            }
        }
        temp = this.url.indexOf("?");
        if (temp == -1) {
            this.url = String.valueOf(this.url) + "?";
        }
        else if (!this.url.endsWith("&")) {
            this.url = String.valueOf(this.url) + "&";
        }
        this.pageTextCn = "";
        this.pageTextCn = this.pageTextCn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + 1).concat("\">\u9996\u9875</a>");
        if (this.currentPage == 1) {
            this.pageTextCn = this.pageTextCn.concat("\u4e0a\u4e00\u9875");
        }
        else {
            this.pageTextCn = this.pageTextCn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.previous).concat("\">\u4e0a\u4e00\u9875</a>");
        }
        if (this.currentPage == this.pages) {
            this.pageTextCn = this.pageTextCn.concat("\u4e0b\u4e00\u9875");
        }
        else {
            this.pageTextCn = this.pageTextCn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.next).concat("\">\u4e0b\u4e00\u9875</a>");
        }
        this.pageTextCn = this.pageTextCn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.pages).concat("\">\u5c3e\u9875</a>");
        this.pageTextEn = "";
        this.pageTextEn = this.pageTextEn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + 1).concat("\">First page</a>");
        this.pageTextEn = this.pageTextEn.concat("&nbsp;&nbsp;");
        if (this.currentPage == 1) {
            this.pageTextEn = this.pageTextEn.concat("Previous page");
        }
        else {
            this.pageTextEn = this.pageTextEn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.previous).concat("\">Previous page</a>");
        }
        this.pageTextEn = this.pageTextEn.concat("&nbsp;&nbsp;");
        if (this.currentPage == this.pages) {
            this.pageTextEn = this.pageTextEn.concat("Next page");
        }
        else {
            this.pageTextEn = this.pageTextEn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.next).concat("\">Next page</a>");
        }
        this.pageTextEn = this.pageTextEn.concat("&nbsp;&nbsp;");
        this.pageTextEn = this.pageTextEn.concat("<a href=\"").concat(this.url).concat(String.valueOf(currentpage) + "=" + this.pages).concat("\">End page</a>");
        this.pageTextAjax = "";
        this.pageTextAjax = this.pageTextAjax.concat("<a href='javascript:" + ajaxF + "(1)'>" + this.getText("sundyn.homePage") + "</a>");
        this.pageTextAjax = String.valueOf(this.pageTextAjax) + " ";
        if (this.currentPage == 1) {
            this.pageTextAjax = this.pageTextAjax.concat(this.getText("sundyn.previousPage"));
        }
        else {
            this.pageTextAjax = this.pageTextAjax.concat("<a href='javascript:" + ajaxF + "(" + this.previous + ")'>" + this.getText("sundyn.previousPage") + "</a>");
        }
        this.pageTextAjax = String.valueOf(this.pageTextAjax) + " ";
        if (this.currentPage == this.pages) {
            this.pageTextAjax = this.pageTextAjax.concat(this.getText("sundyn.nextPage"));
        }
        else {
            this.pageTextAjax = this.pageTextAjax.concat("<a href='javascript:" + ajaxF + "(" + this.next + ")'>" + this.getText("sundyn.nextPage") + "</a>");
        }
        this.pageTextAjax = String.valueOf(this.pageTextAjax) + " ";
        this.pageTextAjax = this.pageTextAjax.concat("<a href='javascript:" + ajaxF + "(" + this.pages + ")'>" + this.getText("sundyn.lastPage") + "</a>");
    }
}
