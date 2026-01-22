package com.dhernandez.auction_service.infraestructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhernandez.auction_service.infraestructure.persistence.AuctionJpaEntity;

public interface AuctionJpaRepository extends JpaRepository<AuctionJpaEntity, Long>{
    boolean existByTitle(String title);
}
