package com.dhernandez.auction_service.application.result;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dhernandez.auction_service.domain.model.Enum.EnumAuction;

public class AuctionStatusActive {
    private Long auctionId;
    private String title;
    private String description;
    private EnumAuction status;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private LocalDateTime endTime;

    public AuctionStatusActive(Long auctionId, 
                                String title, String description, 
                                EnumAuction status, BigDecimal startingPrice, 
                                BigDecimal currentPrice, LocalDateTime endTime){
        this.auctionId = auctionId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.endTime = endTime;                            
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public EnumAuction getStatus() {
        return status;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
