package com.dhernandez.auction_service.application.pagination;


public class PageRequest {
    private int page;
    private int size;
    public PageRequest(int page, int size){
        this.page = page;
        this.size = size;
    }

    public int getPage(){
        return page;
    }
    public int getSize(){
        return size;
    }
}
