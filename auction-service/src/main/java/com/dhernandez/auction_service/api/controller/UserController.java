package com.dhernandez.auction_service.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhernandez.auction_service.api.dto.CreateUserRequest;
import com.dhernandez.auction_service.api.dto.LoginRequest;
import com.dhernandez.auction_service.api.dto.VerifyEmailRequest;
import com.dhernandez.auction_service.application.command.CreateUserCommand;
import com.dhernandez.auction_service.application.command.LoginCommand;
import com.dhernandez.auction_service.application.command.VerifyEmailCommand;
import com.dhernandez.auction_service.application.result.CreateUserResult;
import com.dhernandez.auction_service.application.result.LoginResult;
import com.dhernandez.auction_service.application.result.VerifyEmailResult;
import com.dhernandez.auction_service.application.useCase.User.CreateUserUseCase;
import com.dhernandez.auction_service.application.useCase.User.LoginUseCase;
import com.dhernandez.auction_service.application.useCase.User.VerifyEmailUseCase;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("Auction/user")
public class UserController {

    @Autowired CreateUserUseCase createUserUseCase;
    @Autowired VerifyEmailUseCase verifyEmail;
    @Autowired LoginUseCase loginUser;

    @PostMapping("/createUser")
    public ResponseEntity<CreateUserResult> createUser(@Valid @RequestBody CreateUserRequest entryUserDTO ){
        CreateUserCommand userCommand = new CreateUserCommand(entryUserDTO.getEmail(), entryUserDTO.getUserName(), entryUserDTO.getPassword());
        return new ResponseEntity<CreateUserResult>(createUserUseCase.createUser(userCommand), HttpStatus.CREATED);
    }

    @PutMapping("/verifyEmail")
    public ResponseEntity<VerifyEmailResult> verifyEmail(@Valid @RequestBody VerifyEmailRequest entryVerifyEmailDTO ){
        VerifyEmailCommand verifyCommand = new VerifyEmailCommand(entryVerifyEmailDTO.getEmail(), entryVerifyEmailDTO.getToken());
        return new ResponseEntity<VerifyEmailResult>(verifyEmail.verifyEmail(verifyCommand), HttpStatus.ACCEPTED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResult> login(@Valid @RequestBody LoginRequest entryLoginDTO){
        LoginCommand command = new LoginCommand(entryLoginDTO.getEmail(), entryLoginDTO.getPassword());
        return new ResponseEntity<LoginResult>(loginUser.login(command), HttpStatus.OK);
    }
    
}
