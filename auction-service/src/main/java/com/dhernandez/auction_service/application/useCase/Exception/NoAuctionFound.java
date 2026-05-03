package com.dhernandez.auction_service.application.useCase.Exception;

public class NoAuctionFound extends RuntimeException {
    public NoAuctionFound(String message){
        super(message);
    }    
}
