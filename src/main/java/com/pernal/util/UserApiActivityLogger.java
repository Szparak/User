package com.pernal.util;

import com.pernal.persistence.entity.UserApiLogEntity;
import com.pernal.persistence.repository.IUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserApiActivityLogger {

    private final IUserRepository userRepository;

    public UserApiActivityLogger(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void logGet(String login){
        Optional<UserApiLogEntity> updatedUserApiLogEntity = userRepository.incrementRequestByLogin(login);
        if(!updatedUserApiLogEntity.isPresent()){
            UserApiLogEntity userApiLogEntity = new UserApiLogEntity();
            userApiLogEntity.setLogin(login);
            userApiLogEntity.setRequestCount(1);

            userRepository.save(userApiLogEntity);
        }
    }
}
