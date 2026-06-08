package com.dhernandez.auction_service.infrastructure.persistence.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dhernandez.auction_service.infrastructure.persistence.AuctionJpaEntity;

public interface AuctionJpaRepository extends JpaRepository<AuctionJpaEntity, Long>{
    boolean existsById(Long id);
    boolean existsByTitle(String title);
    Page<AuctionJpaEntity> findByStatusAndEndTimeLessThanEqual(String status, LocalDateTime now, Pageable pageable);
    @Query("""
        SELECT a 
        FROM AuctionJpaEntity a 
        WHERE a.status = 'CREATED' 
        AND a.startTime <= :now
    """)
    Page<AuctionJpaEntity> findAuctionsReadyToActivate(@Param("now") LocalDateTime now, Pageable pageable);
    Page<AuctionJpaEntity> findByStatus(String status, Pageable pageable);
    Page<AuctionJpaEntity> findByOwnerId(Long id, Pageable pageable);
}
