package com.dhernandez.auction_service.api.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateBidRequest {

    @NotNull(message = "El id de la subasta no puede ser nulo")
    private Long auctionId;

    @NotNull(message = "El valor de la puja no puede ser nulo")
    @Positive(message = "El precio de la puja debe ser mayor a 0")
    private BigDecimal amount;

    public CreateBidRequest(Long auctionId, BigDecimal amount){
        this.auctionId = auctionId;
        this.amount = amount;
    }

    public Long getAuctionId(){
        return auctionId;
    }
    public BigDecimal getAmount(){
        return amount;
    }
}
