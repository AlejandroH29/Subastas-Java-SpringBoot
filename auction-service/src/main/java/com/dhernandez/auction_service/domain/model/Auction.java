package com.dhernandez.auction_service.domain.model;

import java.time.LocalDateTime;

import com.dhernandez.auction_service.domain.exception.ErrorCreatingAuction;

public class Auction{
    private String idAuction;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EnumAuction status;
    private Double startingPrice;
    private Double currentPrice;
    private String ownerId;
    private String winnerId;

    public Auction(String title, String description,  LocalDateTime startTime, LocalDateTime endTime, Double startingPrice, String ownerId){
        if(startingPrice <= 0){
            throw new ErrorCreatingAuction("El precio debe ser superior a 0");
        }
        if(startTime.isAfter(endTime)){
            throw new ErrorCreatingAuction("El timpo de cierre debe ser mayor al tiempo de inicio");
        }

        if(title.isBlank() || title == null){
            throw new ErrorCreatingAuction("El titulo no puede estar vacio");
        }

        if(ownerId.isBlank() || ownerId == null){
            throw new ErrorCreatingAuction("El creador de la subasta no puede estar vacio");
        }
        
        this.idAuction = null;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = EnumAuction.CREATED;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.ownerId = ownerId;
        this.winnerId = null;
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
    public Double getCurrentPrice(){
        return currentPrice;
    }
    public String getOwnerId(){
        return ownerId;
    }
    public String getWinnerId(){
        return winnerId;
    }
}
    
