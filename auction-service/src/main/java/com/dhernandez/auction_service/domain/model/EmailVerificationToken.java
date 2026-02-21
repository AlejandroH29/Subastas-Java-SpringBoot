package com.dhernandez.auction_service.domain.model;

import java.time.LocalDateTime;

import com.dhernandez.auction_service.domain.exception.ErrorCreatingToken;

public class EmailVerificationToken {
    private String id;
    private String userId;
    private Integer token;
    private boolean used;
    private LocalDateTime expirationDate;
    private LocalDateTime createdAt;

    public EmailVerificationToken(String userId, Integer token, LocalDateTime expirationDate, LocalDateTime createdAt){
        if (userId == null || userId.isBlank()) {
            throw new ErrorCreatingToken("El userId no puede ser vacío");
        }

        if (token < 1000 || token > 9999) {
            throw new ErrorCreatingToken("El token debe ser de 4 dígitos");
        }

        if (expirationDate.isBefore(createdAt)) {
            throw new ErrorCreatingToken("La fecha de expiración no puede ser anterior a la creación");
        }

        if (expirationDate == null || createdAt == null) {
            throw new ErrorCreatingToken("Las fechas no pueden ser null");
        }

        this.id = null;
        this.userId = userId;
        this.token = token;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
        this.used = false;
    }

    public void markAsUsed() {
        if (this.used) {
            throw new ErrorCreatingToken("El token ya fue usado");
        }
        if (isExpired()) {
            throw new ErrorCreatingToken("El token está expirado");
        }
        this.used = true;
    }

    public boolean isExpired() {
        if(LocalDateTime.now().isAfter(this.expirationDate)){
            return true;
        }
        return false;
    }

    public boolean isUsed() {
        return used;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getToken() {
        return token;
    }

    public boolean getUsed(){
        return used;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
