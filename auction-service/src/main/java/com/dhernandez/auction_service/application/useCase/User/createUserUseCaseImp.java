package com.dhernandez.auction_service.application.useCase.User;

import java.time.LocalDateTime;

import com.dhernandez.auction_service.application.command.createUserCommand;
import com.dhernandez.auction_service.application.port.out.User.existUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.existUserByUserNamePort;
import com.dhernandez.auction_service.application.port.out.User.madeTokenPasswordPort;
import com.dhernandez.auction_service.application.port.out.User.passwordHashPort;
import com.dhernandez.auction_service.application.port.out.User.saveTokenVerificationPort;
import com.dhernandez.auction_service.application.port.out.User.saveUserPort;
import com.dhernandez.auction_service.application.port.out.User.sendEmailVerificationPort;
import com.dhernandez.auction_service.application.result.createUserResult;
import com.dhernandez.auction_service.domain.model.EmailVerificationToken;
import com.dhernandez.auction_service.domain.model.User;

import jakarta.transaction.Transactional;

public class createUserUseCaseImp implements createUserUseCase{

    final saveUserPort saveUserPort;
    final existUserByEmailPort existUserByEmailPort;
    final existUserByUserNamePort existUserByUserNamePort;
    final sendEmailVerificationPort sendEmailVerificationPort;
    final passwordHashPort passwordHashPort;
    final madeTokenPasswordPort madeTokenPassword;
    final saveTokenVerificationPort saveTokenVerificationPort;

    public createUserUseCaseImp(saveUserPort saveUserPort, existUserByEmailPort existUserByEmailPort, existUserByUserNamePort existUserByUserNamePort, sendEmailVerificationPort sendEmailVerificationPort, passwordHashPort passwordHashPort, madeTokenPasswordPort madeTokenPassword, saveTokenVerificationPort saveTokenVerificationPort){
        this.saveUserPort = saveUserPort;
        this.existUserByEmailPort = existUserByEmailPort;
        this.existUserByUserNamePort = existUserByUserNamePort;
        this.sendEmailVerificationPort = sendEmailVerificationPort;
        this.passwordHashPort = passwordHashPort;
        this.madeTokenPassword = madeTokenPassword;
        this.saveTokenVerificationPort = saveTokenVerificationPort;
    }

    @Transactional
    @Override
    public createUserResult createUser(createUserCommand command) {
        existUserByEmailPort.existUserByEmail(command.getEmail());
        existUserByUserNamePort.existUserByUserName(command.getUserName());
        String passwordHashed = passwordHashPort.hash(command.getPassword());
        User user = new User(command.getEmail(), command.getUserName(), passwordHashed);
        User savedUser = saveUserPort.saveUser(user);
        int token = madeTokenPassword.token();
        EmailVerificationToken tokenDomain = new EmailVerificationToken(savedUser.getIdUser(), token, LocalDateTime.now().plusMinutes(5), LocalDateTime.now());
        saveTokenVerificationPort.saveToken(tokenDomain);
        sendEmailVerificationPort.sendEmailVerification(user.getEmail(), token);
        return new createUserResult(savedUser.getIdUser(), savedUser.getEmail(), savedUser.getUserName(), savedUser.getVerified(), savedUser.getRole());
    }
}
