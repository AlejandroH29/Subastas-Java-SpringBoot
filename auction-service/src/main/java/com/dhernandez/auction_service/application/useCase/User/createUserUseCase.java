package com.dhernandez.auction_service.application.useCase.User;

import com.dhernandez.auction_service.application.command.CreateUserCommand;
import com.dhernandez.auction_service.application.result.CreateUserResult;

public interface CreateUserUseCase {
    public CreateUserResult createUser(CreateUserCommand userCommand);
}
