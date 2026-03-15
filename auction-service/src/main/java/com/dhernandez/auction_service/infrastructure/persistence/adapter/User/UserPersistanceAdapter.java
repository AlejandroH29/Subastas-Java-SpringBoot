package com.dhernandez.auction_service.infrastructure.persistence.adapter.User;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.User.ExistUserByEmailPort;
import com.dhernandez.auction_service.application.port.out.User.ExistUserByUserNamePort;
import com.dhernandez.auction_service.application.port.out.User.SaveUserPort;
import com.dhernandez.auction_service.domain.exception.ErrorCreatingUser;
import com.dhernandez.auction_service.domain.model.User;
import com.dhernandez.auction_service.domain.model.Enum.EnumRoleUser;
import com.dhernandez.auction_service.infrastructure.persistence.UserJpaEntity;
import com.dhernandez.auction_service.infrastructure.persistence.repository.UserJpaRepository;

@Component
public class UserPersistanceAdapter implements ExistUserByEmailPort, ExistUserByUserNamePort, SaveUserPort {

    private final UserJpaRepository userRepository;
    
    public UserPersistanceAdapter(UserJpaRepository userJpaRepository){
        this.userRepository = userJpaRepository;
    }

    @Override
    public User saveUser(User user) {
        UserJpaEntity userEntity = new UserJpaEntity(user.getEmail(), user.getUserName(), user.getPassword(), user.getVerified(), user.getRole().toString());
        if(user.getIdUser() != null){
            userEntity.setId(Long.parseLong(user.getIdUser()));
        }
        UserJpaEntity userSaved = userRepository.save(userEntity);
        return new User(userSaved.getId().toString(), userSaved.getEmail(), userSaved.getUserName(), userSaved.getPassword(), userSaved.getVerified(), EnumRoleUser.valueOf(userSaved.getRole()));
    }
    
    @Override
    public boolean existUserByUserName(String userName) {
        if(userRepository.existsByUserName(userName)){
            throw new ErrorCreatingUser("Ya existe un usuario con ese nombre de usuario");
        }
        return false;
    }

    @Override
    public boolean existUserByEmail(String email) {
        if(userRepository.existsByEmail(email)){
            throw new ErrorCreatingUser("Ya existe un usuario con ese correo electronico");
        }
        return false;
    }
    
}
