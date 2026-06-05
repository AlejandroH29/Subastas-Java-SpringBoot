package com.dhernandez.auction_service.application.result;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dhernandez.auction_service.domain.model.Enum.EnumAuction;

public class AuctionInfoResult {
    private Long auctionId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EnumAuction status;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;

    public AuctionInfoResult(Long auctionId,
                                String title,
                                String description,
                                LocalDateTime startTime,
                                LocalDateTime endTime,
                                EnumAuction status,
                                BigDecimal startingPrice,
                                BigDecimal currentPrice) {
        this.auctionId = auctionId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
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
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public EnumAuction getStatus(){
        return status;
    }
    public BigDecimal getStartingPrice() {
        return startingPrice;
    }
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }
}
