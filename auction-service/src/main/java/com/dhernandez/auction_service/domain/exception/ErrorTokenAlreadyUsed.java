package com.dhernandez.auction_service.domain.exception;

public class ErrorTokenAlreadyUsed extends RuntimeException{
    public ErrorTokenAlreadyUsed(String message){
        super(message);
    }
}
