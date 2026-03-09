package com.dhernandez.auction_service.application.port.out.User;

public interface PasswordHashPort {
    public String hash(String password);
}
