package com.dhernandez.auction_service.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Auctions", schema = "auctions_db")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idAuction;
    private String title;
    private String description;
    @Column(name= "starttime")
    private LocalDateTime startTime;
    @Column(name= "endtime")
    private LocalDateTime endTime;
    private String status;
    @Column(name= "startingprice")
    private BigDecimal startingPrice;
    @Column(name= "currentprice")
    private BigDecimal currentPrice;
    @Column(name= "ownerid")
    private Long ownerId;
    @Column(name= "winnerid")
    private Long winnerId;

    public AuctionJpaEntity(String title, String description,  LocalDateTime startTime, LocalDateTime endTime, String status, BigDecimal startingPrice, Long ownerId){
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.ownerId = ownerId;
        this.winnerId = null;
    }
}
