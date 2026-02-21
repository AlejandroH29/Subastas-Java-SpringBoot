package com.dhernandez.auction_service.application.port.out.User;

public interface passwordHashPort {
    public String hash(String password);
}
