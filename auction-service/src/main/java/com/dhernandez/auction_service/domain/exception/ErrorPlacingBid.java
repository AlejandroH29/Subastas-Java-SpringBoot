package com.dhernandez.auction_service.domain.exception;

public class ErrorPlacingBid extends RuntimeException {
    public ErrorPlacingBid(String message){
        super(message);
    }
}
