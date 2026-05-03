package com.dhernandez.auction_service.api.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dhernandez.auction_service.domain.exception.ErrorCreatingBid;
import com.dhernandez.auction_service.domain.exception.ErrorPlacingBid;

@ControllerAdvice
public class BidControllerAdvice {
    @ExceptionHandler(ErrorCreatingBid.class)
    public ResponseEntity<String>errorCreatingBid(ErrorCreatingBid error){
        return new ResponseEntity<String>(error.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    
    @ExceptionHandler(ErrorPlacingBid.class)
    public ResponseEntity<String>errorPlacingBid(ErrorPlacingBid error){
        return new ResponseEntity<String>(error.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
