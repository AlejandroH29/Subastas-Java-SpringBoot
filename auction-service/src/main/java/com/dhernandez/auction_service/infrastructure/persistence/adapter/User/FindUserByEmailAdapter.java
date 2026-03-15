package com.dhernandez.auction_service.infrastructure.persistence.adapter.User;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.dhernandez.auction_service.application.port.out.User.FindUserByEmailPort;
import com.dhernandez.auction_service.domain.exception.ErrorFindingUser;
import com.dhernandez.auction_service.domain.model.User;
import com.dhernandez.auction_service.domain.model.Enum.EnumRoleUser;
import com.dhernandez.auction_service.infrastructure.persistence.UserJpaEntity;
import com.dhernandez.auction_service.infrastructure.persistence.repository.UserJpaRepository;

@Component
public class FindUserByEmailAdapter implements FindUserByEmailPort {
    private final UserJpaRepository userRepository;

    public FindUserByEmailAdapter(UserJpaRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(String email) {
        UserJpaEntity user = findUserJpaEntity(email);
        if(user == null){
            throw new ErrorFindingUser("El usuario no se encontro");
        }
        return new User(user.getId().toString(), user.getEmail(), user.getUserName(), user.getPassword(), user.getVerified(), EnumRoleUser.valueOf(user.getRole()));
    }

    public UserJpaEntity findUserJpaEntity(String email){
        Optional<UserJpaEntity> user = userRepository.findByEmail(email);
        return user.orElse(null); 
    }
}
