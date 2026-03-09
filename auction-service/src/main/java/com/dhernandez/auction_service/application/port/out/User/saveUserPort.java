package com.dhernandez.auction_service.application.port.out.User;

import com.dhernandez.auction_service.domain.model.User;

public interface SaveUserPort {
    public User saveUser(User user);
}
