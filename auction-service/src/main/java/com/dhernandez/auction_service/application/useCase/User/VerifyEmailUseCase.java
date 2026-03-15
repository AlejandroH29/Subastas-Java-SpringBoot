package com.dhernandez.auction_service.application.useCase.User;

import com.dhernandez.auction_service.application.command.VerifyEmailCommand;
import com.dhernandez.auction_service.application.result.VerifyEmailResult;

public interface VerifyEmailUseCase {
    public VerifyEmailResult verifyEmail(VerifyEmailCommand verifyCommand);
}
