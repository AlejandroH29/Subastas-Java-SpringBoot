package com.dhernandez.auction_service.application.useCase.User;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import com.dhernandez.auction_service.application.Service.EmailService;
import com.dhernandez.auction_service.application.command.SendEmailVerificationCommand;
import com.dhernandez.auction_service.application.port.out.User.FindUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.MadeTokenPasswordPort;
import com.dhernandez.auction_service.application.port.out.User.SaveTokenVerificationPort;
import com.dhernandez.auction_service.application.useCase.Exception.UserAlreadyVerified;
import com.dhernandez.auction_service.domain.exception.ErrorFindingUser;
import com.dhernandez.auction_service.domain.model.EmailVerificationToken;
import com.dhernandez.auction_service.domain.model.User;

public class SendEmailVerificationUseCaseImp implements SendEmailVerificationUseCase{

    private final FindUserByEmailPort findUserByEmailport;
    private final MadeTokenPasswordPort madeTokenPassword;
    private final SaveTokenVerificationPort saveTokenVerificationPort;
    private final EmailService emailService;
    public SendEmailVerificationUseCaseImp(FindUserByEmailPort findUserByEmailport,
                                            MadeTokenPasswordPort madeTokenPassword,
                                            SaveTokenVerificationPort saveTokenVerificationPort,
                                            EmailService emailService
    ){
        this.findUserByEmailport = findUserByEmailport;
        this.madeTokenPassword = madeTokenPassword;
        this.saveTokenVerificationPort = saveTokenVerificationPort;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public String sendEmailVerification(SendEmailVerificationCommand command) {
        User userFound = findUserByEmailport.findUser(command.getEmail());
        if(userFound == null){
            throw new ErrorFindingUser("No se encontro el usuario o no existe");
        }else{
            if(userFound.getVerified()){
                throw new UserAlreadyVerified("Usuario ya verificado");
            }
            int token = madeTokenPassword.token();
            EmailVerificationToken tokenDomain = new EmailVerificationToken(userFound.getIdUser(), 
                                                                            token, 
                                                                            LocalDateTime.now().plusMinutes(5), 
                                                                            LocalDateTime.now());
            saveTokenVerificationPort.saveToken(tokenDomain);
            emailService.sendEmailVerification(userFound.getEmail(), 
                                                userFound.getUserName(), 
                                                tokenDomain.getToken());
            return "Correo de verificacion de cuenta enviado con exito, " + userFound.getUserName();
        }
    }
}
