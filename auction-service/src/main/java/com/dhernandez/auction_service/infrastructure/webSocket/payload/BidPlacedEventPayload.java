package com.dhernandez.auction_service.infrastructure.webSocket.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BidPlacedEventPayload implements PayLoadEvent{
    private String type;
    private Long auctionId;
    private BigDecimal currentPrice;
    private Long winnerId;
    private LocalDateTime timeStamp;
    public BidPlacedEventPayload(String type, Long auctionId, BigDecimal currentPrice, Long winnerId, LocalDateTime timeStamp){
        this.type = type;
        this.auctionId = auctionId;
        this.currentPrice = currentPrice;
        this.winnerId = winnerId;
        this.timeStamp = timeStamp;
    }

    public String getType(){
        return type;
    }
    public Long getAuctionId(){
        return auctionId;
    }
    public BigDecimal getCurrentPrice(){
        return currentPrice;
    }
    public Long getWinnerId(){
        return winnerId;
    }
    public LocalDateTime getTimeStamp(){
        return timeStamp;
    }
}
