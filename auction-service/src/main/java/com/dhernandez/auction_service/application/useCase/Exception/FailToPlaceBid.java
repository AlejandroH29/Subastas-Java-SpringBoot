package com.dhernandez.auction_service.application.useCase.Exception;

public class FailToPlaceBid extends RuntimeException{
    public FailToPlaceBid(String message){
        super(message);
    }
}
