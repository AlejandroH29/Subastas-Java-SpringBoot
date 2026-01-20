package com.dhernandez.auction_service.domain.exception;

public class ErrorCreatingAuction extends RuntimeException{
    public ErrorCreatingAuction(String message){
        super(message);
    }
}
