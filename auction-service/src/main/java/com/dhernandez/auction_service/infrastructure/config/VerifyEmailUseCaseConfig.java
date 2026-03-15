package com.dhernandez.auction_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhernandez.auction_service.application.port.out.User.FindTokenByUserIdAndTokenPort;
import com.dhernandez.auction_service.application.port.out.User.FindUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.SaveTokenVerificationPort;
import com.dhernandez.auction_service.application.port.out.User.SaveUserPort;
import com.dhernandez.auction_service.application.useCase.User.VerifyEmailUseCase;
import com.dhernandez.auction_service.application.useCase.User.VerifyEmailUseCaseImp;

@Configuration
public class VerifyEmailUseCaseConfig {

    @Bean
    public VerifyEmailUseCase verifyEmailUseCase(FindUserByEmailPort findUserByEmail, FindTokenByUserIdAndTokenPort findTokenByUserIdAndToken, SaveTokenVerificationPort saveTokenVerification,SaveUserPort saveUser){
        return new VerifyEmailUseCaseImp ( findUserByEmail,  findTokenByUserIdAndToken,  saveTokenVerification, saveUser);
    }
}
