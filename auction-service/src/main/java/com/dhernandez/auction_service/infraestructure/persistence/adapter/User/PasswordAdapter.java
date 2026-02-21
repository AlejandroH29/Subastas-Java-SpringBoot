package com.dhernandez.auction_service.infraestructure.persistence.adapter.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.User.passwordHashPort;

@Component
public class PasswordAdapter implements passwordHashPort{
    @Override
    public String hash(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordHashed = encoder.encode(password);
        return passwordHashed;
    }

}
