package com.dhernandez.auction_service.application.command;

public class SendEmailVerificationCommand {
    private String email;
    public SendEmailVerificationCommand(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
}
