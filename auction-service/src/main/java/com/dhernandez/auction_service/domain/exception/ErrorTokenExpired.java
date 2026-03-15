package com.dhernandez.auction_service.domain.exception;

public class ErrorTokenExpired extends RuntimeException{
    public ErrorTokenExpired(String message){
        super(message);
    }
}
