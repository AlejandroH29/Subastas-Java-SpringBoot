package com.dhernandez.auction_service.api.dto;

import jakarta.validation.constraints.NotNull;

public class CloseAuctionRequest {
    @NotNull
    private Long auctionId;
    public CloseAuctionRequest(Long auctionId){
        this.auctionId = auctionId;
    }
    public Long getAuctionId(){
        return auctionId;
    }
}
