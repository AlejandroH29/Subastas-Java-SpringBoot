package com.dhernandez.auction_service.application.result;

import java.util.ArrayList;
import java.util.List;

public class ActiveAuctionsResult {
    private List<AuctionStatusActive> auctionsActive;
    public ActiveAuctionsResult(List<AuctionStatusActive> auctionsActive){
        this.auctionsActive = auctionsActive;
    }
    public List<AuctionStatusActive> getAuctionsActive(){
        return new ArrayList<>(auctionsActive);
    }
}
