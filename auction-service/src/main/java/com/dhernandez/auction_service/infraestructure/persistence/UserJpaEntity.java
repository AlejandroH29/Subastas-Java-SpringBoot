package com.dhernandez.auction_service.infraestructure.persistence;

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
@Table(name = "Users", schema = "auctions_db")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String email;
    private String userName;
    private String password;
    private Boolean verified;
    private String role;

    public UserJpaEntity(String email, String userName, String password, Boolean verified, String role){
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.verified = verified;
        this.role = role;
    }
}
