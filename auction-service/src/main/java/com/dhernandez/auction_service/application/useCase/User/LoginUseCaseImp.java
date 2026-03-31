package com.dhernandez.auction_service.application.useCase.User;

import com.dhernandez.auction_service.application.command.LoginCommand;
import com.dhernandez.auction_service.application.port.out.User.FindUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.JwtGeneratorPort;
import com.dhernandez.auction_service.application.port.out.User.PasswordVerificationPort;
import com.dhernandez.auction_service.application.result.LoginResult;
import com.dhernandez.auction_service.application.result.UserSummary;
import com.dhernandez.auction_service.application.useCase.Exception.IncorrectPasswordLogin;
import com.dhernandez.auction_service.application.useCase.Exception.UserLoginNotVerified;
import com.dhernandez.auction_service.domain.model.User;


public class LoginUseCaseImp implements LoginUseCase {
    private final FindUserByEmailPort findUserByEmail;
    private final PasswordVerificationPort passwordVerification;
    private final JwtGeneratorPort jwtGenerator;

    public LoginUseCaseImp(FindUserByEmailPort findUserByEmail, PasswordVerificationPort passwordVerification, JwtGeneratorPort jwtGenerator){
        this.findUserByEmail = findUserByEmail;
        this.passwordVerification = passwordVerification;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public LoginResult login(LoginCommand loginCommand) {
        User userFound = findUserByEmail.findUser(loginCommand.getEmail());
        if(!userFound.getVerified()){
            throw new UserLoginNotVerified("El usuario no esta verificado, verifica tu correo electronico");
        }
        boolean verification = passwordVerification.verifyPassword(loginCommand.getPassword(), userFound.getPassword());
        if(!verification){
            throw new IncorrectPasswordLogin("Contraseña incorrecta");
        }
        String accessToken = jwtGenerator.generateJwt(userFound.getIdUser(), userFound.getEmail(), userFound.getRole().name());
        UserSummary user = new UserSummary(userFound.getIdUser(), userFound.getEmail(), userFound.getUserName(), userFound.getRole().name());
        return new LoginResult(accessToken, "Bearer", user);
    }
    
}
