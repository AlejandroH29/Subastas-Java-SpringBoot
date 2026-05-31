package com.dhernandez.auction_service.application.command;

public class CloseAuctionCommand {
    private Long auctionId;
    
    public CloseAuctionCommand(Long auctionId){
        this.auctionId = auctionId;
    }

    public Long getAuctionId(){
        return auctionId;
    }
}
