package com.dhernandez.auction_service.application.result;

import java.util.ArrayList;
import java.util.List;

public class MyAuctionsResult {
    private List<MyAuctions> myAuctions;
    public MyAuctionsResult(List<MyAuctions> myAuctions){
        this.myAuctions = myAuctions;
    }

    public List<MyAuctions> getMyAuctions(){
        return new ArrayList<>(myAuctions);
    }
}
