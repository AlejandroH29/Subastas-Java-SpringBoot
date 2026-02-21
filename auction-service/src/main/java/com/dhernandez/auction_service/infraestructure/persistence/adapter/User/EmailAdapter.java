package com.dhernandez.auction_service.infraestructure.persistence.adapter.User;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.User.sendEmailVerificationPort;

@Component
public class EmailAdapter implements sendEmailVerificationPort{
    @Override
    public void sendEmailVerification(String email, int token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailVerification'");
    }
}
