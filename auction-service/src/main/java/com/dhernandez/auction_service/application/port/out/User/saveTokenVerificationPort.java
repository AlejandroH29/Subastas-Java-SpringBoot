package com.dhernandez.auction_service.application.port.out.User;

import com.dhernandez.auction_service.domain.model.EmailVerificationToken;

public interface SaveTokenVerificationPort {
    public void saveToken(EmailVerificationToken token);
}
