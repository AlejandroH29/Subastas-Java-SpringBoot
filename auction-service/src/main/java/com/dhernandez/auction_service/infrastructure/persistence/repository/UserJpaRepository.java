package com.dhernandez.auction_service.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhernandez.auction_service.infrastructure.persistence.UserJpaEntity;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long>{
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<UserJpaEntity> findByEmail(String email);
}
