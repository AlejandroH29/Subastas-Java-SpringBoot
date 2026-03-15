package com.dhernandez.auction_service.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhernandez.auction_service.infrastructure.persistence.TokenJpaEntity;

public interface TokenJpaRepository extends JpaRepository<TokenJpaEntity, Long>{
    Optional<TokenJpaEntity> findByUserIdAndToken(String userId, int token);
}
