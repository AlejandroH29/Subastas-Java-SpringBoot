package com.dhernandez.auction_service.application.port.out.User;

public interface ExistUserByEmailPort {
    public boolean existUserByEmail(String email);
}
