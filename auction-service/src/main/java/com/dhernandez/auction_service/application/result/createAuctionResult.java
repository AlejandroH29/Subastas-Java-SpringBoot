package com.dhernandez.auction_service.application.result;

import java.time.LocalDateTime;

import com.dhernandez.auction_service.domain.model.EnumAuction;

public class createAuctionResult {
    private String idAuction;
    private String title;
    private String description;
    private EnumAuction status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double startingPrice;

    public createAuctionResult(String idAuction, String title, String description, EnumAuction status, LocalDateTime startTime, LocalDateTime endTime, Double startingPrice){
        this.idAuction = idAuction;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startingPrice = startingPrice;
    }

    public String getIdAuction(){
        return idAuction;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }
    public LocalDateTime getEndTime(){
        return endTime;
    }
    public EnumAuction getStatus(){
        return status;
    }
    public Double getStartingPrice(){
        return startingPrice;
    }
        
}
