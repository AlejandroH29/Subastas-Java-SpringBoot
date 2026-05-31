package com.dhernandez.auction_service.application.command;

public class ActiveAuctionCommand {
    private Long auctionId;
    
    public ActiveAuctionCommand(Long auctionId){
        this.auctionId = auctionId;
    }

    public Long getAuctionId(){
        return auctionId;
    }

}
