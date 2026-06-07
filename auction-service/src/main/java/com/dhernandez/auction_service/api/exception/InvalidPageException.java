package com.dhernandez.auction_service.api.exception;

public class InvalidPageException extends RuntimeException{
    public InvalidPageException(String message){
        super(message);
    }
}
