package com.dhernandez.auction_service.application.command;

import java.math.BigDecimal;

public class CreateBidCommand {
    private Long auctionId;
    private BigDecimal amount;

    public CreateBidCommand(Long auctionId, BigDecimal amount){
        this.auctionId = auctionId;
        this.amount = amount;
    }

    public Long getAuctionId(){
        return auctionId;
    }
    public BigDecimal getAmount(){
        return amount;
    }
}
