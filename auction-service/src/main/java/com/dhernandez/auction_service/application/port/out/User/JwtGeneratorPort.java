package com.dhernandez.auction_service.application.port.out.User;

public interface JwtGeneratorPort {
    public String generateJwt(Long userId, String email, String role);
}
