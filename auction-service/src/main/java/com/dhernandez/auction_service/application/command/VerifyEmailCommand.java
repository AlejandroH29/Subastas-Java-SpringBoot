package com.dhernandez.auction_service.application.command;

public class VerifyEmailCommand {
    private final String email;
    private final int token;

    public VerifyEmailCommand(String email, int token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public int getToken() {
        return token;
    }
}

