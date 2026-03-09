package com.dhernandez.auction_service.application.port.out.Email;

import com.dhernandez.auction_service.domain.model.EmailMessage;

public interface SendEmailPort {
    public void send(EmailMessage emailMessage);
}
