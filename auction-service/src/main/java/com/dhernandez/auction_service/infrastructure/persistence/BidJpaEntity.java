package com.dhernandez.auction_service.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Bids", schema = "auctions_db")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "auctionid")
    private Long auctionId;
    @Column(name = "userid")
    private Long userId;
    private BigDecimal amount;
    @Column(name = "timestamp")
    private LocalDateTime timeStamp;

    public BidJpaEntity(Long auctionId, Long userId, BigDecimal amount, LocalDateTime timeStamp){
        this.auctionId = auctionId;
        this.userId = userId;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }
}
