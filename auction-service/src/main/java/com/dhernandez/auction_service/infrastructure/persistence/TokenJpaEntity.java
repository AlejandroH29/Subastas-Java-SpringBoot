package com.dhernandez.auction_service.infrastructure.persistence;

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
@Table(name = "Tokens", schema = "auctions_db")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "userid")
    private String userId;
    private Integer token;
    private Boolean used;
    @Column(name = "expirationdate")
    private LocalDateTime expirationDate;
    @Column(name = "creationAt")
    private LocalDateTime creationAt;

    public TokenJpaEntity(String userId, Integer token, Boolean used, LocalDateTime expirationDate, LocalDateTime creationAt){
        this.userId = userId;
        this.token = token;
        this.used = used;
        this.expirationDate = expirationDate;
        this.creationAt = creationAt;
    }
}
