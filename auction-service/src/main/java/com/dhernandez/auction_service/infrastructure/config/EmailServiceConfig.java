package com.dhernandez.auction_service.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dhernandez.auction_service.application.Service.EmailService;
import com.dhernandez.auction_service.application.port.out.Email.SendEmailPort;

@Configuration
public class EmailServiceConfig {
    @Bean
    public EmailService emailService(SendEmailPort sendEmailPort) {
        return new EmailService(sendEmailPort);
    }
}
