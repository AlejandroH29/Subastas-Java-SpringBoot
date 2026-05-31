package com.dhernandez.auction_service.application.useCase.Exception;

public class IncorrectUserIdAuction extends RuntimeException{
    public IncorrectUserIdAuction(String message){
        super(message);
    }
}
