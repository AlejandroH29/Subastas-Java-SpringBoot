package com.dhernandez.auction_service.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhernandez.auction_service.infrastructure.persistence.AuctionJpaEntity;

public interface AuctionJpaRepository extends JpaRepository<AuctionJpaEntity, Long>{
    boolean existsById(Long id);
    boolean existsByTitle(String title);
    List<AuctionJpaEntity> findByStatusAndEndTimeLessThanEqual(String status, LocalDateTime now);
}
