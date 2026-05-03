package com.dhernandez.auction_service.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dhernandez.auction_service.domain.exception.ErrorActivatingAuction;
import com.dhernandez.auction_service.domain.exception.ErrorCreatingAuction;
import com.dhernandez.auction_service.domain.exception.ErrorPlacingBid;
import com.dhernandez.auction_service.domain.model.Enum.EnumAuction;

public class Auction{
    private Long idAuction;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EnumAuction status;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private Long ownerId;
    private Long winnerId;

    public Auction(String title, String description,  LocalDateTime startTime, LocalDateTime endTime, BigDecimal startingPrice, Long ownerId){
        if(startingPrice == null || startingPrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new ErrorCreatingAuction("El precio debe ser superior a 0");
        }
        if(startTime == null || endTime == null){
            throw new ErrorCreatingAuction("Las fechas de creacion y cierre no pueden estar vacias");
        }
        if(startTime.isAfter(endTime)){
            throw new ErrorCreatingAuction("El timpo de cierre debe ser mayor al tiempo de inicio");
        }

        if(title == null || title.isBlank()){
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

    public Auction(Long idAuction, String title, String description,  LocalDateTime startTime, LocalDateTime endTime, EnumAuction status, BigDecimal startingPrice, BigDecimal currentPrice, Long ownerId, Long winnerId){
        
        if(idAuction == null){
            throw new ErrorCreatingAuction("El id de la subasta no puede ser nulo");
        }
        if(startingPrice == null || startingPrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new ErrorCreatingAuction("El precio debe ser superior a 0");
        }
        if(startTime == null || endTime == null){
            throw new ErrorCreatingAuction("Las fechas de creacion y cierre no pueden estar vacias");
        }
        if(startTime.isAfter(endTime)){
            throw new ErrorCreatingAuction("El timpo de cierre debe ser mayor al tiempo de inicio");
        }

        if(title == null || title.isBlank()){
            throw new ErrorCreatingAuction("El titulo no puede estar vacio");
        }

        if(ownerId == null){
            throw new ErrorCreatingAuction("El creador de la subasta no puede estar vacio");
        }
    
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

    public void activeAuction(){
        if(!this.status.equals(EnumAuction.ACTIVE) && 
            !this.status.equals(EnumAuction.CLOSED)){
                this.status = EnumAuction.ACTIVE;
        }else{
            throw new ErrorActivatingAuction("No se puede activar esta subasta, esta cerrada o ya esta activa");
        }
    }
    public void closeAuction(){
        if(!this.status.equals(EnumAuction.CLOSED)){
                this.status = EnumAuction.CLOSED;
        }else{
            throw new ErrorActivatingAuction("No se puede cerrar esta subasta, ya esta cerrada");
        }
    }
    public void placeBid(Bid bid){
        if(bid == null){
            throw new ErrorPlacingBid("Puja invalida");
        }
        if(!getStatus().equals(EnumAuction.ACTIVE) ||
            LocalDateTime.now().isAfter(endTime)){
            throw new ErrorPlacingBid("No se puede hacer puja a esta subasta, no esta activa o ya cerró");
        }
        if(bid.getUserId().equals(this.ownerId)){
            throw new ErrorPlacingBid("El dueño no puede pujar a su misma subasta");
        }
        if (bid.getAmount().compareTo(currentPrice) <= 0) {
            throw new ErrorPlacingBid("El valor de la puja debe ser mayor al valor del precio actual");
        }
        this.currentPrice = bid.getAmount();
        this.winnerId = bid.getUserId();
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
    public BigDecimal getStartingPrice(){
        return startingPrice;
    }
    public BigDecimal getCurrentPrice(){
        return currentPrice;
    }
    public Long getOwnerId(){
        return ownerId;
    }
    public Long getWinnerId(){
        return winnerId;
    }
}
    
