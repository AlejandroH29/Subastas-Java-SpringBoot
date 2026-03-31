package com.dhernandez.auction_service.api.controllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dhernandez.auction_service.application.useCase.Exception.IncorrectPasswordLogin;
import com.dhernandez.auction_service.application.useCase.Exception.UserLoginNotVerified;

@ControllerAdvice
public class LoginControllerAdvice {

    @ExceptionHandler(UserLoginNotVerified.class)
    public ResponseEntity<String> userNotVerified(UserLoginNotVerified message){
        return new ResponseEntity<String>(message.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(IncorrectPasswordLogin.class)
    public ResponseEntity<String> incorrectPassword(IncorrectPasswordLogin message){
        return new ResponseEntity<String>(message.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
