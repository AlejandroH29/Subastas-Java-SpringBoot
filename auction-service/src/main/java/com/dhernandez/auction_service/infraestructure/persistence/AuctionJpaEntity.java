package com.dhernandez.auction_service.infraestructure.persistence;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Auctions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuctionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAuction;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Double startingPrice;
    private Double currentPrice;
    private String ownerId;
    private String winnerId;

    public AuctionJpaEntity(String title, String description,  LocalDateTime startTime, LocalDateTime endTime, String status, Double startingPrice, String ownerId){
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
