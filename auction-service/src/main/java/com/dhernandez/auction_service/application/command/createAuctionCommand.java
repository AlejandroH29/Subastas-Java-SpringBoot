package com.dhernandez.auction_service.application.command;

import java.time.LocalDateTime;

public class createAuctionCommand {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double startingPrice;
    private String ownerId;
    public createAuctionCommand(String title, String description,  LocalDateTime startTime, LocalDateTime endTime, Double startingPrice, String ownerId){
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startingPrice = startingPrice;
        this.ownerId = ownerId;
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
    public Double getStartingPrice(){
        return startingPrice;
    }
    public String getOwnerId(){
        return ownerId;
    }
}
