package com.dhernandez.auction_service.application.port.out.User;

import com.dhernandez.auction_service.domain.model.User;

public interface FindUserByEmailPort {
    public User findUser(String email);
}
