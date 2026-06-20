package com.dhernandez.auction_service.application.useCase.Exception;

public class UserAlreadyVerified extends RuntimeException{
    public UserAlreadyVerified(String message){
        super(message);
    }
}
