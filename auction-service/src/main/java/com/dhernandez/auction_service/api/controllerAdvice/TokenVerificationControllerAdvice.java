package com.dhernandez.auction_service.api.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dhernandez.auction_service.domain.exception.ErrorFindingToken;
import com.dhernandez.auction_service.domain.exception.ErrorTokenAlreadyUsed;
import com.dhernandez.auction_service.domain.exception.ErrorTokenExpired;

@ControllerAdvice
public class TokenVerificationControllerAdvice {
    @ExceptionHandler(ErrorFindingToken.class)
    public ResponseEntity<String> errorFindingToken(ErrorFindingToken error){
        return new ResponseEntity<String>(error.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ErrorTokenAlreadyUsed.class)
    public ResponseEntity<String> errorTokenAlreadyUsed(ErrorTokenAlreadyUsed error){
        return new ResponseEntity<String>(error.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ErrorTokenExpired.class)
    public ResponseEntity<String> errorTokenExpired(ErrorTokenExpired error){
        return new ResponseEntity<String>(error.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
