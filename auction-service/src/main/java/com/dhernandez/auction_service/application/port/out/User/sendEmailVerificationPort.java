package com.dhernandez.auction_service.application.port.out.User;

public interface sendEmailVerificationPort {
    public void sendEmailVerification(String email, int token);
}
