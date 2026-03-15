package com.dhernandez.auction_service.api.dto;

public class VerifyEmailRequest {
    private String email;
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
