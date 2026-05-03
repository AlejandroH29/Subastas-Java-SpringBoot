package com.dhernandez.auction_service.application.result;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateBidResult {
    private Long id;
    private Long auctionId;
    private BigDecimal amount;
    private LocalDateTime timeStamp;

    public CreateBidResult(Long id, Long auctionId, BigDecimal amount, LocalDateTime timeStamp){
        this.id = id;
        this.auctionId = auctionId;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public Long getId(){
        return id;
    }
    public Long getAuctionId(){
        return auctionId;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public LocalDateTime getTimeStamp(){
        return timeStamp;
    }
}
