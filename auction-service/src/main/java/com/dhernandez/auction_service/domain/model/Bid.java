package com.dhernandez.auction_service.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.dhernandez.auction_service.domain.exception.ErrorCreatingBid;

public class Bid {
    private Long id;
    private Long auctionId;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime timeStamp;

    public Bid(Long auctionId, Long userId, BigDecimal amount){
        if(auctionId == null){
            throw new ErrorCreatingBid("No se reconoce subasta a la cual pujar");
        }
        if(userId == null){
            throw new ErrorCreatingBid("No se reconoce usuario");
        }
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new ErrorCreatingBid("El valor de la puja no puede ser negativo ni cero");
        }
    
        this.id = null;
        this.auctionId = auctionId;
        this.userId = userId;
        this.amount = amount;
        this.timeStamp = LocalDateTime.now();
    }
    public Bid(Long id, Long auctionId, Long userId, BigDecimal amount, LocalDateTime timeStamp){
        this.id = id;
        this.auctionId = auctionId;
        this.userId = userId;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public Long getId(){
        return id;
    }
    public Long getAuctionId(){
        return auctionId;
    }
    public Long getUserId(){
        return userId;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public LocalDateTime getTimeStamp(){
        return timeStamp;
    }
}
