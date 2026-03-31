package com.dhernandez.auction_service.application.port.out.User;

public interface PasswordVerificationPort {
    public boolean verifyPassword(String password, String passwordHashed);
}
