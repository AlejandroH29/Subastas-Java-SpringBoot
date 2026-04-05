package com.dhernandez.auction_service.application.command;

import java.time.LocalDateTime;

public class CreateAuctionCommand {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double startingPrice;
    public CreateAuctionCommand(String title, String description,  LocalDateTime startTime, LocalDateTime endTime, Double startingPrice){
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startingPrice = startingPrice;
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
}
