package com.dhernandez.auction_service.application.useCase.User;

import java.time.LocalDateTime;

import com.dhernandez.auction_service.application.Service.EmailService;
import com.dhernandez.auction_service.application.command.CreateUserCommand;
import com.dhernandez.auction_service.application.port.out.User.ExistUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.ExistUserByUserNamePort;
import com.dhernandez.auction_service.application.port.out.User.MadeTokenPasswordPort;
import com.dhernandez.auction_service.application.port.out.User.PasswordHashPort;
import com.dhernandez.auction_service.application.port.out.User.SaveTokenVerificationPort;
import com.dhernandez.auction_service.application.port.out.User.SaveUserPort;
import com.dhernandez.auction_service.application.result.CreateUserResult;
import com.dhernandez.auction_service.domain.model.EmailVerificationToken;
import com.dhernandez.auction_service.domain.model.User;

import org.springframework.transaction.annotation.Transactional;

public class CreateUserUseCaseImp implements CreateUserUseCase{

    final SaveUserPort saveUserPort;
    final ExistUserByEmailPort existUserByEmailPort;
    final ExistUserByUserNamePort existUserByUserNamePort;
    final PasswordHashPort passwordHashPort;
    final MadeTokenPasswordPort madeTokenPassword;
    final SaveTokenVerificationPort saveTokenVerificationPort;
    final EmailService emailService;

    public CreateUserUseCaseImp(SaveUserPort saveUserPort, ExistUserByEmailPort existUserByEmailPort, ExistUserByUserNamePort existUserByUserNamePort, PasswordHashPort passwordHashPort, MadeTokenPasswordPort madeTokenPassword, SaveTokenVerificationPort saveTokenVerificationPort, EmailService emailService){
        this.saveUserPort = saveUserPort;
        this.existUserByEmailPort = existUserByEmailPort;
        this.existUserByUserNamePort = existUserByUserNamePort;
        this.passwordHashPort = passwordHashPort;
        this.madeTokenPassword = madeTokenPassword;
        this.saveTokenVerificationPort = saveTokenVerificationPort;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public CreateUserResult createUser(CreateUserCommand command) {
        existUserByEmailPort.existUserByEmail(command.getEmail());
        existUserByUserNamePort.existUserByUserName(command.getUserName());
        String passwordHashed = passwordHashPort.hash(command.getPassword());
        User user = new User(command.getEmail(), command.getUserName(), passwordHashed);
        User savedUser = saveUserPort.saveUser(user);
        int token = madeTokenPassword.token();
        EmailVerificationToken tokenDomain = new EmailVerificationToken(savedUser.getIdUser(), token, LocalDateTime.now().plusMinutes(5), LocalDateTime.now());
        saveTokenVerificationPort.saveToken(tokenDomain);
        emailService.sendEmailVerification(savedUser.getEmail(), savedUser.getUserName(), tokenDomain.getToken());
        return new CreateUserResult(savedUser.getIdUser(), savedUser.getEmail(), savedUser.getUserName(), savedUser.getVerified(), savedUser.getRole());
    }
}
