package com.dhernandez.auction_service.domain.exception;

public class ErrorFindingToken extends RuntimeException{
    public ErrorFindingToken(String message){
        super(message);
    }
}
