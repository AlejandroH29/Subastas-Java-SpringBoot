package com.dhernandez.auction_service.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AuctionClosed implements DomainEvent{
    private Long auctionId;
    private LocalDateTime endTime;
    private Long winnerId;
    private BigDecimal finalPrice;
    public AuctionClosed(Long auctionId, LocalDateTime endTime, Long winnerId, BigDecimal finalPrice){
        this.auctionId = auctionId;
        this.endTime = endTime;
        this.winnerId = winnerId;
        this.finalPrice = finalPrice;
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
