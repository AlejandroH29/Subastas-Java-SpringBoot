package com.dhernandez.auction_service.application.useCase.User;

import com.dhernandez.auction_service.application.command.LoginCommand;
import com.dhernandez.auction_service.application.result.LoginResult;

public interface LoginUseCase {
    public LoginResult login(LoginCommand loginCommand);
}
