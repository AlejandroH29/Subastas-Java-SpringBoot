package com.dhernandez.auction_service.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhernandez.auction_service.infrastructure.persistence.BidJpaEntity;
import java.util.List;


public interface BidJpaRepository extends JpaRepository<BidJpaEntity, Long>{
    List<BidJpaEntity> findByAuctionIdOrderByTimeStampDesc(Long auctionId);
}
