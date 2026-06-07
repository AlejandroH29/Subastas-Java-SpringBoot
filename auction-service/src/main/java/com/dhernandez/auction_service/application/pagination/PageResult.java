package com.dhernandez.auction_service.application.pagination;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T>{
    private List<T> data;
    private int page;
    private int pageSize;
    private int totalItems;
    private int totalPages;
    public PageResult(List<T> data, int page, int pageSize, int totalItems, int totalPages){
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
    public List<T> getData() {
        return new ArrayList<>(data);
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
