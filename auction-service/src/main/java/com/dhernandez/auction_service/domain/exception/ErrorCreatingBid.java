package com.dhernandez.auction_service.domain.exception;

public class ErrorCreatingBid extends RuntimeException{
    public ErrorCreatingBid(String message){
        super(message);
    }
}
