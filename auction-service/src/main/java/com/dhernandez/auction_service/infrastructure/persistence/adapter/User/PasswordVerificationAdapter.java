package com.dhernandez.auction_service.infrastructure.persistence.adapter.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.User.PasswordVerificationPort;

@Component
public class PasswordVerificationAdapter implements PasswordVerificationPort{
    @Override
    public boolean verifyPassword(String password, String passwordHashed) {
        PasswordEncoder encode = new BCryptPasswordEncoder();
        return encode.matches(password,passwordHashed);
    }
}
