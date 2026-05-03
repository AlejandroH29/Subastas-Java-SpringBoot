package com.dhernandez.auction_service.domain.exception;

public class ErrorActivatingAuction extends RuntimeException{
    public ErrorActivatingAuction(String message){
        super(message);
    }
}
