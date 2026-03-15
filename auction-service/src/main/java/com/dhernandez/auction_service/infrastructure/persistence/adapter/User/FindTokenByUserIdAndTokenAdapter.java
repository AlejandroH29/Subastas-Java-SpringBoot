package com.dhernandez.auction_service.infrastructure.persistence.adapter.User;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.User.FindTokenByUserIdAndTokenPort;
import com.dhernandez.auction_service.domain.exception.ErrorFindingToken;
import com.dhernandez.auction_service.domain.model.EmailVerificationToken;
import com.dhernandez.auction_service.infrastructure.persistence.TokenJpaEntity;
import com.dhernandez.auction_service.infrastructure.persistence.repository.TokenJpaRepository;
@Component
public class FindTokenByUserIdAndTokenAdapter implements FindTokenByUserIdAndTokenPort{

    private final TokenJpaRepository tokenRepository;

    public FindTokenByUserIdAndTokenAdapter(TokenJpaRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }
    @Override
    public EmailVerificationToken findToken(String userId, int token) {
        TokenJpaEntity tokenFound = findTokenJpaEntity(userId, token);
        if(tokenFound == null){
            throw new ErrorFindingToken("No se encontro el token");
        }
        return new EmailVerificationToken(tokenFound.getId().toString(),tokenFound.getUserId(), tokenFound.getToken(), tokenFound.getUsed(), tokenFound.getExpirationDate(), tokenFound.getCreationAt());
    }

    public TokenJpaEntity findTokenJpaEntity(String userId, int token){
        Optional<TokenJpaEntity> tokenFound = tokenRepository.findByUserIdAndToken(userId, token);
        return tokenFound.orElse(null);
    }
    
}
