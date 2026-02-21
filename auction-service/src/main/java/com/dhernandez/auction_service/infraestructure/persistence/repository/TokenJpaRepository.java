package com.dhernandez.auction_service.infraestructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhernandez.auction_service.infraestructure.persistence.TokenJpaEntity;

public interface TokenJpaRepository extends JpaRepository<TokenJpaEntity, Long>{
    
}
