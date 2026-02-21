package com.dhernandez.auction_service.application.useCase.User;

import com.dhernandez.auction_service.application.command.createUserCommand;
import com.dhernandez.auction_service.application.result.createUserResult;

public interface createUserUseCase {
    public createUserResult createUser(createUserCommand userCommand);
}
