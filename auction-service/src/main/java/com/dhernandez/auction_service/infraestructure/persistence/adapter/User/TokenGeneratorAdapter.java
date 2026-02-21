package com.dhernandez.auction_service.infraestructure.persistence.adapter.User;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.User.madeTokenPasswordPort;
import com.dhernandez.auction_service.application.port.out.User.saveTokenVerificationPort;
import com.dhernandez.auction_service.domain.model.EmailVerificationToken;
import com.dhernandez.auction_service.infraestructure.persistence.TokenJpaEntity;
import com.dhernandez.auction_service.infraestructure.persistence.repository.TokenJpaRepository;


@Component
public class TokenGeneratorAdapter implements madeTokenPasswordPort, saveTokenVerificationPort{
    private final TokenJpaRepository tokenRepository;

    public TokenGeneratorAdapter(TokenJpaRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }

    @Override
    public int token() {
        SecureRandom secureRandom = new SecureRandom();
        int numero = secureRandom.nextInt(9000) + 1000;
        return numero;
    }

    @Override
    public void saveToken(EmailVerificationToken token) {
        TokenJpaEntity tokenEntity = new TokenJpaEntity(token.getUserId(), token.getToken(), token.getUsed(), token.getExpirationDate(), token.getCreatedAt());
        tokenRepository.save(tokenEntity);
    }
}
