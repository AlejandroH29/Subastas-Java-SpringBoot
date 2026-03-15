package com.dhernandez.auction_service.domain.exception;

public class ErrorFindingUser extends RuntimeException{
    public ErrorFindingUser(String message){
        super(message);
    }
}
