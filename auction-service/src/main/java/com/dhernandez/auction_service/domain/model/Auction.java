package com.dhernandez.auction_service.domain.model;

import java.time.LocalDateTime;

import com.dhernandez.auction_service.domain.exception.ErrorCreatingAuction;
import com.dhernandez.auction_service.domain.model.Enum.EnumAuction;

public class Auction{
    private Long idAuction;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EnumAuction status;
    private Double startingPrice;
    private Double currentPrice;
    private Long ownerId;
    private Long winnerId;

    public Auction(String title, String description,  LocalDateTime startTime, LocalDateTime endTime, Double startingPrice, Long ownerId){
        if(startingPrice <= 0){
            throw new ErrorCreatingAuction("El precio debe ser superior a 0");
        }
        if(startTime.isAfter(endTime)){
            throw new ErrorCreatingAuction("El timpo de cierre debe ser mayor al tiempo de inicio");
        }

        if(title.isBlank() || title == null){
            throw new ErrorCreatingAuction("El titulo no puede estar vacio");
        }

        if(ownerId == null){
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

    public Auction(Long idAuction, String title, String description,  LocalDateTime startTime, LocalDateTime endTime, EnumAuction status, Double startingPrice, Double currentPrice, Long ownerId, Long winnerId){
        this.idAuction = idAuction;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.ownerId = ownerId;
        this.winnerId = winnerId;
    }

    public Long getIdAuction(){
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
    public Long getOwnerId(){
        return ownerId;
    }
    public Long getWinnerId(){
        return winnerId;
    }
}
    
