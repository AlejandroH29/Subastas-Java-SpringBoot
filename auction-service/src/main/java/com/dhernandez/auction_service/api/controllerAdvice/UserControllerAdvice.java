package com.dhernandez.auction_service.api.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dhernandez.auction_service.domain.exception.ErrorCreatingUser;
import com.dhernandez.auction_service.domain.exception.ErrorFindingUser;

@ControllerAdvice
public class UserControllerAdvice {   
    @ExceptionHandler(ErrorCreatingUser.class)
    public ResponseEntity<String> errorUser(ErrorCreatingUser error){
        return new ResponseEntity<String>(error.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    } 
    @ExceptionHandler(ErrorFindingUser.class)
    public ResponseEntity<String> errorFindingUser(ErrorFindingUser error){
        return new ResponseEntity<String>(error.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    } 
}
