package com.dhernandez.auction_service.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VerifyEmailRequest {

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotNull(message = "El token no puede ser nulo")
    @Min(value = 1000, message = "El token debe tener 4 dígitos")
    @Max(value = 9999, message = "El token debe tener 4 dígitos")
    private Integer token;
    
    public VerifyEmailRequest(String email, Integer token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public Integer getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(Integer token) {
        this.token = token;
    }
}
