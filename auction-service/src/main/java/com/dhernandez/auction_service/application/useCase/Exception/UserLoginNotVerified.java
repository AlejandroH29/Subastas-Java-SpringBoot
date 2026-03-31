package com.dhernandez.auction_service.application.useCase.Exception;

public class UserLoginNotVerified extends RuntimeException{
    public UserLoginNotVerified(String message){
        super(message);
    }
}
