package com.dhernandez.auction_service.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhernandez.auction_service.infrastructure.persistence.BidJpaEntity;

public interface BidJpaRepository extends JpaRepository<BidJpaEntity, Long>{

}
