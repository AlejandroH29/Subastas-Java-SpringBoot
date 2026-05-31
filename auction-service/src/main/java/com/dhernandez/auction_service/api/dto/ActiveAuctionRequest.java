package com.dhernandez.auction_service.api.dto;

import jakarta.validation.constraints.NotNull;

public class ActiveAuctionRequest {
    @NotNull
    private Long auctionId;
    public ActiveAuctionRequest(Long auctionId){
        this.auctionId = auctionId;
    }
    public Long getAuctionId(){
        return auctionId;
    }
}
