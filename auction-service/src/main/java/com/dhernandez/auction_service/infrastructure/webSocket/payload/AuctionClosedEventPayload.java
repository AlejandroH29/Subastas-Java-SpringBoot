package com.dhernandez.auction_service.infrastructure.webSocket.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuctionClosedEventPayload implements PayLoadEvent{
    private String type;
    private Long auctionId;
    private LocalDateTime endTime;
    private Long winnerId;
    private BigDecimal finalPrice;
    public AuctionClosedEventPayload(String type, Long auctionId, LocalDateTime endTime, Long winnerId, BigDecimal finalPrice){
        this.type = type;
        this.auctionId = auctionId;
        this.endTime = endTime;
        this.winnerId = winnerId;
        this.finalPrice = finalPrice;
    }

    public String getType(){
        return type;
    }
    public Long getAuctionId(){
        return auctionId;
    }
    public LocalDateTime getEndTime(){
        return endTime;
    }
    public Long getWinnerId(){
        return winnerId;
    }
    public BigDecimal getFinalPrice(){
        return finalPrice;
    }

}
