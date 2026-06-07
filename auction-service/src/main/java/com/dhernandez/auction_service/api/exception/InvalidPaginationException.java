package com.dhernandez.auction_service.api.exception;

public class InvalidPaginationException extends RuntimeException {
    public InvalidPaginationException(String message){
        super(message);
    }
}
