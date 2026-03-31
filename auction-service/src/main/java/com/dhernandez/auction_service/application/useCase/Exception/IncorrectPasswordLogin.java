package com.dhernandez.auction_service.application.useCase.Exception;

public class IncorrectPasswordLogin extends RuntimeException{
    public IncorrectPasswordLogin(String message){
        super(message);
    }
}
