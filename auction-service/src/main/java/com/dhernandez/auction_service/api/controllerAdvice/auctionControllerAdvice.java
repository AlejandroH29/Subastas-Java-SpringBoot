package com.dhernandez.auction_service.api.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dhernandez.auction_service.domain.exception.ErrorCreatingAuction;

@ControllerAdvice
public class auctionControllerAdvice {
    @ExceptionHandler(ErrorCreatingAuction.class)
    public ResponseEntity<String> errorAuction(ErrorCreatingAuction error){
        return new ResponseEntity<String>(error.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
