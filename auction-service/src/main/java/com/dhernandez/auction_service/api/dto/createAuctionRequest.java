package com.dhernandez.auction_service.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateAuctionRequest {

    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    private String title;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String description;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    private LocalDateTime startTime;

    @NotNull(message = "La fecha de fin no puede ser nula")
    private LocalDateTime endTime;

    @NotNull(message = "El precio inicial no puede ser nulo")
    @Positive(message = "El precio inicial debe ser mayor a 0")
    private BigDecimal startingPrice;

    public CreateAuctionRequest(String title, String description,  LocalDateTime startTime, LocalDateTime endTime, BigDecimal startingPrice){
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startingPrice = startingPrice;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }
    public LocalDateTime getEndTime(){
        return endTime;
    }
    public BigDecimal getStartingPrice(){
        return startingPrice;
    }
}
