package com.dhernandez.auction_service.application.useCase.User;

import com.dhernandez.auction_service.application.command.SendEmailVerificationCommand;

public interface SendEmailVerificationUseCase {
    public String sendEmailVerification(SendEmailVerificationCommand command);
}
