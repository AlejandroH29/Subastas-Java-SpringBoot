package com.dhernandez.auction_service.domain.exception;

public class ErrorCreatingToken extends RuntimeException{
    public ErrorCreatingToken(String message){
        super(message);
    }
}
