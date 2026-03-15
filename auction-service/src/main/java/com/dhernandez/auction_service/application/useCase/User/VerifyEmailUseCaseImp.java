package com.dhernandez.auction_service.application.useCase.User;

import org.springframework.transaction.annotation.Transactional;

import com.dhernandez.auction_service.application.command.VerifyEmailCommand;
import com.dhernandez.auction_service.application.port.out.User.FindTokenByUserIdAndTokenPort;
import com.dhernandez.auction_service.application.port.out.User.FindUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.SaveTokenVerificationPort;
import com.dhernandez.auction_service.application.port.out.User.SaveUserPort;
import com.dhernandez.auction_service.application.result.VerifyEmailResult;
import com.dhernandez.auction_service.domain.model.EmailVerificationToken;
import com.dhernandez.auction_service.domain.model.User;

public class VerifyEmailUseCaseImp implements VerifyEmailUseCase{

    private final FindUserByEmailPort findUserByEmail;
    private final FindTokenByUserIdAndTokenPort findTokenByUserIdAndToken;
    private final SaveTokenVerificationPort saveTokenVerification;
    private final SaveUserPort saveUser;

    public VerifyEmailUseCaseImp(FindUserByEmailPort findUserByEmail, FindTokenByUserIdAndTokenPort findTokenByUserIdAndToken, SaveTokenVerificationPort saveTokenVerification,SaveUserPort saveUser){
        this.findUserByEmail = findUserByEmail;
        this.findTokenByUserIdAndToken = findTokenByUserIdAndToken;
        this.saveTokenVerification = saveTokenVerification;
        this.saveUser = saveUser;
    }
    @Transactional
    @Override
    public VerifyEmailResult verifyEmail(VerifyEmailCommand verifyCommand) {
        User user = findUserByEmail.findUser(verifyCommand.getEmail());
        EmailVerificationToken token = findTokenByUserIdAndToken.findToken(user.getIdUser(), verifyCommand.getToken());
        token.isExpired();
        token.isUsed();
        token.markAsUsed(); 
        user.verify();
        saveTokenVerification.saveToken(token);
        saveUser.saveUser(user);
        return new VerifyEmailResult(user.getIdUser(), user.getEmail(), user.getUserName(), user.getVerified());
    }
    
}
