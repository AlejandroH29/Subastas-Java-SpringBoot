package com.dhernandez.auction_service.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhernandez.auction_service.application.port.out.User.existUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.existUserByUserNamePort;
import com.dhernandez.auction_service.application.port.out.User.madeTokenPasswordPort;
import com.dhernandez.auction_service.application.port.out.User.passwordHashPort;
import com.dhernandez.auction_service.application.port.out.User.saveTokenVerificationPort;
import com.dhernandez.auction_service.application.port.out.User.saveUserPort;
import com.dhernandez.auction_service.application.port.out.User.sendEmailVerificationPort;
import com.dhernandez.auction_service.application.useCase.User.createUserUseCase;
import com.dhernandez.auction_service.application.useCase.User.createUserUseCaseImp;

@Configuration
public class UserUseCaseConfig {
    @Bean
    public createUserUseCase createUserUseCase(saveUserPort saveUserPort, existUserByEmailPort existUserByEmailPort, existUserByUserNamePort existUserByUserNamePort, sendEmailVerificationPort sendEmailVerificationPort, passwordHashPort passwordHashPort, madeTokenPasswordPort madeTokenPassword, saveTokenVerificationPort saveTokenVerificationPort){
        return new createUserUseCaseImp(saveUserPort, existUserByEmailPort, existUserByUserNamePort, sendEmailVerificationPort, passwordHashPort, madeTokenPassword, saveTokenVerificationPort);
    }
}
