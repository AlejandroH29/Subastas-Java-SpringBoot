package com.dhernandez.auction_service.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhernandez.auction_service.infrastructure.persistence.AuctionJpaEntity;

public interface AuctionJpaRepository extends JpaRepository<AuctionJpaEntity, Long>{
    boolean existsByTitle(String title);
}
