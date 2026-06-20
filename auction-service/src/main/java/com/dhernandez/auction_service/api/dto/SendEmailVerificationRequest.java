package com.dhernandez.auction_service.api.dto;

public class SendEmailVerificationRequest {
    private String email;
    public SendEmailVerificationRequest(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
}
