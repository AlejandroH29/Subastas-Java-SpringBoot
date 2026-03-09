package com.dhernandez.auction_service.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhernandez.auction_service.application.Service.EmailService;
import com.dhernandez.auction_service.application.port.out.User.ExistUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.ExistUserByUserNamePort;
import com.dhernandez.auction_service.application.port.out.User.MadeTokenPasswordPort;
import com.dhernandez.auction_service.application.port.out.User.PasswordHashPort;
import com.dhernandez.auction_service.application.port.out.User.SaveTokenVerificationPort;
import com.dhernandez.auction_service.application.port.out.User.SaveUserPort;
import com.dhernandez.auction_service.application.useCase.User.CreateUserUseCase;
import com.dhernandez.auction_service.application.useCase.User.CreateUserUseCaseImp;

@Configuration
public class UserUseCaseConfig {
    @Bean
    public CreateUserUseCase createUserUseCase(SaveUserPort saveUserPort, ExistUserByEmailPort existUserByEmailPort, ExistUserByUserNamePort existUserByUserNamePort, PasswordHashPort passwordHashPort, MadeTokenPasswordPort madeTokenPassword, SaveTokenVerificationPort saveTokenVerificationPort, EmailService emailService){
        return new CreateUserUseCaseImp(saveUserPort, existUserByEmailPort, existUserByUserNamePort, passwordHashPort, madeTokenPassword, saveTokenVerificationPort, emailService);
    }
}
