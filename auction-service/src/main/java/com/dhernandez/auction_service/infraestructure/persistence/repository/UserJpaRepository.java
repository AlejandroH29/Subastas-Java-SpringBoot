package com.dhernandez.auction_service.infraestructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhernandez.auction_service.infraestructure.persistence.UserJpaEntity;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long>{
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
}
