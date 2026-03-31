package com.dhernandez.auction_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhernandez.auction_service.application.Service.EmailService;
import com.dhernandez.auction_service.application.port.out.User.ExistUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.ExistUserByUserNamePort;
import com.dhernandez.auction_service.application.port.out.User.FindUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.JwtGeneratorPort;
import com.dhernandez.auction_service.application.port.out.User.MadeTokenPasswordPort;
import com.dhernandez.auction_service.application.port.out.User.PasswordHashPort;
import com.dhernandez.auction_service.application.port.out.User.PasswordVerificationPort;
import com.dhernandez.auction_service.application.port.out.User.SaveTokenVerificationPort;
import com.dhernandez.auction_service.application.port.out.User.SaveUserPort;
import com.dhernandez.auction_service.application.useCase.User.CreateUserUseCase;
import com.dhernandez.auction_service.application.useCase.User.CreateUserUseCaseImp;
import com.dhernandez.auction_service.application.useCase.User.LoginUseCase;
import com.dhernandez.auction_service.application.useCase.User.LoginUseCaseImp;

@Configuration
public class UserUseCaseConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(SaveUserPort saveUserPort, ExistUserByEmailPort existUserByEmailPort, ExistUserByUserNamePort existUserByUserNamePort, PasswordHashPort passwordHashPort, MadeTokenPasswordPort madeTokenPassword, SaveTokenVerificationPort saveTokenVerificationPort, EmailService emailService){
        return new CreateUserUseCaseImp(saveUserPort, existUserByEmailPort, existUserByUserNamePort, passwordHashPort, madeTokenPassword, saveTokenVerificationPort, emailService);
    }
    
    @Bean
    public LoginUseCase loginUseCase(FindUserByEmailPort findUserByEmailPort, PasswordVerificationPort passwordVerification, JwtGeneratorPort jwtGenerator){
        return new LoginUseCaseImp(findUserByEmailPort, passwordVerification, jwtGenerator);
    }
}
