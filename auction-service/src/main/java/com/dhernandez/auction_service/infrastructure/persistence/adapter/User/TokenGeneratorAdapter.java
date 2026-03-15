package com.dhernandez.auction_service.infrastructure.persistence.adapter.User;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.User.MadeTokenPasswordPort;
import com.dhernandez.auction_service.application.port.out.User.SaveTokenVerificationPort;
import com.dhernandez.auction_service.domain.model.EmailVerificationToken;
import com.dhernandez.auction_service.infrastructure.persistence.TokenJpaEntity;
import com.dhernandez.auction_service.infrastructure.persistence.repository.TokenJpaRepository;


@Component
public class TokenGeneratorAdapter implements MadeTokenPasswordPort, SaveTokenVerificationPort{
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
        if(token.getId() != null){
            tokenEntity.setId(Long.parseLong(token.getId()));
        }
        tokenRepository.save(tokenEntity);
    }
}
