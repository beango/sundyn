package com.sundyn.utils;

public class Pagination
{
    private int page;
    private int pageSize;
    private int prePage;
    private int nextPage;
    private int startRow;
    private int maxPage;
    private int countTotal;
    
    public Pagination() {
        this.page = 1;
        this.pageSize = 5;
        this.prePage = 1;
        this.nextPage = 2;
        this.startRow = 1;
        this.maxPage = Integer.MAX_VALUE;
    }
    
    public int getPage() {
        return this.page;
    }
    
    public void setPage(int page) {
        if (page <= 0) {
            page = 1;
        }
        if (page > this.maxPage) {
            page = this.maxPage;
        }
        this.page = page;
        this.prePage = ((this.page == 1) ? 1 : (this.page - 1));
        this.nextPage = ((this.page == this.maxPage) ? this.maxPage : (this.page + 1));
        this.startRow = (this.page - 1) * this.pageSize + 1;
    }
    
    public int getPageSize() {
        return this.pageSize;
    }
    
    public void setPageSize(int pageSize) {
        if (pageSize < 3) {
            pageSize = 3;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }
        this.pageSize = pageSize;
        this.setPage(this.page);
    }
    
    public int getPrePage() {
        return this.prePage;
    }
    
    public void setPrePage(final int prePage) {
        this.prePage = prePage;
    }
    
    public int getNextPage() {
        return this.nextPage;
    }
    
    public void setNextPage(final int nextPage) {
        this.nextPage = nextPage;
    }
    
    public int getStartRow() {
        return this.startRow;
    }
    
    public void setStartRow(final int startRow) {
        this.startRow = startRow;
    }
    
    public int getMaxPage() {
        return this.maxPage;
    }
    
    public void setMaxPage(int maxPage) {
        if (maxPage <= 0) {
            maxPage = 1;
        }
        this.maxPage = maxPage;
        this.setPage(this.page);
    }
    
    public int getCountTotal() {
        return this.countTotal;
    }
    
    public void setCountTotal(final int countTotal) {
        this.countTotal = countTotal;
    }
}
