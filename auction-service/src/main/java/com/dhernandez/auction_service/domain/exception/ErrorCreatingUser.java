package com.dhernandez.auction_service.domain.exception;

public class ErrorCreatingUser extends RuntimeException{
    public ErrorCreatingUser(String message){
        super(message);
    }
}
